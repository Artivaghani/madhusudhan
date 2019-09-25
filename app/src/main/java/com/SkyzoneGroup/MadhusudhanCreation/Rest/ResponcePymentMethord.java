package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponcePymentMethord {
    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_charge")
    @Expose
    private String productCharge;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("cod_charge")
    @Expose
    private String codCharge;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("data")
    @Expose
    private List<DatumPymentMethord> data = null;

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

    public String getProductCharge() {
        return productCharge;
    }

    public void setProductCharge(String productCharge) {
        this.productCharge = productCharge;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getCodCharge() {
        return codCharge;
    }

    public void setCodCharge(String codCharge) {
        this.codCharge = codCharge;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<DatumPymentMethord> getData() {
        return data;
    }

    public void setData(List<DatumPymentMethord> data) {
        this.data = data;
    }

}
