package com.dailyFitSoft.dailyfit.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dailyFitSoft.dailyfit.MainActivity;
import com.dailyFitSoft.dailyfit.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static CalendarView homeCalendar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        settingCalendar(root);

        return root;

    }
    private void settingCalendar(View root)
    {
        homeCalendar = root.findViewById(R.id.homeCalendar);
        homeCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String textDate= dayOfMonth + "-" + month + "-" + year;
                Toast.makeText(getContext(), textDate,Toast.LENGTH_SHORT).show();
                MainActivity.selectedDate = textDate;
            }
        });
    }

    public static CalendarView getHomeCalendar() {
        return homeCalendar;
    }
}