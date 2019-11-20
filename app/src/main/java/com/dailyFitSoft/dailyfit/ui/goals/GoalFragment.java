package com.dailyFitSoft.dailyfit.ui.goals;

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
import com.dailyFitSoft.dailyfit.dataStore.Goal;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ListView listView;
    private GoalAdapter goalAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goal, container, false);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        List<Goal> goals = new ArrayList<>();
        try{
            goals =  dataBaseHelper.getGoalList();
        } catch (NullPointerException ex){
            System.out.println("Database is empty!");
        }

        listView = root.findViewById(R.id.goalListView);

        goalAdapter = new GoalAdapter(getContext(), (ArrayList<Goal>) goals);
        listView.setAdapter(goalAdapter);

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
        return root;
    }
}

