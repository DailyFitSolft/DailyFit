package com.dailyFitSoft.dailyfit.ui.statistics;

import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
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
import com.dailyFitSoft.dailyfit.dataStore.Profile;
import com.dailyFitSoft.dailyfit.dataStore.Training;
import com.dailyFitSoft.dailyfit.dataStore.Weight;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        createWeightPlot(root);
        createBMIPlot(root);
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

            //ile sie biegalo
            long difference = stopDateTime.getTime() - startDateTime.getTime();
            difference /= 1000;
            //teraz difference jest w ssekundach

            long minutesOfBurningCalories = difference / 60;

            try
            {
                Cursor cursor =  dataBaseHelper.getExerciseData(training.getIDExercise());
                cursor.moveToFirst();

                int burnedCaloriesColumnId = cursor.getColumnIndex("BurnedCalories");
                long burnedCalories = cursor.getLong(burnedCaloriesColumnId);
                //511
                spaloneKalorie = burnedCalories/60 * minutesOfBurningCalories;
                //1768

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            //sprawdzam czy jest juz jakis wpis z taka data
            for (DataPoint d:dataPoints) {
                double dateInMiliSeconds = d.getX();
                long dateInSeconds = new Double(dateInMiliSeconds).longValue() * 1000;
                Date tempDate = new Date(dateInSeconds);

                //biore tylko poczatek z data
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                if(formatter.format(tempDate).equals(formatter.format(stopDateTime)))
                {

                }
            }


            dataPoints.add(new DataPoint(stopDateTime,spaloneKalorie));

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

    public void createWeightPlot(View root){
        List<Weight> weightsList = dataBaseHelper.getWeightList();
        GraphView graphView = root.findViewById(R.id.weightGraph);

        List<DataPoint> dataPointList = new ArrayList<>();
        for (Weight weight : weightsList){
            dataPointList.add(new DataPoint(weight.getDate(), weight.getWeight()));
        }
        DataPoint[] dataPointsArray = new DataPoint[dataPointList.size()];
        dataPointsArray = dataPointList.toArray(dataPointsArray);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
        graphView.addSeries(series);
        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

// set manual x bounds to have nice steps
        graphView.getViewport().setMinX(dataPointList.get(0).getX());
        graphView.getViewport().setMaxX(dataPointList.get(dataPointList.size()-1).getX());
        graphView.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graphView.getGridLabelRenderer().setHumanRounding(false);

    }

    public void createBMIPlot(View root){
        List<Weight> weightsList = dataBaseHelper.getWeightList();
        Profile profile = dataBaseHelper.getProfile();
        double height = profile.getHeight();
        GraphView graphView = root.findViewById(R.id.bmiGraph);

        List<DataPoint> dataPointList = new ArrayList<>();
        for (Weight weight : weightsList){
            dataPointList.add(new DataPoint(weight.getDate(), weight.getWeight() / Math.sqrt(height / 100)));
        }
        DataPoint[] dataPointsArray = new DataPoint[dataPointList.size()];
        dataPointsArray = dataPointList.toArray(dataPointsArray);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
        graphView.addSeries(series);
        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        // set manual x bounds to have nice steps
        graphView.getViewport().setMinX(dataPointList.get(0).getX());
        graphView.getViewport().setMaxX(dataPointList.get(dataPointList.size()-1).getX());
        graphView.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graphView.getGridLabelRenderer().setHumanRounding(false);
    }
}
