package com.rich.bryan.controller;

import com.rich.bryan.entity.BooksUsers;
import com.rich.bryan.services.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shelf")
public class ShelfController {

    private ShelvesService shelvesService;

    @Autowired
    public ShelfController(ShelvesService shelvesService) {
        this.shelvesService = shelvesService;
    }

    @PostMapping("/create")
    public void create(@RequestParam("shelfName") String shelfName, Principal principal){
        shelvesService.createShelf(principal.getName(), shelfName);
    }

    @PostMapping("/add")
    public List<Object[]> add(@RequestParam("id") Long id,
                              @RequestParam("shelfName") String shelfName,
                              Principal principal){

        shelvesService.addBooktoShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @DeleteMapping("/remove/{shelfName}/{id}")
    public List<Object[]> remove(@PathVariable("id") Long id,
                                 @PathVariable("shelfName") String shelfName,
                                 Principal principal){

        shelvesService.removeFromShelf(principal.getName(), shelfName, id);
        return shelvesService.getShelves(principal.getName());
    }

    @PostMapping("/rename")
    public void renameShelf(@RequestParam("name") String shelfName,
                            @RequestParam("new") String newShelfName,
                            Principal principal){

        shelvesService.renameShelf(principal.getName(), shelfName, newShelfName);
    }

    @DeleteMapping("/{name}")
    public void deleteShelf(@PathVariable("name") String shelfName, Principal principal){
        shelvesService.deleteShelf(principal.getName(), shelfName);
    }

    @PostMapping("/state")
    public Map<String, Integer> state(BooksUsers bu, Principal principal){
        shelvesService.updateState(bu);
        return shelvesService.numBooksOnShelf(principal.getName());
    }

    @PostMapping("/progress")
    public Integer updateCR(BooksUsers bu){
        shelvesService.updateState(bu);
        return shelvesService.getCurrentPage(bu.getId());
    }
}