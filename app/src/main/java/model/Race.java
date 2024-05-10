package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Race {
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("round")
    @Expose
    public String round;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("raceName")
    @Expose
    public String raceName;
    @SerializedName("Circuit")
    @Expose
    public Circuit circuit;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("time")
    @Expose
    public String time;
}
