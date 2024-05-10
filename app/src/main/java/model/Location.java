package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String _long;
    @SerializedName("locality")
    @Expose
    public String locality;
    @SerializedName("country")
    @Expose
    public String country;
}
