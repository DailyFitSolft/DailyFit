package com.dailyFitSoft.dailyfit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DailyFit_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String EXERCISE_TABLE_NAME = "exercises";
    private static final String EXERCISE_COL1 = "ID";
    private static final String EXERCISE_COL2 = "Name";
    private static final String EXERCISE_COL3 = "Difficulty";
    private static final String EXERCISE_COL4 = "BurnedCalories";

    private static final String PLANNED_EXERCISE_TABLE_NAME = "plannedExercises";
    private static final String PLANNED_EXERCISE_COL1 = "ID";
    private static final String PLANNED_EXERCISE_COL2 = "ExerciseID";
    private static final String PLANNED_EXERCISE_COL3 = "TrainTime";
    private static final String PLANNED_EXERCISE_COL4 = "RepeatCount";
    private static final String PLANNED_EXERCISE_COL5 = "PlannedDateAndTime";

    private static final String GOAL_TABLE_NAME = "goals";
    private static final String GOAL_COL1 = "ID";
    private static final String GOAL_COL2 = "Name";
    private static final String GOAL_COL3 = "EndDate";
    private static final String GOAL_COL4 = "Achived";

    private static final String WEIGHT_TABLE_NAME = "weight";
    private static final String WEIGHT_COL1 = "ID";
    private static final String WEIGHT_COL2 = "Date";
    private static final String WEIGHT_COL3 = "Weight";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableExercise = "CREATE TABLE " + EXERCISE_TABLE_NAME + " ( " + EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXERCISE_COL2 + " TEXT, " + EXERCISE_COL3 + " INTEGER, " + EXERCISE_COL4 + " INTEGER);";
        String createTablePlannedExercise = "CREATE TABLE " + PLANNED_EXERCISE_TABLE_NAME + " ( " + PLANNED_EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLANNED_EXERCISE_COL2 + " INTEGER, " + PLANNED_EXERCISE_COL3 + " INTEGER, " + PLANNED_EXERCISE_COL4 + " INTEGER, " + PLANNED_EXERCISE_COL5 + " TEXT);";
        String createTableGoal = "CREATE TABLE " + GOAL_TABLE_NAME + " ( " + GOAL_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL_COL2 + " TEXT, " + GOAL_COL3 + " TEXT, " + GOAL_COL4 + " INTEGER);";
        String createTableWeight = "CREATE TABLE " + WEIGHT_TABLE_NAME + " ( " + WEIGHT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WEIGHT_COL2 + " TEXT, " + WEIGHT_COL3 + " INTEGER);";

        sqLiteDatabase.execSQL(createTableExercise);
        sqLiteDatabase.execSQL(createTablePlannedExercise);
        sqLiteDatabase.execSQL(createTableGoal);
        sqLiteDatabase.execSQL(createTableWeight);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLANNED_EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WEIGHT_TABLE_NAME);
    }
}
