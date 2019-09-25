package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;

import java.util.List;

/**
 * Created by kevalt on 4/3/2018.
 */

public class ResponceSub {
    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Subcatdatamodel> data = null;

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

    public List<Subcatdatamodel> getData() {
        return data;
    }

    public void setData(List<Subcatdatamodel> data) {
        this.data = data;
    }
}
