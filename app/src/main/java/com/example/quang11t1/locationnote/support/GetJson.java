package com.example.quang11t1.locationnote.support;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJson {

    HttpURLConnection urlConnection;
    public String getStringJson(String link) {

        StringBuilder result = new StringBuilder();
        System.out.println("link receive :"+link);
        try {
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            System.out.println(" khong the lay dw lieu thanh cong");
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }
}
