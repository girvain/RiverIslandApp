package me.gavin.riverislandapp;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.gavin.riverislandapp.model.Product;

public class ProductFetch {

    private static final String TAG = "productFetch";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // ------------- Methods for parsing data using GSON library ------------------- >

    public List<Product> fetchAndFilterByTrending() {
        return fetchItems().stream()
                .filter(product -> product.isTrending() == true)
                .collect(Collectors.toList());
    }

    public List<Product> fetchAndFilterByCategory(String category) {
        return fetchItems().stream()
                .filter(product -> isWordInList(product.getCategory(), category))
                .collect(Collectors.toList());
    }

    public List<Product> filterByCategory(List<Product> products, String category) {
        return products.stream()
                .filter(product -> isWordInList(product.getCategory(), category))
                .collect(Collectors.toList());
    }

    public List<Product> filterByColour(List<Product> products, String colour) {
        return products.stream()
                .filter(product -> isWordInList(product.getColour(), colour))
                .collect(Collectors.toList());
    }

    public List<Product> filterBySize(List<Product> products, String size) {
        return products.stream()
                .filter(product -> isWordInList(product.getSizes(), size))
                .collect(Collectors.toList());
    }

    /**
     * Method to check if a word is in a string of words seperated with a comma. It works
     * by checking if the string contains a comma then spliting it into an array or words
     * and checking if each of these words is equal to the seconds argument of the method.
     * If there is no comma, the method performs a simple .equals check.
     * @param words
     * @param word
     * @return
     */
    public boolean isWordInList(String words, String word) {
        if (words.contains(",")) {
            String[] wordList = words.split(",");
            for (String w : wordList) {
                if (w.equals(word)) return true;
            }
        } else {
            if (words.equals(word)) return true;
        }
        return false;
    }

    public List<Product> fetchItems() {
        List<Product> items = new ArrayList<>();
        try {
            String url = "https://static-ri.ristack-3.nn4maws.net/v1/plp/en_gb/2506/products.json";
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            //JSONObject jsonBody = new JSONObject(jsonString);
            items = parseItems(jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return items;
    }


    public List<Product> parseItems(String jsonObject) {
        Gson g = new GsonBuilder()
                .registerTypeAdapter(Product[].class, new Deserializer<Product[]>())
                .create();

        List<Product> products = Arrays.asList(g.fromJson(jsonObject, Product[].class));
        return products;
    }


    // ------------- Methods for parsing data using simple JSON library ------------------- >

//    public List<Product> fetchItems() {
//        List<Product> items = new ArrayList<>();
//        try {
//            String url = Uri.parse("https://static-ri.ristack-3.nn4maws.net/v1/plp/en_gb/2506/products.json")
//                    .buildUpon()
//                    .build().toString();
//            String jsonString = getUrlString(url);
//            Log.i(TAG, "Received JSON: " + jsonString);
//            JSONObject jsonBody = new JSONObject(jsonString);
//            parseItems(items, jsonBody);
//        } catch (IOException ioe) {
//            Log.e(TAG, "Failed to fetch items", ioe);
//        } catch (JSONException je) {
//            Log.e(TAG, "Failed to parse JSON", je);
//        }
//        return items;
//    }
//
//    public List<Product> parseItems(List<Product> products, JSONObject jsonBody)
//    throws IOException, JSONException {
//        JSONArray jsonProdArray = jsonBody.getJSONArray("Products");
//        for (int i = 0; i < jsonProdArray.length(); i++) {
//            JSONObject jsonObject = jsonProdArray.getJSONObject(i);
//            Product product = new Product();
//            // map the json data to a product object
//            product.prodid = jsonObject.getString("prodid");
//
//            // add item to list
//            products.add(product);
//        }
//        return products;
//    }

}
