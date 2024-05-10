package com.example.projecto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.CallMrData;
import model.Circuit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CircuitListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CircuitAdapter circuitAdapter;
    private ArrayList<Circuit> circuitList;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        circuitList = new ArrayList<>();
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
        getApi().getCircuitsList()
                .enqueue(new Callback<CallMrData>() {
                    @Override
                    public void onResponse(Call<CallMrData> call, Response<CallMrData> response) {
                        CallMrData datas = response.body();
                        assert datas != null;
                        List<Circuit> circuits = datas.getMRData().circuitTable.circuits;
                        for(Circuit circuit: circuits){
                            circuitList.add(circuit);
                            //Set the RecyclerView adapter
                            circuitAdapter = new CircuitAdapter(getActivity(), circuitList);
                            recyclerView.setAdapter(circuitAdapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<CallMrData>call, Throwable t) {
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mContextView = inflater.inflate(R.layout.fragment_circuit_list, container, false);
        getRetrofitData();
        recyclerView = mContextView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return mContextView;
    }
}