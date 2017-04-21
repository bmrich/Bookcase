package com.rich.bryan.controller;

import com.rich.bryan.dao.Impl.DaoService;
import com.rich.bryan.dao.ShelvesDao;
import com.rich.bryan.dto.Query;
import com.rich.bryan.services.GetSearchResults;
import com.rich.bryan.services.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class SearchController {

    @Autowired
    private GetSearchResults getSearchResults;

    @Autowired
    private DaoService daoService;

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
        model.addAttribute("results", getSearchResults.searchResults(query.getQuery(), principal.getName()));
        return "dashboard-results";
    }

    @PostMapping("/save/{id}")
    public String save(@PathVariable Integer id, Principal principal){
        getSearchResults.saveSearchResult(id, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/books")
    public String books(Model model, Principal principal){
        model.addAttribute("results", daoService.getBooks(principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model, Principal principal){
        model.addAttribute("results", daoService.getAuthor(id, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Cards";
    }

    @GetMapping("/bookinfo/{isbn13}")
    public String singleBook(@PathVariable("isbn13") String isbn13, Model model, Principal principal){
        model.addAttribute("results", daoService.getSingleBook(isbn13));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("query", new Query());
        return "Book-Info";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, Principal principal){
        daoService.deleteBook(id, principal.getName());
        return "redirect:/books";
    }

    @GetMapping("/material")
    public String www(Model model, Principal principal){
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        return "Material2";
    }
}