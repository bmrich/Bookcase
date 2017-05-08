package com.rich.bryan.controller;

import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.SaveBookService;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.rich.bryan.services.Utils.Sort.DATE_CREATED_DESC;
import static com.rich.bryan.services.Utils.Sort.DATE_STARTED;

@Controller
public class MainController {

    private BookService bookService;
    private ShelvesService shelvesService;
    private SaveBookService saveBookService;

    @Autowired
    public MainController(BookService bookService, ShelvesService shelvesService, SaveBookService saveBookService) {
        this.bookService = bookService;
        this.shelvesService = shelvesService;
        this.saveBookService = saveBookService;
    }

    @GetMapping("/save/{id}")
    public String save(@PathVariable("id") String id, Principal principal){
        String isbn13 = saveBookService.saveBook(id, principal.getName());
        return "redirect:/bookinfo/" + isbn13;
    }

    @GetMapping({"/books", "/"})
    public String books(Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), DATE_CREATED_DESC));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "My Books");
        model.addAttribute("dropdown", "books");
        return "Cards";
    }

    @GetMapping("/books/{sortBy}")
    public String books(@PathVariable("sortBy") Integer sortBy, Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), Sort.valueOf(sortBy)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "My Books");
        model.addAttribute("dropdown", "books");
        return "Cards";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model, Principal principal){

        Object[] objects = bookService.getAuthor(id, principal.getName(), DATE_CREATED_DESC);

        model.addAttribute("results", objects[1]);
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", objects[0]);
        model.addAttribute("authorId", id);
        model.addAttribute("dropdown", "author");
        return "Cards";
    }

    @GetMapping("/author/{id}/{sort}")
    public String getAuthor(@PathVariable("id") Long id,
                            @PathVariable("sort") Integer sort,
                            Model model, Principal principal){

        Object[] objects = bookService.getAuthor(id, principal.getName(), Sort.valueOf(sort));

        model.addAttribute("results", objects[1]);
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", objects[0]);
        model.addAttribute("authorId", id);
        model.addAttribute("dropdown", "author");
        return "Cards";
    }

    @GetMapping("/bookinfo/{isbn13}")
    public String singleBook(@PathVariable("isbn13") String isbn13, Model model, Principal principal){
        model.addAttribute("results", bookService.getSingleBook(isbn13, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("isOnShelf", shelvesService.getShelvesBookIsOn(principal.getName(),isbn13));
        model.addAttribute("readingState", shelvesService.getReadingState(principal.getName(),isbn13));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        return "Book_Info";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Principal principal){
        bookService.deleteBook(id, principal.getName());
        return "redirect:/books";
    }

    @GetMapping("/createShelf/{shelfName}")
    @ResponseBody
    public String createShelf(@PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.createShelf(principal.getName(), shelfName);
        return "";
    }

    @GetMapping("/addToShelf/{id}/{shelfName}")
    @ResponseBody
    public List<Object[]> addToShelf(@PathVariable("id") Long id,
                                     @PathVariable("shelfName") String shelfName,
                                     Principal principal){

        shelvesService.addBooktoShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @GetMapping("/removeFromShelf/{id}/{shelfName}")
    @ResponseBody
    public List<Object[]> removeFromShelf(@PathVariable("id") Long id,
                                          @PathVariable("shelfName") String shelfName,
                                          Principal principal){

        shelvesService.removeFromShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @GetMapping("/getShelf/{shelfName}")
    public String getShelf(@PathVariable("shelfName") String shelfName, Principal principal, Model model){
        model.addAttribute("results", shelvesService.getShelf(principal.getName(), shelfName, DATE_CREATED_DESC));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", shelfName);
        model.addAttribute("showOptions", true);
        model.addAttribute("dropdown", "shelf");
        return "Cards";
    }

    @GetMapping("/getShelf/{shelfName}/{sort}")
    public String getShelf(@PathVariable("shelfName") String shelfName,
                           @PathVariable("sort") Integer sort,
                           Principal principal, Model model){

        model.addAttribute("results", shelvesService.getShelf(principal.getName(), shelfName, Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", shelfName);
        model.addAttribute("showOptions", true);
        model.addAttribute("dropdown", "shelf");
        return "Cards";
    }

    @GetMapping("/renameShelf/{shelfName}/{newShelfName}")
    public String renameShelf(@PathVariable("shelfName") String shelfName,
                            @PathVariable("newShelfName") String newShelfName,
                            Principal principal) {

        shelvesService.renameShelf(principal.getName(), shelfName, newShelfName);
        System.out.println("redirect:/getShelf/"+newShelfName);
        return "redirect:/getShelf/"+newShelfName;
    }

    @GetMapping("/deleteShelf/{shelfName}")
    public String deleteShelf(@PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.deleteShelf(principal.getName(), shelfName);

        return "redirect:/books";
    }

    @GetMapping("/state")
    @ResponseBody
    public Map<String, Integer> state(BooksUsers bu, Principal principal){
        shelvesService.updateState(bu);
        return shelvesService.numBooksOnShelf(principal.getName());
    }

    @GetMapping("/updatecr")
    @ResponseBody
    public Integer updateCR(BooksUsers bu){
        shelvesService.updateState(bu);
        return shelvesService.getCurrentPage(bu.getId());
    }

    @GetMapping("/perm/{shelf}/{sort}")
    public String getPerm(@PathVariable("shelf") String shelf,
                         @PathVariable("sort") Integer sort,
                         Principal principal, Model model){

        model.addAttribute("results", shelvesService.getPerm(principal.getName(), shelf, Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));

        String[] name = getStateFromCode(shelf);

        model.addAttribute("pageName", name[0]);

        return name[1];
    }

    @GetMapping("/perm/{shelf}")
    public String getPerm(@PathVariable("shelf") String shelf,
                          Principal principal, Model model){

        model.addAttribute("results", shelvesService.getPerm(principal.getName(), shelf, DATE_STARTED));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));

        String[] name = getStateFromCode(shelf);

        model.addAttribute("pageName", name[0]);

        return name[1];
    }

    private String[] getStateFromCode(@PathVariable("shelf") String shelf) {
        String[] name = new String[2];
        switch (shelf){
            case "TR":
                name[0] = "To Read";
                name[1] = "Cards";
                break;
            case "CR":
                name[0] = "Currently Reading";
                name[1] ="Currently_Reading_Cards";
                break;
            case "R":
                name[0] = "Completed";
                name[1] = "Cards";
                break;
        }
        return name;
    }
}