package com.example.projecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import model.CallMrData;
import model.Race;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class schedulesFragment extends Fragment {

    private TextView textViewResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://ergast.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private F1API getApi(){
        return getRetrofit().create(F1API.class);
    }

    private void getRetrofitData() {
        getApi().getSchedulesList()
                .enqueue(new Callback<CallMrData>() {
                    @Override
                    public void onResponse(Call<CallMrData> call, Response<CallMrData> response) {
                        CallMrData datas = response.body();
                        List<Race> races = datas.getMRData().raceTable.races;
                        for(Race race: races){
                            String content= "";
                            content += "Round: " + race.round + "\n";
                            content += "Race Name: " + race.raceName + "\n";
                            content += "Date: " + race.date + "\n";
                            content += "Time: " + race.time + "\n";
                            content += "Circuit: " + race.circuit.circuitName + "\n";
                            content += "Locality: " + race.circuit.location.locality + "\n";
                            content += "Country: " + race.circuit.location.country + "\n\n";
                            textViewResult.append(content);
                        }
                    }
                    @Override
                    public void onFailure(Call<CallMrData>call, Throwable t) {
                        textViewResult.setText(t.getMessage());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mContextView = inflater.inflate(R.layout.fragment_schedules, container, false);
        getRetrofitData();
        textViewResult = mContextView.findViewById(R.id.Text_view_schedules_result);
        return mContextView;
    }
}