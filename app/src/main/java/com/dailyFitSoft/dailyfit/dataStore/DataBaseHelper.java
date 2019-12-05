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
    private static final int DATABASE_VERSION = 6;


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
    private static final String GOAL_COL2 = "GoalType";
    private static final String GOAL_COL3 = "EndDate";
    private static final String GOAL_COL4 = "Achived";
    private static final String GOAL_COL5 = "AchivedValue";
    private static final String GOAL_COL6 =  "ValueToAchive";

    private static final String WEIGHT_TABLE_NAME = "weight";
    private static final String WEIGHT_COL1 = "ID";
    private static final String WEIGHT_COL2 = "Date";
    private static final String WEIGHT_COL3 = "Weight";

    private static final String TRAINING_TABLE_NAME = "training";
    private static final String TRAINING_COL1 = "ID";
    private static final String TRAINING_COL2 = "ActivityType";
    private static final String TRAINING_COL3 = "StartTime";
    private static final String TRAINING_COL4 = "StopTime";
    private static final String TRAINING_COL5 = "StartDate";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableExercise = "CREATE TABLE " + EXERCISE_TABLE_NAME + " ( " + EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXERCISE_COL2 + " TEXT, " + EXERCISE_COL3 + " INTEGER, " + EXERCISE_COL4 + " INTEGER);";
        String createTablePlannedExercise = "CREATE TABLE " + PLANNED_EXERCISE_TABLE_NAME + " ( " + PLANNED_EXERCISE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLANNED_EXERCISE_COL2 + " INTEGER, " + PLANNED_EXERCISE_COL3 + " INTEGER, " + PLANNED_EXERCISE_COL4 + " INTEGER, " + PLANNED_EXERCISE_COL5 + " TEXT, " + PLANNED_EXERCISE_COL6 + " TEXT);";
        String createTableGoal = "CREATE TABLE " + GOAL_TABLE_NAME + " ( " + GOAL_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GOAL_COL2 + " INTEGER, " + GOAL_COL3 + " TEXT, " + GOAL_COL4 + " INTEGER, " + GOAL_COL5 + " INTEGER, " + GOAL_COL6 + " INTEGER);";
        String createTableWeight = "CREATE TABLE " + WEIGHT_TABLE_NAME + " ( " + WEIGHT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WEIGHT_COL2 + " TEXT, " + WEIGHT_COL3 + " INTEGER);";
        String createTableTraining = "CREATE TABLE " + TRAINING_TABLE_NAME + " ( " + TRAINING_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TRAINING_COL2 + " TEXT, " + TRAINING_COL3 + " TEXT, " + TRAINING_COL4 + " TEXT, " + TRAINING_COL5 + " TEXT);";


        sqLiteDatabase.execSQL(createTableExercise);
        sqLiteDatabase.execSQL(createTablePlannedExercise);
        sqLiteDatabase.execSQL(createTableGoal);
        sqLiteDatabase.execSQL(createTableWeight);
        sqLiteDatabase.execSQL(createTableTraining);

        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
            ") VALUES ('Bieganie', 5, 20)");
        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
                ") VALUES ('Nordic walking', 3, 10)");
        sqLiteDatabase.execSQL(" INSERT INTO " + EXERCISE_TABLE_NAME +"( " + EXERCISE_COL2 + " , " + EXERCISE_COL3 + "," + EXERCISE_COL4 +
                ") VALUES ('Piłka nożna', 7, 25)");
