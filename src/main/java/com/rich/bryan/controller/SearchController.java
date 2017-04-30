package com.rich.bryan.controller;

import com.rich.bryan.dto.Query;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.GetSearchResults;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Utils.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.rich.bryan.services.Utils.SortBy.DATE_ADDED_DESC;

@Controller
public class SearchController {

    @Autowired
    private GetSearchResults getSearchResults;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShelvesService shelvesService;

    @GetMapping("/search")
    public String search(@ModelAttribute Query query, Model model, Principal principal){
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("results", getSearchResults.searchResults(query.getQuery(), principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        return "dashboard-results";
    }

    @PostMapping("/save/{id}")
    public String save(@PathVariable Integer id, Principal principal){
        String isbn13 = getSearchResults.saveSearchResult(id, principal.getName());
        return "redirect:/bookinfo/" + isbn13;
    }

    @GetMapping({"/books", "/"})
    public String books(Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), DATE_ADDED_DESC));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "My Books");
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/books/{sortBy}")
    public String books(@PathVariable("sortBy") Integer sortBy, Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), SortBy.valueOf(sortBy)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "My Books");
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model, Principal principal){

        Object[] objects = bookService.getAuthor(id, principal.getName());

        model.addAttribute("results", objects[1]);
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", objects[0]);
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/bookinfo/{isbn13}")
    public String singleBook(@PathVariable("isbn13") String isbn13, Model model, Principal principal){
        model.addAttribute("results", bookService.getSingleBook(isbn13, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("isOnShelf", shelvesService.getShelvesBookIsOn(principal.getName(),isbn13));
        model.addAttribute("readingState", shelvesService.getReadingState(principal.getName(),isbn13));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("query", new Query());
        return "Book-Info";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Principal principal){
        bookService.deleteBook(id, principal.getName());
        return "redirect:/books";
    }

    @GetMapping("/createShelf/{shelfName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void createShelf(@PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.createShelf(principal.getName(), shelfName);
    }

    @GetMapping("/addToShelf/{id}/{shelfName}")
    @ResponseBody
    public List<Object[]> addToShelf(@PathVariable("id") Long id, @PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.addBooktoShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @GetMapping("/removeFromShelf/{id}/{shelfName}")
    @ResponseBody
    public List<Object[]> removeFromShelf(@PathVariable("id") Long id, @PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.removeFromShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @GetMapping("/getShelf/{shelfName}")
    public String getShelf(@PathVariable("shelfName") String shelfName, Principal principal, Model model){
        model.addAttribute("results", shelvesService.getShelf(principal.getName(), shelfName));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", shelfName);
        model.addAttribute("showOptions", true);
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/renameShelf/{shelfName}/{newShelfName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void renameShelf(@PathVariable("shelfName") String shelfName, @PathVariable("newShelfName") String newShelfName, Principal principal) {
        shelvesService.renameShelf(principal.getName(), shelfName, newShelfName);
    }

    @GetMapping("/deleteShelf/{shelfName}")
    public String deleteShelf(@PathVariable("shelfName") String shelfName, Principal principal){
        shelvesService.deleteShelf(principal.getName(), shelfName);

        return "redirect:/books";
    }
}