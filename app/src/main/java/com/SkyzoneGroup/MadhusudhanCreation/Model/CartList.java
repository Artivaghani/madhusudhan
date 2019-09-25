package com.SkyzoneGroup.MadhusudhanCreation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevalt on 4/13/2018.
 */

public class CartList {
    /// CART LIST
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("Sub_total")
    @Expose
    private String subTotal;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("you_save")
    @Expose
    private String youSave;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getYouSave() {
        return youSave;
    }

    public void setYouSave(String youSave) {
        this.youSave = youSave;
    }
}
