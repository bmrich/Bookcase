package com.rich.bryan.controller;

import com.rich.bryan.dto.Query;
import com.rich.bryan.services.BookService;
import com.rich.bryan.services.GetSearchResults;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Enum.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

import static com.rich.bryan.services.Enum.SortBy.DATE_ADDED_DESC;

@Controller
public class SearchController {

    @Autowired
    private GetSearchResults getSearchResults;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShelvesService shelvesService;

    @GetMapping("/")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "index";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute Query query, Model model, Principal principal){
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("results", getSearchResults.searchResults(query.getQuery(), principal.getName()));
        return "dashboard-results";
    }

    @PostMapping("/save/{id}")
    public String save(@PathVariable Integer id, Principal principal){
        getSearchResults.saveSearchResult(id, principal.getName());
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String books(Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), DATE_ADDED_DESC));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/books/{sortBy}")
    public String books(@PathVariable("sortBy") Integer sortBy, Model model, Principal principal){
        model.addAttribute("results", bookService.getBooks(principal.getName(), SortBy.valueOf(sortBy)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model, Principal principal){
        model.addAttribute("results", bookService.getAuthor(id, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/bookinfo/{isbn13}")
    public String singleBook(@PathVariable("isbn13") String isbn13, Model model, Principal principal){
        model.addAttribute("results", bookService.getSingleBook(isbn13, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Book-Info";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Principal principal){
        bookService.deleteBook(id, principal.getName());
        return "redirect:/books";
    }
}