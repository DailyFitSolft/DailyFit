package com.dailyFitSoft.dailyfit.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.R;

public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);

        View root = inflater.inflate(R.layout.fragment_goal, container, false);
        final TextView textView = root.findViewById(R.id.text_goal);
        goalViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

