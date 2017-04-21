package com.rich.bryan.dao;

import com.rich.bryan.dto.SearchResult;

public interface NewBookDao {
    void newBook(SearchResult searchResult, String username);
    void newBook(Long id, String username);
}
