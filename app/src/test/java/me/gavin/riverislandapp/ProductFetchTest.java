package me.gavin.riverislandapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.gavin.riverislandapp.model.Product;

public class ProductFetchTest {

    ProductFetch mProductFetch;
    JSONObject mJSONObject;
    String jsonStringObj =
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

    String jsonObjArray = "{\n" +
            "  \"Products\": [\n" +
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
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Beige slim fit longline coat\",\n" +
            "      \"cost\": \"90\",\n" +
            "      \"wascost\": \"\",\n" +
            "      \"costEUR\": \"120\",\n" +
            "      \"wascostEUR\": \"\",\n" +
            "      \"costWER\": \"120\",\n" +
            "      \"wascostWER\": \"\",\n" +
            "      \"costUSD\": \"180\",\n" +
            "      \"wascostUSD\": \"\",\n" +
            "      \"costAUD\": \"180\",\n" +
            "      \"wascostAUD\": \"\",\n" +
            "      \"costSEK\": \"1249\",\n" +
            "      \"wascostSEK\": \"\",\n" +
            "      \"costWEK\": \"1249\",\n" +
            "      \"wascostWEK\": \"\",\n" +
            "      \"prodid\": \"792747\",\n" +
            "      \"promotionImage\": \"\",\n" +
            "      \"mediaIcon\": \"\",\n" +
            "      \"colour\": \"Beige\",\n" +
            "      \"sizes\": \"6,8,10,12,14,16,18\",\n" +
            "      \"altImage\": \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_rollover\",\n" +
            "      \"dateSort\": 0,\n" +
            "      \"allImages\": [\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_main\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_rollover\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_back\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_alt1\",\n" +
            "        \"https:\\/\\/images.riverisland.com\\/is\\/image\\/RiverIsland\\/beige-slim-fit-longline-coat_792747_alt2\"\n" +
            "      ],\n" +
            "      \"swatches\": [],\n" +
            "      \"isNewArrival\": true,\n" +
            "      \"isTrending\": true,\n" +
            "      \"category\": \"Coats & Jackets\",\n" +
            "      \"fit\": \"Main Collection\",\n" +
            "      \"design\": \"Plain\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";



    @Before
    public void setUp() throws Exception {
        mProductFetch = new ProductFetch();
    }

    /**
     * This test goes over the network, not using the stubs above
     */
    @Test
    public void fetchItemsTest() {
        List<Product> results = mProductFetch.fetchItems();
        assertEquals("790469", results.get(0).getProdid());
    }

    @Test
    public void parseItemsTest() throws Exception{
        List<Product> products = mProductFetch.parseItems(jsonObjArray);

        assertEquals("790469", products.get(0).getProdid());
        assertNotNull(products);
        assertEquals(5, products.get(0).getAllImages().size());
        //assertTrue(products instanceof ArrayList);
    }

    @Test
    public void fetchItemsTestWithError() throws Exception{
        List<Product> products = mProductFetch.fetchItems();
    }

    /**
     * Test to see if the conversion of JSON string to Product contained all data.
     * Used the debugger to check all fields had a value.
     */
    @Test
    public void gsonJsonToProductTest() {
        Gson g = new Gson();
        Product p = g.fromJson(jsonStringObj, Product.class);

        assertEquals("790469", p.getProdid());
        assertEquals(5, p.getAllImages().size());
    }

    @Test
    public void gsonJsonObjectWithArrayTest() {

    }

    @Test
    public void fetchAndFilterItemsTest() {
        List<Product> results = mProductFetch.fetchAndFilterItems();
        assertNotNull(results);
        assertEquals(true, results.get(0).isTrending());
        assertEquals(true, results.get(1).isTrending());
        assertEquals(true, results.get(2).isTrending());
        assertTrue(results.size() < 516); // this is the size of the pre filtered data
    }

    @Test
    public void fetchAndFilterByCategoryTest() {
        List<Product> results = mProductFetch.fetchAndFilterByCategory("Tops");
        assertNotNull(results);
        assertTrue(results.get(0).getCategory().equals("Tops"));
        assertTrue(results.get(4).getCategory().equals("Tops"));
        assertTrue(results.get(results.size()-1).getCategory().equals("Tops"));
    }
}