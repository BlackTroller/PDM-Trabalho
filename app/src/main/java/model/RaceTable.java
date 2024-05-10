package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaceTable {
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("Races")
    @Expose
    public List<Race> races = null;
}
