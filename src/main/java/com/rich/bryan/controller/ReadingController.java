package com.rich.bryan.controller;

import com.rich.bryan.dto.Progress;
import com.rich.bryan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadingController {

    private BookService bookService;

    @Autowired
    public ReadingController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/progress")
    public Progress reading(@RequestParam("buid") Long buid){
        return bookService.getProgress(buid);
    }
}
