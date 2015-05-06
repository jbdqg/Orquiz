package com.daam.orquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import data.Answer;
import data.Question;

/**
 * Created by johnny on 02-05-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Orquiz";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTION = "Question";
    private static final String KEY_QUESTION_ID = "question_id";
    private static final String KEY_QUESTION_TEXT = "question_text";
    private static final String KEY_QUESTION_URL = "question_url";
    private static final String KEY_QUESTION_TYPE = "question_type";
    private static final String KEY_QUESTION_ORDER = "question_order";
    private static final String KEY_QUESTION_ANSWERRANDOM = "question_answerrandom";
    private static final String KEY_QUESTION_MINPOINTS = "question_minpoints";
    private static final String KEY_QUESTION_TIMELIMIT = "question_timelimit";
    private static final String KEY_QUESTION_ANSWERCORRECT = "question_answercorrect";

    private static final String TABLE_ANSWER = "Answer";
    private static final String KEY_ANSWER_ID = "answer_id";
    //private static final String KEY_ANS
    // VER_QUESTIONID = "question_id";
    private static final String KEY_ANSWER_TEXT = "answer_text";
    private static final String KEY_ANSWER_URL = "answer_url";
    private static final String KEY_ANSWER_POINTS = "answer_points";
    private static final String KEY_ANSWER_ORDER = "answer_order";
    private static final String KEY_ANSWER_CORRECT = "answer_correct";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTION + "("
                + KEY_QUESTION_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TEXT + " TEXT,"
                + KEY_QUESTION_URL + " TEXT,"
                + KEY_QUESTION_TYPE + " TEXT,"
                + KEY_QUESTION_ORDER + " INTEGER,"
                + KEY_QUESTION_ANSWERRANDOM + " INTEGER,"
                + KEY_QUESTION_MINPOINTS + " INTEGER,"
                + KEY_QUESTION_TIMELIMIT + " INTEGER,"
                + KEY_QUESTION_ANSWERCORRECT + " INTEGER"
                + ")";
        db.execSQL(CREATE_QUESTION_TABLE);

        String CREATE_ANSWER_TABLE = "CREATE TABLE " + TABLE_ANSWER + "("
                + KEY_ANSWER_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_ID + " INTEGER,"
                + KEY_ANSWER_TEXT + " TEXT,"
                + KEY_ANSWER_URL + " TEXT,"
                + KEY_ANSWER_POINTS + " INTEGER,"
                + KEY_ANSWER_ORDER + " INTEGER,"
                + KEY_ANSWER_CORRECT + " INTEGER"
                + ")";
        db.execSQL(CREATE_ANSWER_TABLE);

        String sqlInsertQ1 = "INSERT INTO QUESTION(" + KEY_QUESTION_ID + ", " + KEY_QUESTION_TEXT + ", " + KEY_QUESTION_URL + ", "
                                                     + KEY_QUESTION_TYPE + ", " + KEY_QUESTION_ORDER + ", "
                                                     + KEY_QUESTION_ANSWERRANDOM + ", " + KEY_QUESTION_MINPOINTS + ", "
                                                     + KEY_QUESTION_TIMELIMIT + ", " + KEY_QUESTION_ANSWERCORRECT
                                       + ") VALUES( 1, 'What is the name of the leading character?', '/home/johnny/develop/Orquiz/app/src/main/res/drawable-hdpi/30405805_.jpg', 'multiplechoice', 1, 1, 0, 10, 1"
                                       + ")";
        db.execSQL(sqlInsertQ1);

        String sqlInsertQ1A1 = "INSERT INTO ANSWER(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + KEY_ANSWER_TEXT + ", " + KEY_ANSWER_URL + ", "
                                                     + KEY_ANSWER_POINTS + ", " + KEY_ANSWER_ORDER + ", " + KEY_ANSWER_CORRECT
                                       + ") VALUES( 1, 1, 'Georgie', null, 0, 1, 0"
                                       + ")";
        db.execSQL(sqlInsertQ1A1);

        String sqlInsertQ1A2 = "INSERT INTO ANSWER(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + KEY_ANSWER_TEXT + ", " + KEY_ANSWER_URL + ", "
                                                     + KEY_ANSWER_POINTS + ", " + KEY_ANSWER_ORDER + ", " + KEY_ANSWER_CORRECT
                                       + ") VALUES( 2, 1, 'Alex', null, 0, 2, 1"
                                       + ")";
        db.execSQL(sqlInsertQ1A2);

        String sqlInsertQ1A3 = "INSERT INTO ANSWER(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + KEY_ANSWER_TEXT + ", " + KEY_ANSWER_URL + ", "
                                                     + KEY_ANSWER_POINTS + ", " + KEY_ANSWER_ORDER + ", " + KEY_ANSWER_CORRECT
                                       + ") VALUES( 3, 1, 'Pete', null, 0, 2, 0"
                                       + ")";
        db.execSQL(sqlInsertQ1A3);

        String sqlInsertQ1A4 = "INSERT INTO ANSWER(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + KEY_ANSWER_TEXT + ", " + KEY_ANSWER_URL + ", "
                                                     + KEY_ANSWER_POINTS + ", " + KEY_ANSWER_ORDER + ", " + KEY_ANSWER_CORRECT
                                       + ") VALUES( 4, 1, 'Dim', null, 0, 2, 0"
                                       + ")";
        db.execSQL(sqlInsertQ1A4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(db);
    }

    public int getAllQuestionsCount() {
        String selectQuery = "SELECT count(*) FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int questionsCount = 0;
        if (cursor.moveToFirst()) {
            do {
                questionsCount = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return questionsCount;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();

        String selectQuery = "SELECT * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_id(Integer.parseInt(cursor.getString(0)));
                question.setQuestion_text(cursor.getString(1));
                question.setQuestion_url(cursor.getString(2));

                questionList.add(question);
            } while (cursor.moveToNext());
        }
        return questionList;
    }

    public int getAllQuestionAnswersCount(int question_id) {
        String selectQuery = "SELECT count(*) FROM " + TABLE_ANSWER + " WHERE " + KEY_QUESTION_ID + " = " + question_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int questionsCount = 0;
        if (cursor.moveToFirst()) {
            do {
                questionsCount = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return questionsCount;
    }

    public List<Answer> getAllQuestionAnswers(int question_id) {
        List<Answer> questionAnswersList = new ArrayList<Answer>();

        //TODO: colocar nomes das tabelas de que se obtêm dados
        String selectQuery = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + KEY_QUESTION_ID + " = " + question_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setAnswer_id(Integer.parseInt(cursor.getString(0)));
                answer.setAnswerQuestion_id(Integer.parseInt(cursor.getString(0)));
                answer.setAnswer_text(cursor.getString(1));
                answer.setAnswer_url(cursor.getString(2));

                questionAnswersList.add(answer);
            } while (cursor.moveToNext());
        }
        return questionAnswersList;
    }


}
