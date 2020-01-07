package com.dailyFitSoft.dailyfit;

import android.app.AlarmManager;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.R;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.dailyFitSoft.dailyfit.dataStore.Exercise;
import com.dailyFitSoft.dailyfit.dataStore.Profile;
import com.dailyFitSoft.dailyfit.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.dailyFitSoft.dailyfit.dataStore.DataBaseHelper;
import com.google.android.material.snackbar.Snackbar;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView profileNameTextView;
    private TextView bmiTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_excercise, R.id.nav_training_history,R.id.nav_statistics)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Profile profile = dataBaseHelper.getProfile();

        this.profileNameTextView = navigationView.getHeaderView(0).findViewById(R.id.profileName);
        this.profileNameTextView.setText("Witaj " + profile.getName() + "!");

        this.bmiTextView = navigationView.getHeaderView(0).findViewById(R.id.profileBmi);
        double bmi = profile.getWeight()/(profile.getHeight()*profile.getHeight()/10000);
        this.bmiTextView.setText(String.format("BMI: %.2f", bmi));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void showFloatingActionButton() {
        FloatingActionButton button = findViewById(R.id.fab);
        button.show();
    }

    public void hideFloatingActionButton() {
        FloatingActionButton button = findViewById(R.id.fab);
        button.hide();
    }

    public boolean isFloatingButtonShown() {
        FloatingActionButton button = findViewById(R.id.fab);
        return button.isShown();
    }

    public void setProfileName(String name) {
        this.profileNameTextView.setText(name);
    }

    public void setBmiProfile(String bmi) {
        this.bmiTextView.setText(bmi);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }
}
