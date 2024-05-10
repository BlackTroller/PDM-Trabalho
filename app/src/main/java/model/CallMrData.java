package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallMrData {
    @SerializedName("MRData")
    @Expose
    public MRData mRData;

    public MRData getMRData() {
        return mRData;
    }

    @Override
    public String toString() {
        return "mRData" + mRData;
    }
}
