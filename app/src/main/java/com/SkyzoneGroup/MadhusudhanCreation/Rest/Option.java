package com.SkyzoneGroup.MadhusudhanCreation.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevalt on 4/10/2018.
 */

public class Option {

    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("sub_option_id")
    @Expose
    private String subOptionId;
    @SerializedName("option_label")
    @Expose
    private String optionLabel;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getSubOptionId() {
        return subOptionId;
    }

    public void setSubOptionId(String subOptionId) {
        this.subOptionId = subOptionId;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

}
