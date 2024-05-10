package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverTable {
    @SerializedName("driverId")
    @Expose
    public String driverId;
    @SerializedName("Drivers")
    @Expose
    public List<Driver> drivers = null;

    @Override
    public String toString() {
        return "driverId" + driverId + "drivers" + drivers;
    }
}
