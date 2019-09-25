package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kevalt on 15-05-2017.
 */

public class MyOrderDetail
{
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("address")
    @Expose
    private AddressInfo address;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("cod_charge")
    @Expose
    private String codCharge;
    @SerializedName("row_total")
    @Expose
    private String rowTotal;
    @SerializedName("shipping_address")
    @Expose
    private ShippingAddress shippingAddress;
    @SerializedName("billing_address")
    @Expose
    private BillingAddress billingAddress;
    @SerializedName("shipping_charges")
    @Expose
    private String shippingCharges;
    @SerializedName("products")
    @Expose
    private List<MyOrderDetailProduct> products = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCodCharge() {
        return codCharge;
    }

    public void setCodCharge(String codCharge) {
        this.codCharge = codCharge;
    }

    public String getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(String shippingCharges) {
        this.shippingCharges = shippingCharges;
    }
    public String getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(String rowTotal) {
        this.rowTotal = rowTotal;
    }


    public String getOrderStatus() {
        return orderStatus;
    }
    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<MyOrderDetailProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MyOrderDetailProduct> products) {
        this.products = products;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }
}
