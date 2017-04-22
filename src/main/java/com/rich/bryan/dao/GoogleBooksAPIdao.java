package com.rich.bryan.dao;

import org.json.JSONObject;

public interface GoogleBooksAPIdao {
    JSONObject googleBooksApiSearch(String query) throws Exception;
}
