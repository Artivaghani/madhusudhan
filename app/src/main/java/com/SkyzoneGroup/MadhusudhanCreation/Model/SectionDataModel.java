package com.SkyzoneGroup.MadhusudhanCreation.Model;

import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.ArrayList;

public class SectionDataModel {



    private String headerTitle;

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    private String headerId;
    private ArrayList<Datum> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<Datum> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Datum> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Datum> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }



}
