package com.dailyFitSoft.dailyfit.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.DateFormatter;
import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Exercise;
import com.dailyFitSoft.dailyfit.dataStore.PlannedExercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CalendarView homeCalendar;
    private String textRepresentationOfDate="";
    private DataBaseHelper dataBaseHelper;
    private List<String> exercisesOnDay = new LinkedList<>();
    private ListView exerciseListView;
    private ArrayAdapter<String> exerciseListDataAdapter;
    private Exercise tempExercise;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        dataBaseHelper = new DataBaseHelper(getActivity());
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                changeFloatingActionButton();
            }
        });

        settingCalendar(root);
        settingExerciseList(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void settingCalendar(View root)
    {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        textRepresentationOfDate=formatter.format(date);

        homeCalendar = root.findViewById(R.id.home_fragment_calendar);
        homeCalendar.setMinDate(new Date().getTime());
        homeCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                textRepresentationOfDate = dayOfMonth + "-" + (month+1) + "-" + year;
                Toast.makeText(getContext(), textRepresentationOfDate,Toast.LENGTH_SHORT).show();
                refreshListOfExercises();
            }
        });
    }
    private void settingExerciseList(View root)
    {
        exerciseListView = root.findViewById(R.id.list_of_exercises);
        exerciseListDataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,exercisesOnDay);
        exerciseListView.setAdapter(exerciseListDataAdapter);
    }

    private void changeFloatingActionButton()
    {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupOfAddingExerciseToCalendar();
            }
        });
    }
    public void showPopupOfAddingExerciseToCalendar()
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_add_event_to_calendar, null);
        alertDialog.setView(alertDialogView);

        TextView dateOfExerciseText = alertDialogView.findViewById(R.id.date_of_exercise);
        dateOfExerciseText.setText("Exercise on: " + textRepresentationOfDate);

        final Spinner exerciseSelector = alertDialogView.findViewById(R.id.exercise_selector);
        List<Exercise> listOfExercisesFromDatabase = new LinkedList<>();
        if(dataBaseHelper.getExerciseList().isEmpty())
        {
            Toast.makeText(getContext(), "NO VAIABLE EXERCIES IN DATABASE!",Toast.LENGTH_SHORT).show();
            return;
        }
        for (Exercise e: dataBaseHelper.getExerciseList())
        {
            listOfExercisesFromDatabase.add(e);
        }
        ArrayAdapter<Exercise> dataForSpinnerAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listOfExercisesFromDatabase);
        exerciseSelector.setAdapter(dataForSpinnerAdapter);

        exerciseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tempExercise = (Exercise) parentView.getItemAtPosition(position);
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        final EditText numberOfRepeats = alertDialogView.findViewById(R.id.number_of_repeats);
        final EditText timeOfExercise = alertDialogView.findViewById(R.id.time_of_exercise);
        final TimePicker timePicker = alertDialogView.findViewById(R.id.time_picker);
        alertDialog.setPositiveButton("Add exercise", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(numberOfRepeats.getText().toString().isEmpty() || timeOfExercise.getText().toString().isEmpty())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("One of input value is null! Please enter correct value next time :)");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else
                {
                    dataBaseHelper.addPlannedExerciseData(tempExercise.getID(),Integer.parseInt(timeOfExercise.getText().toString()),Integer.parseInt(numberOfRepeats.getText().toString()),textRepresentationOfDate,timePicker.getHour() +":"+ timePicker.getMinute());

                    dialog.cancel();
                    refreshListOfExercises();
                }

            }
        });
        alertDialog.show();

    }
    private void refreshListOfExercises()
    {
        exercisesOnDay.clear();
        exerciseListDataAdapter.notifyDataSetChanged();
        for (PlannedExercise pe:dataBaseHelper.getPlannedExercisesList()) {
            if(pe.getPlannedDate().equals(DateFormatter.dateFromString(textRepresentationOfDate)))
            {
                for (Exercise e:dataBaseHelper.getExerciseList()) {
                    if(e.getID()==pe.getExerciseID())
                    {
                        exercisesOnDay.add(e.toString() + pe.toString());
                        exerciseListDataAdapter.notifyDataSetChanged();
                    }
                }

            }

        }
    }

}