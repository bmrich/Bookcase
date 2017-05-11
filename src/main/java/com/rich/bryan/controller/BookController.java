package com.rich.bryan.controller;

import com.rich.bryan.services.BookService;
import com.rich.bryan.services.SaveBookService;
import com.rich.bryan.services.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private ShelvesService shelvesService;
    private SaveBookService saveBookService;

    @Autowired
    public BookController(BookService bookService, ShelvesService shelvesService, SaveBookService saveBookService) {
        this.bookService = bookService;
        this.shelvesService = shelvesService;
        this.saveBookService = saveBookService;
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestParam("id") String id, Principal principal){
        return saveBookService.saveBook(id, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBook(@PathVariable("id") Long id, Principal principal){
        bookService.deleteBook(id, principal.getName());
    }

    @GetMapping("/info/{isbn13}")
    public String singleBook(@PathVariable("isbn13") String isbn13, Model model, Principal principal){
        model.addAttribute("results", bookService.getSingleBook(isbn13, principal.getName()));
        model.addAttribute("shelves", shelvesService.getShelves(principal.getName()));
        model.addAttribute("isOnShelf", shelvesService.getShelvesBookIsOn(principal.getName(),isbn13));
        model.addAttribute("readingState", shelvesService.getReadingState(principal.getName(),isbn13));
        model.addAttribute("numMap", shelvesService.numBooksOnShelf(principal.getName()));
        return "Book_Info";
    }
}