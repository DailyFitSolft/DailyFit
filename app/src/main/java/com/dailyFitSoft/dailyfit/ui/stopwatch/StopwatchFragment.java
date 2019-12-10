package com.dailyFitSoft.dailyfit.ui.stopwatch;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Exercise;
import com.dailyFitSoft.dailyfit.dataStore.Goal;
import com.dailyFitSoft.dailyfit.dataStore.PlannedExercise;
import com.dailyFitSoft.dailyfit.dataStore.Training;
import com.dailyFitSoft.dailyfit.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class StopwatchFragment extends Fragment {

    private StopwatchViewModel stopwatchViewModel;
    private Chronometer chronometer;
    private long stopOffset;
    private boolean isRunning;
    private DataBaseHelper dataBaseHelper;
    private Exercise tempExercise;

    //variables for training
    String nameOfExercise;
    String startTime;
    String endTime;
    String date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        stopwatchViewModel =
                ViewModelProviders.of(this).get(StopwatchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        chronometer = root.findViewById(R.id.chronometer);
        dataBaseHelper = new DataBaseHelper(getContext());
        List<Exercise> listOfExercisesFromDatabase = dataBaseHelper.getExerciseList();
        final List<Goal> listofgoals = dataBaseHelper.getGoalList();
        Button startButton = (Button) root.findViewById(R.id.startbutton);
        Button pauseButton = root.findViewById(R.id.pausebutton);
        final Button resetButton = root.findViewById(R.id.resetbutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning){
                    chronometer.setBase(SystemClock.elapsedRealtime() - stopOffset);
                    chronometer.start();
                    isRunning = true;
                    resetButton.setClickable(false);
                    startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                }
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning){
                    chronometer.stop();
                    stopOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isRunning = false;
                    resetButton.setClickable(true);
                    endTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    for(Goal goal:listofgoals){
                        switch (goal.getGoalType()){
                            case CZAS_W_RUCHU:
                                int elapsedSeconds = (int)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                                int newArchivedValue = goal.getAchivedValue() + (elapsedSeconds/60);
                                goal.setAchivedValue(newArchivedValue);
                                dataBaseHelper.updateGoalArchivedValue(goal,newArchivedValue);
                                if(goal.getAchivedValue()>=goal.getValueToAchive())
                                {
                                    goal.setAchived(true);
                                    dataBaseHelper.setGoalArchived(goal,true);
                                }

                                break;
                            case SPALONE_KALORIE:
                                //podzielic new archived value przez 60 zeby miec w minutach
                                elapsedSeconds = (int)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                                newArchivedValue = (goal.getAchivedValue() + (elapsedSeconds/3600) * tempExercise.getBurnedCalories());
                                goal.setAchivedValue(newArchivedValue);
                                dataBaseHelper.updateGoalArchivedValue(goal,newArchivedValue);
                                if(goal.getAchivedValue()>=goal.getValueToAchive())
                                {
                                    goal.setAchived(true);
                                    dataBaseHelper.setGoalArchived(goal,true);
                                }
                                break;
                        }
                    }
                    dataBaseHelper.addTrainingData(nameOfExercise,startTime,endTime,date);

                    for (PlannedExercise p:dataBaseHelper.getPlannedExercisesList()) {
                        if(p.getPlannedDate().equals(date) && p.getExerciseID()==tempExercise.getID())
                        {
                            int elapsedSeconds = (int)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                            int elapsedMinuts = elapsedSeconds;
                            if(elapsedMinuts > p.getTrainTime())
                            {
                                dataBaseHelper.dropPlannedExercise(p.getID());
                                Log.d("test","Usunieto zaplanowane cwiczenie: " + p.toString());
                            }
                        }
                    }
                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopOffset = 0;
                isRunning = false;
            }
        });

        final Spinner exerciseSelector = root.findViewById(R.id.spinnerinstopwatch);


        ArrayAdapter<Exercise> dataForSpinnerAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listOfExercisesFromDatabase);
        exerciseSelector.setAdapter(dataForSpinnerAdapter);

        exerciseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tempExercise = (Exercise) parentView.getItemAtPosition(position);
                nameOfExercise = tempExercise.getName();
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopOffset = 0;
                isRunning = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });





        return root;
    }
    public void checkIfPlannedExerciseDone()
    {

    }




}