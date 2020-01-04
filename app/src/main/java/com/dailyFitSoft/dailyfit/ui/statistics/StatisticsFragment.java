package com.dailyFitSoft.dailyfit.ui.statistics;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Training;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;
    private DataBaseHelper dataBaseHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        statisticsViewModel =
                ViewModelProviders.of(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        final TextView textView = root.findViewById(R.id.text_statistics);
        statisticsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        createFirstPlot(root);
        return root;
    }
    public void createFirstPlot(View root)
    {

        ArrayList<Training> trainings = new ArrayList<>();
        ArrayList<DataPoint> dataPoints = new ArrayList<>();

        try {
            trainings = (ArrayList<Training>) dataBaseHelper.getTrainingsList();
        }catch (NullPointerException ex){
            System.out.println("Brak zapisanych treningów w bazie danych");
        }

        for (Training training : trainings) {
            long spaloneKalorie = 0;
            Date startDateTime = training.getStartDateTime();
            Date stopDateTime = training.getStopDateTime();

            long difference = stopDateTime.getTime() - startDateTime.getTime();
            difference /= 1000;


            long minutesOfBurningCalories = difference / 60;

            try
            {
                Cursor cursor =  dataBaseHelper.getExerciseData(training.getIDExercise());
                cursor.moveToFirst();

                int burnedCaloriesColumnId = cursor.getColumnIndex("BurnedCalories");
                long burnedCalories = cursor.getLong(burnedCaloriesColumnId);

                spaloneKalorie = burnedCalories/60 * minutesOfBurningCalories;
                dataPoints.add(new DataPoint(stopDateTime,spaloneKalorie));

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }



        DataPoint[] dataPointsArray = new DataPoint[dataPoints.size()];
        dataPointsArray = dataPoints.toArray(dataPointsArray);

        GraphView graph = root.findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
        graph.addSeries(series);


// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(dataPoints.get(0).getX());
        graph.getViewport().setMaxX(dataPoints.get(dataPoints.size()-1).getX());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

}
