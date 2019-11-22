package com.dailyFitSoft.dailyfit.ui.goals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Goal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoalAdapter extends ArrayAdapter<Goal> {
    private Context context;
    private List<Goal> goalList = new ArrayList<>();
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
    public GoalAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Goal> list) {
        super(context, 0, list);
        this.context = context;
        this.goalList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.goal_list_item,parent,false);
        }

        Goal currentGoal = goalList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name_of_goal);
        name.setText(currentGoal.getName());

        TextView end = (TextView) listItem.findViewById(R.id.goal_end_date);
        end.setText(format.format(currentGoal.getEndDate()));

        TextView notsucced = (TextView) listItem.findViewById(R.id.notsucceded);

        ProgressBar progress = (ProgressBar) listItem.findViewById(R.id.goalProgressBar);

        CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.is_goal_achived);
        if(currentGoal.isAchived()){
            checkBox.setChecked(true);
        }else if(!currentGoal.isAchived() && currentGoal.getEndDate().before(new Date())){
            name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            end.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            checkBox.setVisibility(View.INVISIBLE);
            notsucced.setText("Nie udało się :(");
            notsucced.setTextColor(Color.parseColor("#b53737"));
            notsucced.setTypeface(null, Typeface.BOLD);
            notsucced.setTextSize(25);
            notsucced.setVisibility(View.VISIBLE);
            progress.setVisibility(View.INVISIBLE);
        }

        return listItem;
    }
}
