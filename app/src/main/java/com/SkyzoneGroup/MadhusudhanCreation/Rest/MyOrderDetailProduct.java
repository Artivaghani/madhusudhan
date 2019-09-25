package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevalt on 15-05-2017.
 */

public class MyOrderDetailProduct
{
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("product_qty")
    @Expose
    private String productQty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("Size")
    @Expose
    private String size;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    public String getSize() {
        return size;
    }
    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public void setSize(String size) {
        this.size = size;
    }
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

    public String getProductQty() {
        return productQty;
    }
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

}
