package me.gavin.riverislandapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * I used this website to convert the JSON to a pojo
 * https://json2csharp.com/json-to-pojo
 */

public class Product implements Serializable {
    private String name;
    private String cost;
    private String wascost;
    private String costEUR;
    private String wascostEUR;
    private String costWER;
    private String wascostWER;
    private String costUSD;
    private String wascostUSD;
    private String costAUD;
    private String wascostAUD;
    private String costSEK;
    private String wascostSEK;
    private String costWEK;
    private String wascostWEK;
    private String prodid;
    private String promotionImage;
    private String mediaIcon;
    private String colour;
    private String sizes;
    private String altImage;
    private int dateSort;
    private List<String> allImages;
    private List<Swatch> swatches;
    private boolean isNewArrival;
    private boolean isTrending;
    private String category;
    private String fit;
    private String design;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getWascost() {
        return wascost;
    }

    public void setWascost(String wascost) {
        this.wascost = wascost;
    }

    public String getCostEUR() {
        return costEUR;
    }

    public void setCostEUR(String costEUR) {
        this.costEUR = costEUR;
    }

    public String getWascostEUR() {
        return wascostEUR;
    }

    public void setWascostEUR(String wascostEUR) {
        this.wascostEUR = wascostEUR;
    }

    public String getCostWER() {
        return costWER;
    }

    public void setCostWER(String costWER) {
        this.costWER = costWER;
    }

    public String getWascostWER() {
        return wascostWER;
    }

    public void setWascostWER(String wascostWER) {
        this.wascostWER = wascostWER;
    }

    public String getCostUSD() {
        return costUSD;
    }

    public void setCostUSD(String costUSD) {
        this.costUSD = costUSD;
    }

    public String getWascostUSD() {
        return wascostUSD;
    }

    public void setWascostUSD(String wascostUSD) {
        this.wascostUSD = wascostUSD;
    }

    public String getCostAUD() {
        return costAUD;
    }

    public void setCostAUD(String costAUD) {
        this.costAUD = costAUD;
    }

    public String getWascostAUD() {
        return wascostAUD;
    }

    public void setWascostAUD(String wascostAUD) {
        this.wascostAUD = wascostAUD;
    }

    public String getCostSEK() {
        return costSEK;
    }

    public void setCostSEK(String costSEK) {
        this.costSEK = costSEK;
    }

    public String getWascostSEK() {
        return wascostSEK;
    }

    public void setWascostSEK(String wascostSEK) {
        this.wascostSEK = wascostSEK;
    }

    public String getCostWEK() {
        return costWEK;
    }

    public void setCostWEK(String costWEK) {
        this.costWEK = costWEK;
    }

    public String getWascostWEK() {
        return wascostWEK;
    }

    public void setWascostWEK(String wascostWEK) {
        this.wascostWEK = wascostWEK;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getPromotionImage() {
        return promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }

    public String getMediaIcon() {
        return mediaIcon;
    }

    public void setMediaIcon(String mediaIcon) {
        this.mediaIcon = mediaIcon;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getAltImage() {
        return altImage;
    }

    public void setAltImage(String altImage) {
        this.altImage = altImage;
    }

    public int getDateSort() {
        return dateSort;
    }

    public void setDateSort(int dateSort) {
        this.dateSort = dateSort;
    }

    public List<String> getAllImages() {
        return allImages;
    }

    public void setAllImages(List<String> allImages) {
        this.allImages = allImages;
    }

    public List<Swatch> getSwatches() {
        return swatches;
    }

    public void setSwatches(List<Swatch> swatches) {
        this.swatches = swatches;
    }

    public boolean isNewArrival() {
        return isNewArrival;
    }

    public void setNewArrival(boolean newArrival) {
        isNewArrival = newArrival;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }


}


