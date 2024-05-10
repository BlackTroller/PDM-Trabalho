package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CircuitTable {
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("Circuits")
    @Expose
    public List<Circuit> circuits = null;
}
