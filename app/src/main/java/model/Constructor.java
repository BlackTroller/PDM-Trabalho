package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Constructor {
    @SerializedName("constructorId")
    @Expose
    public String constructorId;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("nationality")
    @Expose
    public String nationality;
}
