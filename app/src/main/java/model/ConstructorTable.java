package model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstructorTable {
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("Constructors")
    @Expose
    public List<Constructor> constructors = null;

}
