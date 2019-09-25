package com.SkyzoneGroup.MadhusudhanCreation.Rest;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by kevalt on 22-04-2017.
 */

public interface ApiInterface {

    //     alll categories Collections
    @GET("web_service.php?f=get_categories")
    Call<Responce> getall_categories();

    //     alll sub categories Collections

    @FormUrlEncoded
    @POST("web_service.php?f=get_sub_categories")
    Call<ResponceSub> Getcatwisesubcat(@Field("cat_id")String catid);



    // alll sub categories  wish categories Collections

    @FormUrlEncoded
    @POST("web_service.php?f=get_post_all_subcategory")
    Call<ResponceSub> Getsubcat(@Field("cat_id")String catid);

// alll sub categories  wish product Collections

    @FormUrlEncoded
    @POST("web_service.php?f=category_wise_product")
    Call<CATResponce> GetSubCategoryWiseProduct(@Field("cat_id")String catid,@Field("customer_id") String user_id);


    @FormUrlEncoded
    @POST("web_service.php?f=single_product_details")
    Call<Responce> Getproductinfo(@Field("product_id") String proid);

    @FormUrlEncoded
    @POST("web_service.php?f=customer_login")
    Call<Responce> checkLogin(@Field("email") String email, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("web_service.php?f=customer_register")
//    Call<Responce> registration(@Field("firstname") String firstname, @Field("lastname") String lastname, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("web_service.php?f=customer_from_shipping_address")
    Call<Responce> Addadress(@Field("customer_id") String user_id,
                             @Field("firstname") String fName,
                             @Field("lastname") String lName,
                             @Field("telephone") String phone_no,
                             @Field("address") String add_one,
                             @Field("address_1") String add_two,
                             @Field("city") String city,
                             @Field("state") String state,
                             @Field("postcode") String pincode,
                             @Field("country") String country,
                             @Field("email") String user_email);

    @FormUrlEncoded
    @POST("web_service.php?f=get_customer_address")
    Call<Responce> getalladdress(@Field("customer_id") String user_id);

    @FormUrlEncoded
    @POST("web_service.php?f=update_customer_address")
    Call<Responce> updateddadress(@Field("entity_id") String addressid,
                                  @Field("firstname") String fName,
                                  @Field("lastname") String lName,
                                  @Field("telephone") String phone_no,
                                  @Field("address") String add_one,
                                  @Field("address_1") String add_two,
                                  @Field("city") String city,
                                  @Field("region") String state,
                                  @Field("postcode") String pincode,
                                  @Field("country") String country);

    @FormUrlEncoded
    @POST("web_service.php?f=add_to_cart")
    Call<Responce> addToCart(@Field("customer_id") String user_id,
                             @Field("product_id") String product_id,
                             @Field("quantity") String quantity,
                             @Field("option_id") String optionsid,
                             @Field("sub_option_id") String suboptionid);

    @FormUrlEncoded
    @POST("web_service.php?f=get_cart_detail")
    Call<ResponceCartItem> getCartDetail(@Field("customer_id") String user_id);

    @FormUrlEncoded
    @POST("web_service.php?f=delete_cart_item")
    Call<Responce> RemoveToCart(@Field("customer_id") String user_id,
                                @Field("item_id") String itemId);


    @FormUrlEncoded
    @POST("web_service.php?f=modify_cart")
    Call<Responce> updateToCart(@Field("customer_id") String user_id,
                                @Field("item_id") String itemId,
                                @Field("quantity") int i);

    @FormUrlEncoded
    @POST("web_service.php?f=customer_address_delete")
    Call<Responce> deleteaddress(@Field("entity_id") String entityId);

    @FormUrlEncoded
    @POST("web_service.php?f=change_password")
    Call<Responce> changepassword(@Field("customer_id") String user_id,
                                  @Field("newpassword") String strPass2);

    @FormUrlEncoded
    @POST("web_service.php?f=customer_all_order_list")
    Call<Responce> getALLMyOrder(@Field("customer_id") String user_id);

    @FormUrlEncoded
    @POST("web_service.php?f=placed_order")
    Call<Responce> getPlacedOrder(@Field("customer_id")String user_id,
                                  @Field("to_entity_id")String addressid,
                                  @Field("from_entity_id")String toaddressid);
//    Call<Responce> getPlacedOrder(@Field("customer_id") String user_id,
//                                  @Field("entity_id") String addressid);


    @FormUrlEncoded
    @POST("web_service.php?f=customer_single_order_detail")
    Call<MyOrderDetailResponse> getMyOrderDetail(@Field("order_id") String odid);

    @GET("web_service.php?f=all_categories")
    Call<Responce> getAllCategory();

    @GET("web_service.php?f=third_step_subcategory")
    Call<Responce> getallsubproduct();

    @FormUrlEncoded
    @POST("web_service.php?f=get_post_all_subcategory")
    Call<Responce> GetcatProduct(@Field("cat_id")String catid);


    @GET("web_service.php?f=get_all_products")
    Call<CATResponce> GetOffersubcategory();

    @GET("web_service.php?f=get_main_slider")
    Call<Responce> getOfferSlider();

    @GET("web_service.php?f=get_offer_slider")
    Call<Responce> getMianSlider();

    @FormUrlEncoded
    @POST("web_service.php?f=customer_forget_password")
    Call<Responce> forGetPassWord(@Field("email") String mail);

    @FormUrlEncoded
    @POST("web_service.php?f=check_checkondelivery")
    Call<Responce> checkondelivery(@Field("postcode")String postcode);

    @FormUrlEncoded
    @POST("web_service.php?f=search")
    Call<Responce> search(@Field("searchstring")String query);

    @FormUrlEncoded
    @POST("web_service.php?f=get_id_wise_address")
    Call<Responce> GetAutoFeldData(@Field("entity_id")String entityId);

    @FormUrlEncoded
    @POST ("web_service.php?f=get_customer_detail")
    Call<Responce> get_customer_detail(@Field("customer_id")String user_id);

    @FormUrlEncoded
    @POST ("web_service.php?f=edit_customer_profile")
//    Call<Responce> edit_customer_profile(String USER_ID, @Field("customer_id") String user_id,
//                                         @Field("firstname") String fname,
//                                         @Field("lastname") String lName);

    Call<Responce> edit_customer_profile(@Field("customer_id")String user_id,
                                         @Field("firstname") String fname,
                                         @Field("lastname") String lName,
                                         @Field("mobile") String phone_no);


    @FormUrlEncoded
    @POST ("web_service.php?f=placed_order_ccavenue")
    Call<Responce> getOrderccavenueResponse(@Field("customer_id")String user_id,
                                            @Field("to_entity_id")String addressid,
                                            @Field("from_entity_id")String toaddressid);
//    Call<Responce> getOrderccavenueResponse(@Field("customer_id")String user_id,
//                                            @Field("entity_id")String addressid);

    @FormUrlEncoded
    @POST ("web_service.php?f=clear_cart")
    Call<Responce> clearCart(@Field("customer_id")String user_id);

    @FormUrlEncoded
    @POST ("web_service.php?f=calcute_shipping_charges")
    Call<Responce> getshippingcharge(@Field("weight")String s);

    @FormUrlEncoded
    @POST ("web_service.php?f=get_all_shipping_rates_for_cart")
    Call<Responce> get_all_shipping_rates_for_cart(@Field("customer_id")String user_id);

    @GET("web_service.php?f=get_cashondelivery_charges")
    Call<ResponceCartItem> getcod();



    @FormUrlEncoded
    @POST("web_service.php?f=customer_register")
    Call<Responce> registration(@Field("firstname")String fname,@Field("lastname") String lname, @Field("email") String email,@Field("password") String password);//,@Field("mobile") String phoneno);

    @FormUrlEncoded
    @POST("web_service.php?f=share_catalog_image")
    Call<Responce> GetcatShareProduct(@Field("cat_id")String sub_cat);

    @FormUrlEncoded
    @POST("web_service.php?f=delete_order")
    Call<ResponceCartItem> getOrderDelete(@Field("order_id")String orderid);

    @FormUrlEncoded
    @POST("web_service.php?f=get_cart_detail_online")
    Call<ResponcePymentMethord> getCartPymentOnlineDetail(@Field("customer_id")String user_id);

    @FormUrlEncoded
    @POST("web_service.php?f=get_cart_detail_cod")
    Call<ResponcePymentMethord> getCartPymentCODDetail(@Field("customer_id")String user_id);

    @FormUrlEncoded
    @POST("web_service.php?f=save_notification_key")
    Call<Responce> GetNotification(@Field("reg_id") String token);


    @GET("web_service.php?f=delete_images")
    Call<ResponceCartItem> getimagedelete();

    @FormUrlEncoded
    @POST("web_service.php?f=add_prodcut_customer_wishlist")
    Call<ResponceSub> addWish(@Field("customer_id") String customer_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("web_service.php?f=delete_customer_wishlist")
    Call<ResponceSub> removeWishList(@Field("customer_id") String customer_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("web_service.php?f=get_customer_wishlist")
    Call<ResponceSub> WishList(@Field("customer_id")  String USER_ID);


    @FormUrlEncoded
    @POST("web_service.php?f=get_related_product")
    Call<ResponceSub> getSimilarProduct(@Field("product_id")  String productId, @Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("web_service.php?f=send_product_inquiry")
    Call<CATResponce> Inquiry(@Field("name") String name,
                          @Field("product_url") String pro_url,
                          @Field("email") String email,
                          @Field("mobile") String mobile_no,
                              @Field("message") String msg);

//    Call<Responce> registration(@Field("firstname") String name,@Field("lastname")  String lname, @Field("email") String mail,@Field("mobile")String phoneno,@Field("password") String password);
}
