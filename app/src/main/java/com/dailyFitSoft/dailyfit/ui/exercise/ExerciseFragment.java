package com.dailyFitSoft.dailyfit.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private DataBaseHelper dataBaseHelper;
    private Exercise tempExercise;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        exerciseViewModel =
                ViewModelProviders.of(this).get(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_excercise, container, false);
        dataBaseHelper = new DataBaseHelper(getContext());

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        ArrayList<String> exercisesToShow = new ArrayList<String>();
        try {
            exercises = (ArrayList<Exercise>) dataBaseHelper.getExerciseList();
        }catch (NullPointerException ex){
            System.out.println("Brak zdefiniowanych aktywności w systemie");
        }

        for (Exercise exercise: exercises) {
            exercisesToShow.add(exercise.getName() +
                    " \n Trudność: " + exercise.getDifficulty() + " | Spalone kalorie/godzinę ruchu: " +
                    exercise.getBurnedCalories());
        }

        ListView listView =  root.findViewById(R.id.ExerciseListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                exercisesToShow
        );

        listView.setAdapter(listViewAdapter);


        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                showPopupOfDeletingExercise(pos);
                return true;
            }
        });



        exerciseViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                changeFloatingActionButton();
            }
        });

        return root;
    }



    private void changeFloatingActionButton()
    {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupOfAddingNewExercise();

            }
        });
    }

    public void showPopupOfAddingNewExercise()
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_add_new_exercise, null);
        alertDialog.setView(alertDialogView);

        final EditText nameOfExercise = alertDialogView.findViewById(R.id.name_of_exercise);
        final EditText burntCalories = alertDialogView.findViewById(R.id.burnt_calories);
        final EditText difficulty = alertDialogView.findViewById(R.id.difficulty);


        alertDialog.setPositiveButton("Dodaj aktywność", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    dataBaseHelper.addExerciseData(nameOfExercise.getText().toString(), Integer.parseInt(difficulty.getText().toString()), Integer.parseInt(burntCalories.getText().toString()));
                }
                catch(NumberFormatException nfe){
                    Toast.makeText(getContext(),"Wszystkie pola muszą zostać wypełnione. Spróbuj jeszcze raz :)",Toast.LENGTH_LONG).show();
                }
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public void showPopupOfDeletingExercise(final int position){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popup_delete_exercise, null);
        alertDialog.setView(alertDialogView);

        alertDialog.setPositiveButton("Delete exercise", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteExercise(position);
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public void deleteExercise(int position){

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        try {
            exercises = (ArrayList<Exercise>) dataBaseHelper.getExerciseList();
        }catch (NullPointerException ex){
            System.out.println("Pusta baza");
        }

        Exercise exercise = exercises.get(position);
        dataBaseHelper.dropExercise(exercise.getID());


    }


}
