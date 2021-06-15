package com.example.co_check;

import com.google.gson.annotations.SerializedName;

public class DataPost {
    @SerializedName("negara")
    private String negara;
    @SerializedName("positif")
    private String positif;
    @SerializedName("sembuh")
    private String sembuh;
    @SerializedName("meninggal")
    private String meninggal;

    public String getNegara() {
        return negara;
    }

    public String getPositif() {
        return positif;
    }

    public String getSembuh() {
        return sembuh;
    }

    public String getMeninggal() {
        return meninggal;
    }
}
