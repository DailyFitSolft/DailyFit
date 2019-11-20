package com.dailyFitSoft.dailyfit.ui.goals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Goal;
import com.dailyFitSoft.dailyfit.dataStore.GoalType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private DataBaseHelper dataBaseHelper;
    private GoalType tempGoalType;
    private String dateText = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goal, container, false);

        dataBaseHelper = new DataBaseHelper(getContext());

        ArrayList<Goal> goals = new ArrayList<>();

        ArrayList<String> goalsToShow = new ArrayList<>();

        try{
            goals = (ArrayList<Goal>) dataBaseHelper.getGoalList();
        } catch (NullPointerException ex){
            System.out.println("Database is empty!");
        }

        for (Goal goal:goals){
            if(goal.isAchived()){
                goalsToShow.add(goal.getGoalType().toString() + " Zaliczone!");
            }else if(!goal.isAchived() && goal.getEndDate().before(new Date())){
                goalsToShow.add(goal.getGoalType().toString() + " Nie udało się :(");
            } else if(!goal.isAchived() && goal.getEndDate().after(new Date())){
                goalsToShow.add(goal.getGoalType().toString() + " W trakcie. Koniec celu: " + simpleDateFormat.format(goal.getEndDate()));
            }
        }


        ListView listGoalView = root.findViewById(R.id.goalListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                goalsToShow
        );

        goalViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                changeFloatingActionButton();
            }
        });

        listGoalView.setAdapter(listViewAdapter);
        return root;
    }

    private void changeFloatingActionButton(){
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupOfAddingGoal();
            }
        });
    }

    private void showPopupOfAddingGoal(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder((getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_add_goal, null);
        alertDialog.setView(alertDialogView);

        final Spinner goalSelector = alertDialogView.findViewById(R.id.goal_selector);
        ArrayAdapter<GoalType> dataFromSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, GoalType.getGoalTypeList());
        goalSelector.setAdapter(dataFromSpinnerAdapter);

        goalSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempGoalType = (GoalType) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText valueToAchive = alertDialogView.findViewById(R.id.goal_value_to_achive);

        CalendarView calendarView = alertDialogView.findViewById(R.id.goal_add_calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                dateText = day + "-" + month + "-" + year;
            }
        });
        alertDialog.setPositiveButton("Add goal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!dateText.equals("") && !valueToAchive.getText().toString().equals("")){
                    dataBaseHelper.addGoalData(tempGoalType, dateText, false, Integer.parseInt(valueToAchive.getText().toString()), 0);
                    dialogInterface.cancel();
                }
            }
        });
        alertDialog.show();
    }
}

