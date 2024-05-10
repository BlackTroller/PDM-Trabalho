package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MRData {
    @SerializedName("xmlns")
    @Expose
    public String xmlns;
    @SerializedName("series")
    @Expose
    public String series;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("limit")
    @Expose
    public String limit;
    @SerializedName("offset")
    @Expose
    public String offset;
    @SerializedName("total")
    @Expose
    public String total;
    @SerializedName("DriverTable")
    @Expose
    public DriverTable driverTable;
    @SerializedName("CircuitTable")
    @Expose
    public CircuitTable circuitTable;
    @SerializedName("ConstructorTable")
    @Expose
    public ConstructorTable constructorTable;
    @SerializedName("RaceTable")
    @Expose
    public RaceTable raceTable;

    @Override
    public String toString() {
        return "xmlns" + xmlns + "series" + series + "url" + url + "limit" + limit + "offset" + offset + "total" + total + "driverTable" + driverTable + "circuitTable" + circuitTable + "constructorTable" + constructorTable + "raceTable" + raceTable;
    }
}
