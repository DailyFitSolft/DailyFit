package com.dailyFitSoft.dailyfit.dataStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dailyFitSoft.dailyfit.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DailyFit_DB";
    private static final int DATABASE_VERSION = 5;


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
    private static final String PLANNED_EXERCISE_COL5 = "PlannedDate";
    private static final String PLANNED_EXERCISE_COL6 = "PlannedTime";

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
        //dodawanie celow
        //addGoalData("Przebiegnij 10km!", "2019-11-20", false);
        //addGoalData("Przebiegnij 5km!", "2019-11-08", true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableExercise = "CREATE TABLE " + EXERCISE_TABLE_NAME + " ( " + EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXERCISE_COL2 + " TEXT, " + EXERCISE_COL3 + " INTEGER, " + EXERCISE_COL4 + " INTEGER);";
        String createTablePlannedExercise = "CREATE TABLE " + PLANNED_EXERCISE_TABLE_NAME + " ( " + PLANNED_EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLANNED_EXERCISE_COL2 + " INTEGER, " + PLANNED_EXERCISE_COL3 + " INTEGER, " + PLANNED_EXERCISE_COL4 + " INTEGER, " + PLANNED_EXERCISE_COL5 + " TEXT, " + PLANNED_EXERCISE_COL6 + " TEXT);";
        String createTableGoal = "CREATE TABLE " + GOAL_TABLE_NAME + " ( " + GOAL_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL_COL2 + " TEXT, " + GOAL_COL3 + " TEXT, " + GOAL_COL4 + " INTEGER);";
        String createTableWeight = "CREATE TABLE " + WEIGHT_TABLE_NAME + " ( " + WEIGHT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WEIGHT_COL2 + " TEXT, " + WEIGHT_COL3 + " INTEGER);";

        sqLiteDatabase.execSQL(createTableExercise);
        sqLiteDatabase.execSQL(createTablePlannedExercise);
        sqLiteDatabase.execSQL(createTableGoal);
        sqLiteDatabase.execSQL(createTableWeight);
        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
            ") VALUES ('Bieganie', 5, 20)");
        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
                ") VALUES ('Nordic walking', 3, 10)");
        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
                ") VALUES ('Piłka nożna', 7, 25)");
        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +
                GOAL_COL4 + ") VALUES ('Przebiegnij 10 km!', '20-11-2019 00:00:00', '0')");
        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +
                GOAL_COL4 + ") VALUES ('Przebiegnij 5 km!', '08-11-2019 00:00:00', '1')");
        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +
                GOAL_COL4 + ") VALUES ('Przejdź 5 km!', '06-11-2019 00:00:00', '0')");




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLANNED_EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WEIGHT_TABLE_NAME);

        onCreate(sqLiteDatabase);
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

    public List<Exercise> getExerciseList() {
        Cursor data = this.getExerciseData();
        List<Exercise> exercises = new ArrayList<>();
        while(data.moveToNext()){
            exercises.add(new Exercise(data.getInt(0), data.getString(1), data.getInt(2), data.getInt(3)));
        }
        return exercises;
    }

    //========PLANNED=EXERCISE======================================================

    public boolean addPlannedExerciseData(int exerciseID, int trainTime, int repeatCount, String date,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANNED_EXERCISE_COL2, exerciseID);
        contentValues.put(PLANNED_EXERCISE_COL3, trainTime);
        contentValues.put(PLANNED_EXERCISE_COL4, repeatCount);
        contentValues.put(PLANNED_EXERCISE_COL5, date);
        contentValues.put(PLANNED_EXERCISE_COL6, time);
        Log.d(PLANNED_EXERCISE_TABLE_NAME, "adding plannedExercise: " + exerciseID);
        long results = db.insert(PLANNED_EXERCISE_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getPlannedExerciseData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PLANNED_EXERCISE_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    private Cursor getPlannedExerciseData(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PLANNED_EXERCISE_TABLE_NAME + " WHERE " + PLANNED_EXERCISE_COL5 + " = " + DateFormatter.stringFromDate(date);
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

    private List<PlannedExercise> getPlannedExerciseList(Cursor data){
        List<PlannedExercise> plannedExercises = new ArrayList<>();
        while(data.moveToNext()){
            try {
//                plannedExercises.add(new PlannedExercise(data.getInt(0),
//                        data.getInt(1),
//                        data.getInt(2),
//                        data.getInt(3),
//                        DateFormatter.dateFromString(data.getString(4))),
//                        data.getString(5));
                plannedExercises.add(new PlannedExercise(data.getInt(0),data.getInt(1),data.getInt(2),data.getInt(3),DateFormatter.dateFromString(data.getString(4)),data.getString(5)));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return  plannedExercises;
    }

    public List<PlannedExercise> getPlannedExercisesList() {
        return getPlannedExerciseList(getPlannedExerciseData());
    }

    public List<PlannedExercise> getPlannedExerciseList(Date date) {
        return  getPlannedExerciseList(getPlannedExerciseData(date));
    }

    //==========GOALS=================================================================

    public boolean addGoalData(String goalName, String endDate, boolean isAchived) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COL2, goalName);
        contentValues.put(GOAL_COL3, endDate);
        contentValues.put(GOAL_COL4, !isAchived ? 0 : 1);
        Log.d(GOAL_TABLE_NAME, "adding goal: " + goalName);
        long results = db.insert(GOAL_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getGoalData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + GOAL_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    private Cursor getGoalData(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + GOAL_TABLE_NAME + " WHERE " + GOAL_COL3 + " = " + DateFormatter.stringFromDate(date);
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

    private List<Goal> getGoalList(Cursor data){
        List<Goal> goals = new ArrayList<>();
        while(data.moveToNext()){
            try {
                goals.add(new Goal(data.getInt(0), data.getString(1), DateFormatter.dateFromString(data.getString(2)), data.getInt(3) != 0));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return goals;
    }

    public List<Goal> getGoalList(){
        return getGoalList(getGoalData());
    }

    public List<Goal> getGoalList(Date date){
        return getGoalList(getGoalData(date));
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

    private Cursor getWeightData(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + WEIGHT_TABLE_NAME + " WHERE " + WEIGHT_COL2 + " = " + DateFormatter.stringFromDate(date);
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

    private List<Weight> getWeightList(Cursor data){
        List<Weight> weights = new ArrayList<>();
        while(data.moveToNext()){
            try {
                weights.add(new Weight(data.getInt(0), DateFormatter.dateFromString(data.getString(1)), data.getFloat(2)));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return weights;
    }

    public List<Weight> getWeightList(){
        return getWeightList(getWeightData());
    }

    public List<Weight> getWeightList(Date date){
        return getWeightList(getWeightData(date));
    }
}
