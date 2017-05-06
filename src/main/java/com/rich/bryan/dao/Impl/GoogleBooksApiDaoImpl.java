package com.rich.bryan.dao.Impl;

import com.rich.bryan.dao.GoogleBooksApiDao;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
public class GoogleBooksApiDaoImpl implements GoogleBooksApiDao {

    @Override
    public JSONObject googleBooksApiSearch(String query) throws Exception {
        String urlString = "https://www.googleapis.com/books/v1/volumes/" +
                URLEncoder.encode(query, "UTF-8");
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("UserEntity-Agent", USER_AGENT);

        // Reading response from input Stream
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}
