package com.dailyFitSoft.dailyfit.ui.training_history;

import android.content.Context;
import android.database.Cursor;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.MainActivity;
import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrainingHistoryFragment extends Fragment {

    private TrainingHistoryViewModel trainingHistoryViewModel;
    private DataBaseHelper dataBaseHelper;
    private ArrayAdapter<String> listViewAdapter;
    private boolean shouldShowFloatingButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity)getActivity();

            if (activity.isFloatingButtonShown()) {
                activity.hideFloatingActionButton();
                shouldShowFloatingButton = true;
            } else {
                shouldShowFloatingButton = false;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity)getActivity();

            if (shouldShowFloatingButton) {
                activity.showFloatingActionButton();
                shouldShowFloatingButton = false;
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trainingHistoryViewModel =
                ViewModelProviders.of(this).get(TrainingHistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_training_history, container, false);
        this.dataBaseHelper = new DataBaseHelper(this.getContext());

        ArrayList<Training> trainings = new ArrayList<Training>();
        ArrayList<String> trainingsToShow = new ArrayList<String>();

        try {
            trainings = (ArrayList<Training>) dataBaseHelper.getTrainingsList();
        }catch (NullPointerException ex){
            System.out.println("Brak zapisanych treningów w bazie danych");
        }

        for (Training training : trainings) {
            String durationString;
            long spaloneKalorie = 0;
            Date startDateTime = training.getStartDateTime();
            Date stopDateTime = training.getStopDateTime();

            long difference = stopDateTime.getTime() - startDateTime.getTime();
            difference /= 1000;

            long hours = difference / 3600;
            long minutes = (difference / 60) % 60;
            long seconds = difference % 60;

            durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            long minutesOfBurningCalories = difference / 60;

            try
            {
                Cursor cursor =  dataBaseHelper.getExerciseData(training.getIDExercise());
                cursor.moveToFirst();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                int burnedCaloriesColumnId = cursor.getColumnIndex("BurnedCalories");
                long burnedCalories = cursor.getLong(burnedCaloriesColumnId);
                int exerciseNameColumnId = cursor.getColumnIndex("Name");
                String exerciseName = cursor.getString(exerciseNameColumnId);

                spaloneKalorie = burnedCalories/60 * minutesOfBurningCalories;

                String startDate = simpleDateFormat.format(startDateTime);

                trainingsToShow.add(startDate + "\n" + exerciseName + "\n" + "Czas trwania: " + durationString +
                        " | Spalone kcal: " + spaloneKalorie);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

        ListView listView =  root.findViewById(R.id.trainingHistoryListView);

        listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                trainingsToShow
        );

        listView.setAdapter(listViewAdapter);

        final TextView textView = root.findViewById(R.id.text_training_history);
        trainingHistoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}