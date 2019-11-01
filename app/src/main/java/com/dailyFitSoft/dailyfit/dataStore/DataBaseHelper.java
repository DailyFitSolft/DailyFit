package com.dailyFitSoft.dailyfit.dataStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    //=========EXERCISE===========================================================

    public boolean addExerciseData(String exerciseName, int difficulty, int burnedCalories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_COL2, exerciseName);
        contentValues.put(EXERCISE_COL3, difficulty);
        contentValues.put(EXERCISE_COL4, burnedCalories);
        Log.d(EXERCISE_TABLE_NAME, "adding data: " + exerciseName);
        long results = db.insert(EXERCISE_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getExerciseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + EXERCISE_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void dropExercise(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_COL1 + " = " + ID;
        db.execSQL(query);
    }

    public void clearExerciseTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + EXERCISE_TABLE_NAME + " WHERE 1=1";
        db.execSQL(query);
    }

    //========PLANNED=EXERCISE======================================================

    public boolean addPlannedExerciseData(int exerciseID, int trainTime, int repeatCount, String dateAndTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANNED_EXERCISE_COL2, exerciseID);
        contentValues.put(PLANNED_EXERCISE_COL3, trainTime);
        contentValues.put(PLANNED_EXERCISE_COL4, repeatCount);
        contentValues.put(PLANNED_EXERCISE_COL5, dateAndTime);
        Log.d(PLANNED_EXERCISE_TABLE_NAME, "adding plannedExercise: " + exerciseID);
        long results = db.insert(PLANNED_EXERCISE_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getPlannedExerciseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PLANNED_EXERCISE_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void dropPlannedExercise(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PLANNED_EXERCISE_TABLE_NAME + " WHERE " + PLANNED_EXERCISE_COL1 + " = " + ID;
        db.execSQL(query);
    }

    public void clearPlannedExerciseTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PLANNED_EXERCISE_TABLE_NAME + " WHERE 1=1";
        db.execSQL(query);
    }

    //==========GOALS=================================================================

    public boolean addGoalData(String goalName, String endDate, int isAchived) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COL2, goalName);
        contentValues.put(GOAL_COL3, endDate);
        contentValues.put(GOAL_COL4, isAchived);
        Log.d(GOAL_TABLE_NAME, "adding goal: " + goalName);
        long results = db.insert(GOAL_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getGoalData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + GOAL_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void dropGoal(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + GOAL_TABLE_NAME + " WHERE " + GOAL_COL1 + " = " + ID;
        db.execSQL(query);
    }

    public void clearGoalsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + GOAL_TABLE_NAME + " WHERE 1=1";
        db.execSQL(query);
    }

    //=========WEIGHT===============================================================

    public boolean addWeightData(String date, float weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEIGHT_COL2, date);
        contentValues.put(WEIGHT_COL3, weight);
        Log.d(WEIGHT_TABLE_NAME, "adding weight: " + weight);
        long results = db.insert(WEIGHT_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getWeightData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + WEIGHT_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void dropWeight(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + WEIGHT_TABLE_NAME + " WHERE " + WEIGHT_COL1 + " = " + ID;
        db.execSQL(query);
    }

    public void clearWeightTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + WEIGHT_TABLE_NAME + " WHERE 1=1";
        db.execSQL(query);
    }
}
