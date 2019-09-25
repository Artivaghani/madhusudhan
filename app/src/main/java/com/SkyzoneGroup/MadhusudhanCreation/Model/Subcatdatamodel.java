package com.SkyzoneGroup.MadhusudhanCreation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevalt on 4/3/2018.
 */

public class Subcatdatamodel {

    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_image")
    @Expose
    private String catImage;
    @SerializedName("cat_title")
    @Expose
    private String catTitle;
    @SerializedName("total_design")
    @Expose
    private Integer totalDesign;
    @SerializedName("starting_price")
    @Expose
    private String startingPrice;
    @SerializedName("discount")
    @Expose
    private Integer discount;


    public String getCatTitle() {
        return catTitle;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }
    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public Integer getTotalDesign() {
        return totalDesign;
    }

    public void setTotalDesign(Integer totalDesign) {
        this.totalDesign = totalDesign;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("is_wishlist")
    @Expose
    private Integer isWishlist;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Integer getIsWishlist() {
        return isWishlist;
    }

    public void setIsWishlist(Integer isWishlist) {
        this.isWishlist = isWishlist;
    }


}
