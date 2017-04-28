package com.rich.bryan.services.Impl;

import com.rich.bryan.dao.BookDao;
import com.rich.bryan.dao.GoogleBooksApiDao;
import com.rich.bryan.dao.NewBookDao;
import com.rich.bryan.entity.Book;
import com.rich.bryan.dto.AuthorDto;
import com.rich.bryan.dto.SearchResult;
import com.rich.bryan.services.GetSearchResults;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetSearchResultsImpl implements GetSearchResults {

    @Autowired
    private GoogleBooksApiDao googleBooksApiDao;

    @Autowired
    private NewBookDao newBookDao;

    @Autowired
    private BookDao bookDao;

    private ConcurrentHashMap<String, Map<Integer, SearchResult>> searchResultMap = new ConcurrentHashMap<>();

    @Override
    @Transactional(dontRollbackOn = NullPointerException.class)
    public String saveSearchResult(Integer id, String username) {

        Map<Integer, SearchResult> results = searchResultMap.get(username);
        SearchResult searchResult = results.get(id);
        String isbn13 = searchResult.getIsbn13();
        try{
            Book book = bookDao.getSingleBook(isbn13);
            newBookDao.newBook(book.getId(), username);
        } catch (NullPointerException e) {
            newBookDao.newBook(searchResult, username);
        }

        searchResultMap.remove(username);

        return isbn13;
    }

    @Override
    public List<SearchResult> searchResults(String query, String name) {

        if (searchResultMap.containsKey(name)){
            searchResultMap.remove(name);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = googleBooksApiDao.googleBooksApiSearch(query);
        } catch (Exception e) {
            System.out.println("HTTP ERROR");
        }

        Map<Integer, SearchResult> searchResults = parseJson(jsonObject);
        searchResultMap.put(name, searchResults);

        return new ArrayList<>(searchResults.values());
    }

    private Map<Integer, SearchResult> parseJson(JSONObject jsonObj){
        Map<Integer, SearchResult> results = new HashMap<>();
        try {
            JSONArray jsonArray = jsonObj.getJSONArray("items");
            for(int i = 0; i < jsonArray.length(); i++) {

                JSONObject temp = jsonArray.optJSONObject(i);
                JSONObject volumeInfo = temp.getJSONObject("volumeInfo");

                SearchResult res = new SearchResult();

                res.setTitle(volumeInfo.optString("title"));
                res.setPublisher(volumeInfo.optString("publisher"));
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
                        res.getAuthorDtoList().add(new AuthorDto(authorsJsonArray.getString(k)));
                    }
                }
                res.setAuthor();

                res.setId(i);

                if (res.getIsbn13() == null || res.getIsbn10() == null){}
                else {
                    results.put(i, res);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }
}