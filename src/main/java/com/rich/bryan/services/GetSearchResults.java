package com.rich.bryan.services;

import com.rich.bryan.dto.SearchResult;

import java.util.List;

public interface GetSearchResults {
    List<SearchResult> searchResults(String query, String name);
    void saveSearchResult(Integer id, String username);
}
