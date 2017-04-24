package com.rich.bryan.dao;

import org.json.JSONObject;

public interface GoogleBooksApiDao {
    JSONObject googleBooksApiSearch(String query) throws Exception;
}
