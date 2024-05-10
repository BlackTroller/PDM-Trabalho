package com.example.projecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private AnyChartView anyChartView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int total;
    private int rightanswers;
    private int wronganswers;

    private final String[] Answers = {"Right anwers", "Wrong answers"};

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (user != null) {
            String email = user.getEmail();
            db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                total = document.getLong("totalAnswers").intValue();
                                rightanswers = document.getLong("rightAnswers").intValue();
                                wronganswers = document.getLong("wrongAnswers").intValue();
                                setupPieChart(total, rightanswers, wronganswers);
                            }
                        }
                    });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mContentView = inflater.inflate(R.layout.fragment_history, container, false);
        anyChartView = mContentView.findViewById(R.id.any_chart_view);
        return mContentView;
    }

    public void setupPieChart(int total, int right, int wrong) {

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        int[] values = {right, wrong};

        for (int i = 0; i< Answers.length; i++) {
            dataEntries.add(new ValueDataEntry(Answers[i], values[i]));
        }

        pie.data(dataEntries);
        pie.title("Question total: " + total);
        pie.title().fontColor("#ffffff");
        pie.title().fontWeight("bold");
        pie.title().fontSize(20);

        pie.background("#000000");

        pie.legend().title()
                .fontWeight("bold")
                .fontColor("#ffffff");
        pie.labels()
                .fontColor("#ffffff")
                .fontWeight("bold")
                .fontSize(18);

        pie.legend()
                .itemsLayout(LegendLayout.HORIZONTAL)
                .fontColor("#ffffff")
                .align(Align.CENTER);

        pie.palette().items("#08ff00", "#fe0500");

        anyChartView.setChart(pie);
    }

}