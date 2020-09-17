package me.gavin.riverislandapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public List<Product> fetchItems() {
        List<Product> items = new ArrayList<>();
        try {
            String url = Uri.parse("https://static-ri.ristack-3.nn4maws.net/v1/plp/en_gb/2506/products.json")
                    .buildUpon()
//                    .appendQueryParameter("method", "flickr.photos.getRecent")
//                    .appendQueryParameter("api_key", API_KEY)
//                    .appendQueryParameter("format", "json")
//                    .appendQueryParameter("nojsoncallback", "1")
//                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    public List<Product> parseItems(List<Product> products, JSONObject jsonBody)
    throws IOException, JSONException {
        JSONArray jsonProdArray = jsonBody.getJSONArray("Products");
        for (int i = 0; i < jsonProdArray.length(); i++) {
            JSONObject jsonObject = jsonProdArray.getJSONObject(i);
            Product product = new Product();
            // map the json data to a product object
            product.prodid = jsonObject.getString("prodid");

            // add item to list
            products.add(product);
        }
        return products;
    }

//    private void parseItems(List<Product> items, JSONObject jsonBody)
//            throws IOException, JSONException {
//        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
//        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
//        for (int i = 0; i < photoJsonArray.length(); i++) {
//            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
//            Product item = new Product();
//            item.setId(photoJsonObject.getString("id"));
//            item.setCaption(photoJsonObject.getString("title"));
//            if (!photoJsonObject.has("url_s")) {
//                continue;
//            }
//            item.setUrl(photoJsonObject.getString("url_s"));
//            items.add(item);
//        }
//    }

}
