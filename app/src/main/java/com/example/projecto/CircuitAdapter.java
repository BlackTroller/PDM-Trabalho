package com.example.projecto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Circuit;

public class CircuitAdapter extends RecyclerView.Adapter<CircuitAdapter.CircuitViewHolder> {
    private final FragmentActivity c;
    private final ArrayList<Circuit> circuitList;
    public CircuitAdapter(FragmentActivity c, ArrayList<Circuit> circuitList) {
        this.c = c;
        this.circuitList = circuitList;
    }
    @Override
    public int getItemCount() {
        return circuitList.size();
    }

    public static class CircuitViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_circuit_name;
        public TextView txt_circuit_locality;
        public TextView txt_circuit_country;
        public Button btn_map;
        public CircuitViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_circuit_name = itemView.findViewById(R.id.txt_circuit_name);
            txt_circuit_locality = itemView.findViewById(R.id.txt_circuit_locality);
            txt_circuit_country = itemView.findViewById(R.id.txt_circuit_country);
            btn_map = itemView.findViewById(R.id.btn_map);
        }
    }

    @NonNull
    @Override
    public CircuitAdapter.CircuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Get Layout inflater from Context
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //Inflate Layout
        View circuitView = layoutInflater.inflate(R.layout.item_circuit, parent, false);
        return new CircuitAdapter.CircuitViewHolder(circuitView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CircuitAdapter.CircuitViewHolder viewHolder, int position) {
        //Get circuit name
        Circuit circuit = circuitList.get(position);
        TextView textView = viewHolder.txt_circuit_name;
        textView.setText(circuit.getCircuitName());

        //Set locality
        TextView textView1 = viewHolder.txt_circuit_locality;
        textView1.setText(circuit.getLocation().locality);

        //Set country
        TextView textView2 = viewHolder.txt_circuit_country;
        textView2.setText("" + circuit.getLocation().country);

        preferences wow = new preferences(c);

        final Button btn1 = viewHolder.btn_map;
        btn1.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            wow.setCords("location", circuit.getLocation().lat, circuit.getLocation()._long, circuit.getCircuitName());
            Fragment fragment;
            fragment = new MapsFragment();
            FragmentTransaction fragmentTransaction = c.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

    }

}
