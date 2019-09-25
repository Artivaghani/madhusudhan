package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;

import java.util.List;

/**
 * Created by kevalt on 08-08-2017.
 */

public class Responce {
    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("size")
    @Expose
    private List<String> size = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    private List<CartList> dataa = null;

    public List<String> getSize() {
        return size;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

}