//        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +
//                GOAL_COL4 + ") VALUES ('Przebiegnij 10 km!', '20-11-2019 00:00:00', '0')");
//        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +
//                GOAL_COL4 + ") VALUES ('Przebiegnij 5 km!', '08-11-2019 00:00:00', '1')");
      
        sqLiteDatabase.execSQL(" INSERT INTO " + GOAL_TABLE_NAME + "(" + GOAL_COL2 + "," + GOAL_COL3 + "," +	
                GOAL_COL4 + "," + GOAL_COL5  + "," + GOAL_COL6 + ") VALUES (1, '30-11-2019 00:00:00', '0', 100, 200)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLANNED_EXERCISE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WEIGHT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRAINING_TABLE_NAME);

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

    public boolean addGoalData(GoalType goalType, String endDate, boolean isAchived, int valueToAchive, int achivedValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COL2, goalType.getValue());
        contentValues.put(GOAL_COL3, endDate);
        contentValues.put(GOAL_COL4, !isAchived ? 0 : 1);
        contentValues.put(GOAL_COL5, achivedValue);
        contentValues.put(GOAL_COL6, valueToAchive);
        Log.d(GOAL_TABLE_NAME, "adding goal: " + goalType.toString());
        long results = db.insert(GOAL_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    public boolean addGoalData(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL_COL2, goal.getGoalType().getValue());
        contentValues.put(GOAL_COL3, DateFormatter.stringFromDate(goal.getEndDate()));
        contentValues.put(GOAL_COL4, !goal.isAchived()? 0 : 1);
        contentValues.put(GOAL_COL5, goal.getAchivedValue());
        contentValues.put(GOAL_COL6, goal.getValueToAchive());
        Log.d(GOAL_TABLE_NAME, "adding goal: " + goal.getGoalType().toString());
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
                goals.add(new Goal(data.getInt(0), GoalType.valueOf(data.getInt(1)), DateFormatter.dateFromString(data.getString(2)), data.getInt(3) != 0, data.getInt(4), data.getInt(5)));
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

    public void updateGoalArchivedValue(Goal goal,int newValue)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + GOAL_TABLE_NAME + " SET " + GOAL_COL5 + " = " + newValue + " WHERE " + GOAL_COL1 + " = " + goal.getID();
        db.execSQL(query);
        Log.d(GOAL_TABLE_NAME, "updated goal: " + goal.getID() + " new value:" + newValue);
    }
    public void setGoalArchived(Goal goal,boolean archived)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + GOAL_TABLE_NAME + " SET " + GOAL_COL4+ " = " + (!archived? 0 : 1) + " WHERE " + GOAL_COL1 + " = " + goal.getID();
        db.execSQL(query);
        Log.d(GOAL_TABLE_NAME, "updated goal: " + goal.getID() + " set archived?:" + goal.isAchived());
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

    //==========GOALS=================================================================

    public boolean addTrainingData(String activityType, String startTime, String stopTime, String startDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRAINING_COL2, activityType);
        contentValues.put(TRAINING_COL3, startTime);
        contentValues.put(TRAINING_COL4, stopTime);
        contentValues.put(TRAINING_COL5, startDate);


        Log.d(TRAINING_TABLE_NAME, "adding training: " + activityType + ", date and time: " + startDate + " " + startTime + " - " + stopTime);
        long results = db.insert(TRAINING_TABLE_NAME, null, contentValues);
        return (results != -1);
    }

    private Cursor getTrainingData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TRAINING_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    private Cursor getTrainingData(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TRAINING_TABLE_NAME + " WHERE " + TRAINING_COL5 + " = " + DateFormatter.stringFromDate(date);
        return db.rawQuery(query, null);
    }

    public void dropTraining(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TRAINING_TABLE_NAME + " WHERE " + TRAINING_COL1 + " = " + ID;
        db.execSQL(query);
    }

    public void clearTrainingTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TRAINING_TABLE_NAME + " WHERE 1=1";
        db.execSQL(query);
    }

    private List<Training> getTrainingsList(Cursor data){
        List<Training> trainings = new ArrayList<>();
        while(data.moveToNext()){
            try {
                trainings.add(new Training(data.getInt(0), data.getString(1), data.getString(2), data.getString(3), DateFormatter.dateFromString(data.getString(4))));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return trainings;
    }

    public List<Training> getTrainingsList(){
        return getTrainingsList(getTrainingData());
    }

    public List<Training> getTrainingsList(Date date){
        return getTrainingsList(getTrainingData(date));
    }
}


