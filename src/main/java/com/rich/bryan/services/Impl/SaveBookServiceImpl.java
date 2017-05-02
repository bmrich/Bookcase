package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.dao.GoogleBooksApiDao;
import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.entity.Author;
import com.rich.bryan.entity.Book;
import com.rich.bryan.entity.Publisher;
import com.rich.bryan.services.SaveBookService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveBookServiceImpl implements SaveBookService {

    @Autowired
    private GoogleBooksApiDao  googleBooksApiDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private NewBookDao newBookDao;

    @Override
    @Transactional
    public String saveBook(String id, String username) {

        JSONObject object = null;
        try {
            object = googleBooksApiDao.googleBooksApiSearch(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Book book = parseJson(object);
        System.out.println(book);

        try{
            Book already = bookDao.getSingleBook(book.getIsbn13());
            newBookDao.newBook(already.getId(), username);
        } catch (NullPointerException e) {
            newBookDao.newBook(book, username);
        }

        return book.getIsbn13();
    }

    private Book parseJson(JSONObject jsonObj){

        Book res = new Book();

        try {
            JSONObject volumeInfo = jsonObj.getJSONObject("volumeInfo");

            res.setTitle(volumeInfo.optString("title"));

            Publisher publisher = new Publisher();
            publisher.setPublisher(volumeInfo.optString("publisher"));
            res.setPublisher(publisher);

            res.setDatePublished(volumeInfo.optString("publishedDate"));
            res.setPageCount(volumeInfo.optInt("pageCount"));
            res.setDescription(volumeInfo.optString("description"));

            JSONObject imageLinksObj = volumeInfo.optJSONObject("imageLinks");
            if (imageLinksObj != null) {
                res.setImageUrl(imageLinksObj.optString("thumbnail"));
                String imgTemp = imageLinksObj.optString("thumbnail");
                imgTemp = imgTemp.replace("&edge=curl", "");
                imgTemp = imgTemp.replace("zoom=1", "zoom=2");
                res.setImageUrl(imgTemp);
            } else {
                res.setImageUrl(null);
            }

            JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
            if (industryIdentifiers != null){
                for (int j = 0; j < industryIdentifiers.length(); j++){
                    JSONObject jsonObject = industryIdentifiers.getJSONObject(j);
                    if (jsonObject.optString("type").equals("ISBN_10")){
                        res.setIsbn10(jsonObject.optString("identifier"));
                    } else if (jsonObject.optString("type").equals("ISBN_13")){
                        res.setIsbn13(jsonObject.optString("identifier"));
                    }
                }
            }

            JSONArray authorsJsonArray = volumeInfo.optJSONArray("authors");
            if (authorsJsonArray != null){
                for (int k = 0; k < authorsJsonArray.length(); k++){
                    Author author = new Author();
                    String name = authorsJsonArray.getString(k);
                    try {
                        author.setLastName(name.substring(name.lastIndexOf(" ")+1));
                        author.setFirstName(name.substring(0, name.lastIndexOf(" ")));
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    res.getAuthors().add(author);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}
