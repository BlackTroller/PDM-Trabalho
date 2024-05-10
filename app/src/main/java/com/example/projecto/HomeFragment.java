package com.example.projecto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.CallMrData;
import model.Race;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_FINE_LOCATION = 100;
    private static final int REQUEST_WRITE_CALENDAR = 200;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Button btn1;
    private TextView tv1;
    private boolean wow;
    private boolean wow1;
    public String date;
    public String time;
    public String raceName;
    public String circuitName;

    public HomeFragment() {
    }

    @SuppressLint("VisibleForTests")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = new FusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(5000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    updateLocation(location);
                    if(getRetrofitData()){
                        break;
                    }

                }
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        tv1 = mContentView.findViewById(R.id.tv1);
        tv1.setVisibility(View.INVISIBLE);
        btn1 = mContentView.findViewById(R.id.btn1);

        btn1.setVisibility(View.INVISIBLE);
        Button btn2 = mContentView.findViewById(R.id.btn2);
        ImageButton teams = mContentView.findViewById(R.id.teams);
        ImageButton circuits = mContentView.findViewById(R.id.circuits);
        ImageButton schedule = mContentView.findViewById(R.id.schedule);
        ImageButton drivers = mContentView.findViewById(R.id.drivers);

        btn1.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new ExtraquestionFragment();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        teams.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new TeamListFragment();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        circuits.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new CircuitListFragment();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        schedule.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new schedulesFragment();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        drivers.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new DriverListFragment();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        btn2.setOnClickListener(v -> {
            try {
                if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                        Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                    requestCalendar();
                    addToCalendar(date);
                }
                Toast.makeText(getContext(), "Added to Calendar with success!", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Not added To Calendar!", Toast.LENGTH_SHORT).show();
            }

        });
        return mContentView;
    }

    private void updateLocation(Location location) {

        if(location != null){

            Log.e("update", "" + location);

        }
    }

    private void startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions();
            return;
        }
        if(locationCallback != null && locationRequest != null){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
    }

    private void requestCalendar(){
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_WRITE_CALENDAR);
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

    private boolean getRetrofitData() {
        getApi().getSchedulesList()
                .enqueue(new Callback<CallMrData>() {
                    @Override
                    public void onResponse(Call<CallMrData> call, Response<CallMrData> response) {
                        CallMrData datas = response.body();
                        assert datas != null;
                        List<Race> races = datas.getMRData().raceTable.races;

                        for(Race race: races){

                            Double circuitLat = Double.parseDouble(race.circuit.getLocation().lat);
                            Double circuitLong = Double.parseDouble(race.circuit.getLocation()._long);
                            date = String.valueOf(race.date);
                            time = String.valueOf(race.time);
                            raceName = String.valueOf(race.raceName);
                            circuitName = String.valueOf(race.circuit.circuitName);

                            try {
                                addToCalendar(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            boolean good = nearCircuit(circuitLat, circuitLong);

                            if(good) {
                                wow1 = true;
                                break;
                            } else {
                                btn1.setVisibility(View.INVISIBLE);
                                tv1.setVisibility(View.INVISIBLE);
                                wow1 = false;
                            }


                        }
                    }
                    @Override
                    public void onFailure(Call<CallMrData>call, Throwable t) {

                    }
                });
        return wow1;
    }

    private boolean nearCircuit(Double circuitLat, Double circuitLong) {
        if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions();
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(),
                        location -> {
                            if(location != null){
                                updateLocation(location);
                                Location temp = new Location(LocationManager.GPS_PROVIDER);
                                temp.setLatitude(circuitLat);
                                temp.setLongitude(circuitLong);

                                Location location1 = new Location(LocationManager.GPS_PROVIDER);

                                location1.setLatitude(location.getLatitude());
                                location1.setLongitude(location.getLongitude());

                                float distance = location1.distanceTo(temp);
                                if (distance <= 10000) {
                                    btn1.setVisibility(View.VISIBLE);
                                    tv1.setVisibility(View.VISIBLE);
                                    wow = true;
                                } else {
                                    wow = false;
                                }
                            }
                        })
                .addOnFailureListener(getActivity(), error -> {
                });
        return wow;
    }

    private void addToCalendar(String date) throws ParseException {
            String myDate = (date) + " 00:00:00";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date xDate = sdf.parse(myDate);
        assert xDate != null;
        long millis = xDate.getTime();

            ContentResolver cr = Objects.requireNonNull(getContext()).getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, millis);
            values.put(CalendarContract.Events.DTEND, millis);
            values.put(CalendarContract.Events.TITLE, raceName);
            values.put(CalendarContract.Events.DESCRIPTION, "New F1 Race in: " + "" + circuitName);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
            cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }

    @Override
    public void onResume() {
        startLocationUpdates();
        super.onResume();
    }

    @Override
    public void onPause() {
        stopLocationUpdates();
        super.onPause();
    }

}
