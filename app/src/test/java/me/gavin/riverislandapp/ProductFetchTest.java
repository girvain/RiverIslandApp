package me.gavin.riverislandapp;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import me.gavin.riverislandapp.model.Product;

//TODO: write gson test
//TODO: write fetch test, possibly use mock for networking part
public class ProductFetchTest {

    ProductFetch mProductFetch;
    JSONObject mJSONObject;
    String jsonString =
            "    {\n" +
            "      \"name\": \"Light purple knit cardigan and bralet set\",\n" +
            "      \"cost\": \"45\",\n" +
            "      \"wascost\": \"\",\n" +
            "      \"costEUR\": \"60\",\n" +
            "      \"wascostEUR\": \"\",\n" +
            "      \"costWER\": \"60\",\n" +
            "      \"wascostWER\": \"\",\n" +
            "      \"costUSD\": \"90\",\n" +
            "      \"wascostUSD\": \"\",\n" +
            "      \"costAUD\": \"90\",\n" +
            "      \"wascostAUD\": \"\",\n" +
            "      \"costSEK\": \"649\",\n" +
            "      \"wascostSEK\": \"\",\n" +
            "      \"costWEK\": \"649\",\n" +
            "      \"wascostWEK\": \"\",\n" +
            "      \"prodid\": \"790469\",\n" +
            "      \"promotionImage\": \"c20140613-back-in-stock-icon-resp\",\n" +
            "      \"mediaIcon\": \"c20140613-back-in-stock-icon-resp\",\n" +
            "      \"colour\": \"Purple\",\n" +
            "      \"sizes\": \"XS,S\",\n" +
            "      \"altImage\": \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_rollover\",\n" +
            "      \"dateSort\": 484,\n" +
            "      \"allImages\": [\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_main\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_rollover\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_back\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_alt1\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_alt2\"\n" +
            "      ],\n" +
            "      \"swatches\": [\n" +
            "        {\n" +
            "          \"productId\": \"790469\",\n" +
            "          \"image\": \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/light-purple-knit-cardigan-and-bralet-set_790469_swatch\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"productId\": \"790470\",\n" +
            "          \"image\": \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/790470_swatch\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"isNewArrival\": true,\n" +
            "      \"isTrending\": true,\n" +
            "      \"category\": \"Tops,Knitwear\",\n" +
            "      \"fit\": \"Main Collection\",\n" +
            "      \"design\": \"Plain\"\n" +
            "    }";




    @Before
    public void setUp() throws Exception {
        mProductFetch = new ProductFetch();
        mJSONObject = new JSONObject(jsonString);

    }

    @Test
    public void fetchItemsTest() {
        List<Product> results = new ArrayList<>();
        //when((anyString())).thenReturn();
        results = mProductFetch.fetchItems();
        assertEquals("790469", results.get(0).prodid);
    }

    @Test
    public void parseItemsTest() throws Exception{
        List<Product> result = new ArrayList<>();
        result = mProductFetch.parseItems(result, mJSONObject);

        assertEquals("790469", result.get(0).prodid);
    }

    /**
     * Test to see if the conversion of JSON string to Product contained all data.
     * Used the debugger to check all fields had a value.
     */
    @Test
    public void gsonJsonToProductTest() {
        Gson g = new Gson();
        Product p = g.fromJson(jsonString, Product.class);

        assertEquals("790469", p.getProdid());
        assertEquals(5, p.allImages.size());
    }
}