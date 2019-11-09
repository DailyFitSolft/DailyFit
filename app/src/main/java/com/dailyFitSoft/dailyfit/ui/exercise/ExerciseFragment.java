package com.dailyFitSoft.dailyfit.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Exercise;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        exerciseViewModel =
                ViewModelProviders.of(this).get(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_excercise, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        ArrayList<String> exercisesToShow = new ArrayList<String>();
        try {
            exercises = (ArrayList<Exercise>) dataBaseHelper.getExerciseList();
        }catch (NullPointerException ex){
            System.out.println("Pusta baza");
        }

        for (Exercise exercise: exercises) {
            exercisesToShow.add(exercise.getName() + " | Trudność: " + exercise.getDifficulty() + " | Spalone kalorie: " +
                    exercise.getBurnedCalories());
        }

        ListView listView =  root.findViewById(R.id.ExerciseListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                exercisesToShow
        );

        listView.setAdapter(listViewAdapter);
        return root;
    }
}
