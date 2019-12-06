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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ListView listView;
    private GoalAdapter goalAdapter;
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

        List<Goal> goals = new ArrayList<>();
        try{
            goals =  dataBaseHelper.getGoalList();
        } catch (NullPointerException ex){
            System.out.println("Database is empty!");
        }

        listView = root.findViewById(R.id.goalListView);

        goalAdapter = new GoalAdapter(getContext(), (ArrayList<Goal>) goals);
        listView.setAdapter(goalAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                GoalAdapter goalAdapter = (GoalAdapter) listView.getAdapter();
//                dataBaseHelper.dropGoal((int)view.getTag());
                showPopupOfDeletingGoal((int) view.getTag());
                return false;
            }
        });

//        ArrayList<String> goalsToShow = new ArrayList<>();
//
//        try{
//            goals = (ArrayList<Goal>) dataBaseHelper.getGoalList();
//        } catch (NullPointerException ex){
//            System.out.println("Database is empty!");
//        }
//
//        for (Goal goal:goals){
//            if(goal.isAchived()){
//                goalsToShow.add(goal.getName() + " Zaliczone!");
//            }else if(!goal.isAchived() && goal.getEndDate().before(new Date())){
//                goalsToShow.add(goal.getName() + " Nie udało się :(");
//            } else if(!goal.isAchived() && goal.getEndDate().after(new Date())){
//                goalsToShow.add(goal.getName() + " W trakcie. Koniec celu: " + simpleDateFormat.format(goal.getEndDate()));
//            }
//        }
//
//
//        ListView listGoalView = root.findViewById(R.id.goalListView);
//
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                goalsToShow
//        );
//
//        listGoalView.setAdapter(listViewAdapter);
        goalViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                changeFloatingActionButton();
            }
        });

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

    private void showPopupOfDeletingGoal(final int goalID){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alerDialogView = inflater.inflate(R.layout.popup_delete_goal, null);
        alertDialog.setView(alerDialogView);
        alertDialog.setPositiveButton("Delete exercise", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dataBaseHelper.dropGoal(goalID);
                goalAdapter.updateList(dataBaseHelper.getGoalList());
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alerDialogView = inflater.inflate(R.layout.popup_delete_goal, null);
        alertDialog.setView(alerDialogView);
        TextView text = alerDialogView.findViewById(R.id.delete_goal_text);
        text.setText("Wszystkie pola muszą zostać wypełnione. Spróbuj jeszcze raz :)");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void showPopupOfAddingGoal(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder((getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_add_goal, null);
        alertDialog.setView(alertDialogView);

        final Spinner goalSelector = alertDialogView.findViewById(R.id.goal_selector);
        final ArrayAdapter<GoalType> dataFromSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, GoalType.getGoalTypeList());
        goalSelector.setAdapter(dataFromSpinnerAdapter);

        dateText = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

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

        final CalendarView calendarView = alertDialogView.findViewById(R.id.goal_add_calendar);
        calendarView.setMinDate(new Date().getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                dateText = day + "-" + (month + 1) + "-" + year;
            }
        });


        alertDialog.setPositiveButton("Dodaj cel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!dateText.equals("") && !valueToAchive.getText().toString().equals("")){
                    dataBaseHelper.addGoalData(tempGoalType, dateText, false, Integer.parseInt(valueToAchive.getText().toString()), 0);
                    goalAdapter.updateList(dataBaseHelper.getGoalList());
                    dialogInterface.cancel();
                }
                else
                    showErrorDialog();
            }
        });
        alertDialog.show();
    }
}

