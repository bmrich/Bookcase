package com.rich.bryan.dao;

import org.json.JSONObject;

/**
 * Created by Bryan Rich on 3/26/2017.
 */
public interface GoogleBooksAPIdao {
    JSONObject googleBooksApiSearch(String query) throws Exception;
}
