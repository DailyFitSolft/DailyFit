package com.dailyFitSoft.dailyfit.ui.stopwatch;

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

import java.util.LinkedList;
import java.util.List;


public class StopwatchFragment extends Fragment {

    private StopwatchViewModel stopwatchViewModel;
    private Chronometer chronometer;
    private long stopOffset;
    private boolean isRunning;
    private DataBaseHelper dataBaseHelper;
    private Exercise tempExercise;

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