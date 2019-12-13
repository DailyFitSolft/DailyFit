package com.dailyFitSoft.dailyfit.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.dailyFitSoft.dailyfit.dataStore.Profile;
import com.dailyFitSoft.dailyfit.dataStore.Weight;
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

        boolean profileShown = false;
        if(dataBaseHelper.getProfileTableSize() == 0){
            profileShown = true;
            showPopupOfAddingProfile();
        }

        if(!profileShown && !Weight.alreadyAsked && (dataBaseHelper.getLastlyAddedWeight() == null || !DateUtils.isToday(dataBaseHelper.getLastlyAddedWeight().getDate().getTime()))){
            showWeightPopup();
            Weight.alreadyAsked = true;
        }
        refreshListOfExercises();

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
        dateOfExerciseText.setText("Data ćwiczenia: " + textRepresentationOfDate);

        final Spinner exerciseSelector = alertDialogView.findViewById(R.id.exercise_selector);
        List<Exercise> listOfExercisesFromDatabase = new LinkedList<>();
        if(dataBaseHelper.getExerciseList().isEmpty())
        {
            Toast.makeText(getContext(), "Brak zdefiniowanych aktywności w bazie danych! Zdefiniuj aktywność i spróbuj ponownie",Toast.LENGTH_SHORT).show();
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
        alertDialog.setPositiveButton("Zaakceptuj plan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(numberOfRepeats.getText().toString().isEmpty() || timeOfExercise.getText().toString().isEmpty())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                   // alertDialog.setTitle("Błąd");
                    alertDialog.setMessage("Wszystkie pola muszą zostać wypełnione. Spróbuj jeszcze raz :)");
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

            Date plannedDate = pe.getPlannedDate();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDatePlannedDate = df.format(plannedDate);
            if(formattedDatePlannedDate.equals(textRepresentationOfDate))
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

    private void showWeightPopup(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_add_weight, null);
        alertDialog.setView(alertDialogView);

        final EditText weightInput = alertDialogView.findViewById(R.id.weight_to_add);
        alertDialog.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if(weightInput.getText().toString().equals(""))
                        showErrorWeightAddingDialog();
                    float weight = Float.valueOf(weightInput.getText().toString());
                    if(weight > 400){
                        showErrorWeightAddingDialog();
                        return;
                    }
                    dataBaseHelper.addWeightData(DateFormatter.stringFromDate(new Date()), weight);
                    dataBaseHelper.modifyProfileWeight(weight);
                    Toast.makeText(getContext(), "Pomyślnie zaktualizowano wagę", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.show();
    }

    private void showPopupOfAddingProfile(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_init_profile, null);
        alertDialog.setView(alertDialogView);
        final EditText nameInput = alertDialogView.findViewById(R.id.name_to_add);
        final EditText heightInput = alertDialogView.findViewById(R.id.height_to_add);
        final EditText weightInput = alertDialogView.findViewById(R.id.weight_to_add);
        final EditText ageInput = alertDialogView.findViewById(R.id.age_to_add);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameInput.getText().toString();
                String height = heightInput.getText().toString();
                String weight = weightInput.getText().toString();
                String age = ageInput.getText().toString();
                if (!(name.equals("") || height.equals("") || weight.equals("") || age.equals(""))){
                    boolean x = dataBaseHelper.addProfileData(name, Double.valueOf(height), Double.valueOf(weight), Integer.valueOf(age));
                    Log.d("db", String.valueOf(x));
                    dataBaseHelper.addWeightData(DateFormatter.stringFromDate(new Date()), Float.valueOf(weightInput.getText().toString()));
                    dialogInterface.dismiss();
                }
                else{
                    Toast.makeText(getContext(), "Pola nie zostały wypełnione poprawnie. Spróbuj jeszcze raz", Toast.LENGTH_LONG).show();
                    showPopupOfAddingProfile();
                }
            }
        });
        alertDialog.show();
    }

    private void showErrorWeightAddingDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alerDialogView = inflater.inflate(R.layout.popup_delete_goal, null);
        alertDialog.setView(alerDialogView);
        TextView text = alerDialogView.findViewById(R.id.delete_goal_text);
        text.setText("Nieodpowiednio wypełnione pole");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}