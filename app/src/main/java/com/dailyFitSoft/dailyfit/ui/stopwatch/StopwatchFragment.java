package com.dailyFitSoft.dailyfit.ui.stopwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;


public class StopwatchFragment extends Fragment {

    private StopwatchViewModel stopwatchViewModel;
    private Chronometer chronometer;
    private boolean isRunning;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stopwatchViewModel =
                ViewModelProviders.of(this).get(StopwatchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        chronometer = root.findViewById(R.id.chronometer);


        return root;
    }

    public void startChronometer(View view){
        if(!isRunning){
            chronometer.start();
            isRunning = true;
        }

    }


    public void pauseChronometer(View view){
        if(isRunning){
            chronometer.stop();
            isRunning = false;
        }

    }


    public void resetChronometer(View view){

    }
}