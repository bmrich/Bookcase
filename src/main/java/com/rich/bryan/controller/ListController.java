package com.rich.bryan.controller;

import com.rich.bryan.services.BookService;
import com.rich.bryan.services.ShelvesService;
import com.rich.bryan.services.Utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/list")
public class ListController {

    private BookService bookService;
    private ShelvesService shelvesService;

    @Autowired
    public ListController(BookService bookService, ShelvesService shelvesService) {
        this.bookService = bookService;
        this.shelvesService = shelvesService;
    }

    @GetMapping(value = "/myBooks")
    public String books(@RequestParam(value = "sort", required = false, defaultValue = "1") Integer sort,
                        Model model, Principal principal){

        model.addAttribute("results", bookService.getBooks(principal.getName(), Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "My Books");
        model.addAttribute("dropdown", "books");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());
        return "Cards";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam("id") Long id,
                            @RequestParam(value = "sort", required = false, defaultValue = "1") Integer sort,
                            Model model, Principal principal){

        Object[] objects = bookService.getAuthor(id, principal.getName(), Sort.valueOf(sort));

        model.addAttribute("results", objects[1]);
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", objects[0]);
        model.addAttribute("authorId", id);
        model.addAttribute("dropdown", "author");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());

        return "Cards";
    }

    @GetMapping("/shelf")
    public String getShelf(@RequestParam("name") String shelfName,
                           @RequestParam(value = "sort", required = false, defaultValue = "1") Integer sort,
                           Principal principal, Model model){

        model.addAttribute("results", shelvesService.getShelf(principal.getName(), shelfName, Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", shelfName);
        model.addAttribute("showOptions", true);
        model.addAttribute("dropdown", "shelf");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());

        return "Cards";
    }

    @GetMapping("/reading")
    public String reading(@RequestParam(value = "sort", required = false, defaultValue = "8") Integer sort,
                          Principal principal, Model model){

        model.addAttribute("results", shelvesService.getPerm(principal.getName(), "CR", Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "Currently Reading");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());

        return "Currently_Reading_Cards";
    }

    @GetMapping("/toRead")
    public String toRead(@RequestParam(value = "sort", required = false, defaultValue = "1") Integer sort,
                          Principal principal, Model model){

        model.addAttribute("results", shelvesService.getPerm(principal.getName(), "TR", Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "To Read");
        model.addAttribute("dropdown", "toRead");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());

        return "Cards";
    }

    @GetMapping("/completed")
    public String completed(@RequestParam(value = "sort", required = false, defaultValue = "9") Integer sort,
                         Principal principal, Model model){

        model.addAttribute("results", shelvesService.getPerm(principal.getName(), "R", Sort.valueOf(sort)));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        model.addAttribute("pageName", "Completed");
        model.addAttribute("dropdown", "completed");
        model.addAttribute("sortName", Sort.valueOf(sort).toString());

        return "Completed_Cards";
    }
}
