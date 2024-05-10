package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("driverId")
    @Expose
    public String driverId;
    @SerializedName("permanentNumber")
    @Expose
    public String permanentNumber;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("givenName")
    @Expose
    public String givenName;
    @SerializedName("familyName")
    @Expose
    public String familyName;
    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
    @SerializedName("nationality")
    @Expose
    public String nationality;

    @Override
    public String toString() {
        return "driverId" + driverId + "permanentNumber" + permanentNumber + "code" + code + "url" + url + "givenName" + givenName + "familyName" + familyName + "dateOfBirth" + dateOfBirth + "nationality" + nationality;
    }
}
