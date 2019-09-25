package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kevalt on 08-08-2017.
 */

public class Datum {

    @SerializedName("supplier")
    @Expose
    private String supplier;
    @SerializedName("street_1")
    @Expose
    private String street1;
    @SerializedName("street_2")
    @Expose
    private String street2;
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }


    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("multiple_images")
    @Expose
    private List<MultipleImage> multipleImages = null;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<MultipleImage> getMultipleImages() {
        return multipleImages;
    }

    public void setMultipleImages(List<MultipleImage> multipleImages) {
        this.multipleImages = multipleImages;
    }


//pincode cod cheked


    @SerializedName("slider_id")
    @Expose
    private String sliderId;
    @SerializedName("weblink")
    @Expose
    private String weblink;



    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }




    // cat_list
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("cat_title")
    @Expose
    private String catTitle;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_image")
    @Expose
    private String catImage;
    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Datum(String cat_name, String cat_id) {
        this.catName=cat_name;
        this.catId=cat_id;
    }
    public Datum() {

    }
    public String getCatTitle() {
        return catTitle;
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

    // cat_wish_product_list

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
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("availability")
    @Expose
    private String availability;

    @SerializedName("product_url")
    @Expose
    private String productUrl;
    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Integer getDiscount() {
        return discount;
    }
    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    // single pro_info

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sku")
    @Expose
    private String sku;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    //user login
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // get all address


    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getSelected() {
        return isSelected;
    }


    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("entity_type_id")
    @Expose
    private String entityTypeId;
    @SerializedName("attribute_set_id")
    @Expose
    private String attributeSetId;
    @SerializedName("increment_id")
    @Expose
    private Object incrementId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("street")
    @Expose
    private String street;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(String entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(String attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Object getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(Object incrementId) {
        this.incrementId = incrementId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    //Add to cart list

    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;


    @SerializedName("Sub_total")
    @Expose
    private String subTotal;
    @SerializedName("you_save")
    @Expose
    private String youSave;
    @SerializedName("custom_mobile")
    @Expose
    private String customMobile;
    public String getCustomMobile() {
        return customMobile;
    }

    public void setCustomMobile(String customMobile) {
        this.customMobile = customMobile;
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


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }


    public String getYouSave() {
        return youSave;
    }

    public void setYouSave(String youSave) {
        this.youSave = youSave;
    }


    //// my all order




    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_qty")
    @Expose
    private String orderQty;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }





    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("total_ordr_qty")
    @Expose
    private String totalOrdrQty;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    boolean checked;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalOrdrQty() {
        return totalOrdrQty;
    }

    public void setTotalOrdrQty(String totalOrdrQty) {
        this.totalOrdrQty = totalOrdrQty;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }


    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }


    @SerializedName("total_design")
    @Expose
    private Integer totalDesign;
    @SerializedName("starting_price")
    @Expose
    private String startingPrice;


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



    //// user info get
    @SerializedName("website_id")
    @Expose
    private String websiteId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("store_id")
    @Expose
    private String storeId;



    @SerializedName("disable_auto_group_change")
    @Expose
    private String disableAutoGroupChange;
    @SerializedName("created_in")
    @Expose
    private String createdIn;

    @SerializedName("password_hash")
    @Expose
    private String passwordHash;
    @SerializedName("default_shipping")
    @Expose
    private String defaultShipping;



    public String getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(String websiteId) {
        this.websiteId = websiteId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }



    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }




    public String getDisableAutoGroupChange() {
        return disableAutoGroupChange;
    }

    public void setDisableAutoGroupChange(String disableAutoGroupChange) {
        this.disableAutoGroupChange = disableAutoGroupChange;
    }

    public String getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(String createdIn) {
        this.createdIn = createdIn;
    }



    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(String defaultShipping) {
        this.defaultShipping = defaultShipping;
    }




}
