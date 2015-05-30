package com.daam.orquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;
import com.daam.orquiz.data.Question;
import com.daam.orquiz.data.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnny on 02-05-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Orquiz";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PARTICIPANT = "Participant";
    private static final String KEY_PARTICIPANT_ID = "participant_id";
    private static final String FIELD_PARTICIPANT_NAME = "participant_name";
    private static final String FIELD_PARTICIPANT_URL = "participant_url";
    private static final String FIELD_PARTICIPANT_FACEBOOKID = "participant_facebookid";

    private static final String TABLE_PARTICIPATION = "Participation";
    private static final String KEY_PARTICIPATION_ID = "participation_id";
    private static final String FIELD_PARTICIPATION_START = "participation_start";
    private static final String FIELD_PARTICIPATION_END = "participation_end";
    private static final String FIELD_PARTICIPATION_TOTALTIME = "participation_totaltime";
    private static final String FIELD_PARTICIPATION_POINTS = "participation_points";
    private static final String FIELD_PARTICIPATION_RANKING = "participation_ranking";
    private static final String FIELD_PARTICIPATION_STATUS = "participation_status";

    private static final String TABLE_PARTICIPATIONQUESTION = "ParticipationQuestion";
    private static final String KEY_PARTICIPATIONQUESTION_ID = "participationquestion_id";
    private static final String FIELD_PARTICIPATIONQUESTION_POINTS = "participationquestion_points";
    private static final String FIELD_PARTICIPATIONQUESTION_ANSWERSJSON = "participationquestion_answersjson";
    private static final String FIELD_PARTICIPATIONQUESTION_SERVERSTART = "participationquestion_serverstart";
    private static final String FIELD_PARTICIPATIONQUESTION_SERVEREND = "participationquestion_serverend";
    private static final String FIELD_PARTICIPATIONQUESTION_CLIENTSTART = "participationquestion_clientstart";
    private static final String FIELD_PARTICIPATIONQUESTION_CLIENTEND = "participationquestion_clientend";
    private static final String FIELD_PARTICIPATIONQUESTION_ANSWERTIME = "participationquestion_answertime";
    private static final String FIELD_PARTICIPATIONQUESTION_ORDER = "participationquestion_order";


    private static final String TABLE_QUIZ = "Quiz";
    private static final String KEY_QUIZ_ID = "quiz_id";
    private static final String FIELD_QUIZ_REFERENCE = "quiz_reference";
    private static final String FIELD_QUIZ_NAME = "quiz_name";
    private static final String FIELD_QUIZ_DESCRIPTION = "quiz_description";
    private static final String FIELD_QUIZ_URL = "quiz_url";
    private static final String FIELD_QUIZ_QUESTIONSRANDOM = "quiz_questionsrandom";
    private static final String FIELD_QUIZ_QUESTIONSNUMBER = "quiz_questionsnumber";
    private static final String FIELD_QUIZ_CONSIDERTIME = "quiz_considertime";

    private static final String TABLE_QUESTION = "Question";
    private static final String KEY_QUESTION_ID = "question_id";
    private static final String FIELD_QUESTION_TEXT = "question_text";
    private static final String FIELD_QUESTION_URL = "question_url";
    private static final String FIELD_QUESTION_TYPE = "question_type";
    private static final String FIELD_QUESTION_ORDER = "question_order";
    private static final String FIELD_QUESTION_ANSWERRANDOM = "question_answerrandom";
    private static final String FIELD_QUESTION_MINPOINTS = "question_minpoints";
    private static final String FIELD_QUESTION_TIMELIMIT = "question_timelimit";
    private static final String FIELD_QUESTION_ANSWERCORRECT = "question_answercorrect";

    private static final String TABLE_ANSWER = "Answer";
    private static final String KEY_ANSWER_ID = "answer_id";
    //private static final String KEY_ANS
    // VER_QUESTIONID = "question_id";
    private static final String FIELD_ANSWER_TEXT = "answer_text";
    private static final String FIELD_ANSWER_URL = "answer_url";
    private static final String FIELD_ANSWER_POINTS = "answer_points";
    private static final String FIELD_ANSWER_ORDER = "answer_order";
    private static final String FIELD_ANSWER_CORRECT = "answer_correct";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPATIONQUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTICIPANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);

        String CREATE_PARTICIPANT_TABLE = "CREATE TABLE " + TABLE_PARTICIPANT + "("
                + KEY_PARTICIPANT_ID + " INTEGER PRIMARY KEY,"
                + FIELD_PARTICIPANT_NAME + " TEXT,"
                + FIELD_PARTICIPANT_URL + " TEXT,"
                + FIELD_PARTICIPANT_FACEBOOKID + " TEXT"
                + ")";
        db.execSQL(CREATE_PARTICIPANT_TABLE);

        String CREATE_PARTICIPATION_TABLE = "CREATE TABLE " + TABLE_PARTICIPATION + "("
                + KEY_PARTICIPATION_ID + " INTEGER PRIMARY KEY,"
                + KEY_PARTICIPANT_ID + " INTEGER,"
                + FIELD_PARTICIPATION_START + " INTEGER,"
                + FIELD_PARTICIPATION_END + " INTEGER,"
                + FIELD_PARTICIPATION_TOTALTIME + " INTEGER,"
                + FIELD_PARTICIPATION_POINTS + " INTEGER,"
                + FIELD_PARTICIPATION_RANKING + " TEXT,"
                + FIELD_PARTICIPATION_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_PARTICIPATION_TABLE);


        String CREATE_PARTICIPATIONQUESTION_TABLE = "CREATE TABLE " + TABLE_PARTICIPATIONQUESTION + "("
                + KEY_PARTICIPATIONQUESTION_ID + " INTEGER PRIMARY KEY,"
                + KEY_PARTICIPATION_ID + " INTEGER,"
                + KEY_QUESTION_ID + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_POINTS + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_ANSWERSJSON + " TEXT,"
                + FIELD_PARTICIPATIONQUESTION_SERVERSTART + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_SERVEREND + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_CLIENTSTART + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_CLIENTEND + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_ANSWERTIME + " INTEGER,"
                + FIELD_PARTICIPATIONQUESTION_ORDER + " INTEGER"
                + ")";
        db.execSQL(CREATE_PARTICIPATIONQUESTION_TABLE);

        String CREATE_QUIZ_TABLE = "CREATE TABLE " + TABLE_QUIZ + "("
                + KEY_QUIZ_ID + " INTEGER PRIMARY KEY,"
                + FIELD_QUIZ_REFERENCE + " TEXT,"
                + FIELD_QUIZ_NAME + " TEXT,"
                + FIELD_QUIZ_DESCRIPTION + " TEXT,"
                + FIELD_QUIZ_URL + " TEXT,"
                + FIELD_QUIZ_QUESTIONSRANDOM + " INTEGER,"
                + FIELD_QUIZ_QUESTIONSNUMBER + " INTEGER,"
                + FIELD_QUIZ_CONSIDERTIME + " INTEGER"
                + ")";
        db.execSQL(CREATE_QUIZ_TABLE);

        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTION + "("
                + KEY_QUESTION_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUIZ_ID + " INTEGER,"
                + FIELD_QUESTION_TEXT + " TEXT,"
                + FIELD_QUESTION_URL + " TEXT,"
                + FIELD_QUESTION_TYPE + " TEXT,"
                + FIELD_QUESTION_ORDER + " INTEGER,"
                + FIELD_QUESTION_ANSWERRANDOM + " INTEGER,"
                + FIELD_QUESTION_MINPOINTS + " INTEGER,"
                + FIELD_QUESTION_TIMELIMIT + " INTEGER,"
                + FIELD_QUESTION_ANSWERCORRECT + " INTEGER"
                + ")";
        db.execSQL(CREATE_QUESTION_TABLE);

        String CREATE_ANSWER_TABLE = "CREATE TABLE " + TABLE_ANSWER + "("
                + KEY_ANSWER_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_ID + " INTEGER,"
                + FIELD_ANSWER_TEXT + " TEXT,"
                + FIELD_ANSWER_URL + " TEXT,"
                + FIELD_ANSWER_POINTS + " INTEGER,"
                + FIELD_ANSWER_ORDER + " INTEGER,"
                + FIELD_ANSWER_CORRECT + " INTEGER"
                + ")";
        db.execSQL(CREATE_ANSWER_TABLE);

        String sqlInsertP = "INSERT INTO " + TABLE_PARTICIPANT + "(" + KEY_PARTICIPANT_ID + ", " + FIELD_PARTICIPANT_NAME + ", " + FIELD_PARTICIPANT_URL + ", "
                + FIELD_PARTICIPANT_FACEBOOKID
                + ") VALUES( 1, 'defaultuser', null, null"
                + ")";
        db.execSQL(sqlInsertP);

        String sqlInsertQU1 = "INSERT INTO " + TABLE_QUIZ + "(" + KEY_QUIZ_ID + ", " + FIELD_QUIZ_REFERENCE + ", " + FIELD_QUIZ_NAME + ", "
                + FIELD_QUIZ_DESCRIPTION + ", " + FIELD_QUIZ_URL + ", " + FIELD_QUIZ_QUESTIONSRANDOM + ", "
                + FIELD_QUIZ_QUESTIONSNUMBER + ", " + FIELD_QUIZ_CONSIDERTIME
                + ") VALUES( 1, 'QCO', 'Clockwork Orange', 'A Quiz About Clockwork Orange', null, 1, 3, 0"
                + ")";
        db.execSQL(sqlInsertQU1);

        String sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 1, 1, 'Who wrote the novel upon which the film A Clockwork Orange is based?', null, 'multiplechoice', 1, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

        String sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 1, 1, 'Stanley Kubrick', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        String sqlInsertQ1A2 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 2, 1, 'Ken Kesey', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A2);

        String sqlInsertQ1A3 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 3, 1, 'Anthony Burgess', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A3);

        String sqlInsertQ1A4 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 4, 1, 'Malcolm McDowell', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A4);

        String sqlInsertQ2 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", "  + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT + ", " + FIELD_QUESTION_URL + ", "
                                                     + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                                                     + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                                                     + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                                       + ") VALUES( 2, 1, 'What is the name of the leading character?', '/home/johnny/develop/Orquiz/app/src/main/res/drawable-hdpi/30405805_.jpg', 'uniquechoice', 2, 1, 0, 10, 1"
                                       + ")";
        db.execSQL(sqlInsertQ2);

        String sqlInsertQ2A5 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                                                     + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                                       + ") VALUES( 5, 2, 'Georgie', null, 0, 1, 0"
                                       + ")";
        db.execSQL(sqlInsertQ2A5);

        String sqlInsertQ2A6 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                                                     + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                                       + ") VALUES( 6, 2, 'Alex', null, 1, 2, 1"
                                       + ")";
        db.execSQL(sqlInsertQ2A6);

        String sqlInsertQ2A7 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                                                     + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                                       + ") VALUES( 7, 2, 'Pete', null, 0, 3, 0"
                                       + ")";
        db.execSQL(sqlInsertQ2A7);

        String sqlInsertQ2A8 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                                                     + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                                       + ") VALUES( 8, 2, 'Dim', null, 0, 4, 0"
                                       + ")";
        db.execSQL(sqlInsertQ2A8);

        String sqlInsertQ3 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", "  + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 3, 1, 'What drink do Alex and his gang consume on the night the film opens?', null, 'multiplechoice', 3, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ3);

        String sqlInsertQ3A9 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 9, 3, 'Vodka', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ3A9);

        String sqlInsertQ3A10 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 10, 3, 'Whiskey', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ3A10);

        String sqlInsertQ3A11 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 11, 3, 'Milk', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ3A11);

        String sqlInsertQ3A12 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 12, 3, 'Water', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ3A12);

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

    public Question getQuestion(int question_id) {
        Question question = new Question();

        String selectQuery = "SELECT " + KEY_QUESTION_ID + ", "
                                       + KEY_QUIZ_ID + ", "
                                       + FIELD_QUESTION_TEXT + ", "
                                       + FIELD_QUESTION_URL + ", "
                                       + FIELD_QUESTION_TYPE + ", "
                                       + FIELD_QUESTION_ORDER + ", "
                                       + FIELD_QUESTION_ANSWERRANDOM + ", "
                                       + FIELD_QUESTION_TIMELIMIT
                           + " FROM " + TABLE_QUESTION
                           + " WHERE " + KEY_QUESTION_ID + " = " + question_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                question.setFieldId(Integer.parseInt(cursor.getString(0)));
                question.setFieldQuizid(Integer.parseInt(cursor.getString(1)));
                if(!cursor.isNull(2)){
                    question.setFieldText(cursor.getString(2));
                }
                if(!cursor.isNull(3)){
                    question.setFieldUrl(cursor.getString(3));
                }
                if(!cursor.isNull(4)){
                    question.setFieldType(cursor.getString(4));
                }
                if(!cursor.isNull(5)){
                    question.setFieldOrder(Integer.parseInt(cursor.getString(5)));
                }
                if(!cursor.isNull(6)){
                    question.setFieldAnswerrandom(Integer.parseInt(cursor.getString(6)));
                }
                if(!cursor.isNull(7)){
                    question.setFieldTimelimit(Integer.parseInt(cursor.getString(7)));
                }
            } while (cursor.moveToNext());
        }
        return question;
    }

    public Quiz getQuiz(int quiz_id) {
        Quiz quiz = new Quiz();

        String selectQuery = "SELECT * FROM " + TABLE_QUIZ + " WHERE " + KEY_QUIZ_ID + " = " + quiz_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                quiz.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    quiz.setFieldReference(cursor.getString(1));
                }
                if(!cursor.isNull(2)){
                    quiz.setFieldName(cursor.getString(2));
                }
                if(!cursor.isNull(3)){
                    quiz.setFieldDescription(cursor.getString(3));
                }
                if(!cursor.isNull(4)){
                    quiz.setFieldUrl(cursor.getString(4));
                }
                if(!cursor.isNull(5)){
                    quiz.setFieldQuestionsrandom(Integer.parseInt(cursor.getString(5)));
                }
                if(!cursor.isNull(6)){
                    quiz.setFieldQuestionsnumber(Integer.parseInt(cursor.getString(6)));
                }
                if(!cursor.isNull(7)){
                    quiz.setFieldConsidertime(Integer.parseInt(cursor.getString(7)));
                }
            } while (cursor.moveToNext());
        }
        return quiz;
    }

    public ParticipationQuestion getParticipationQuestion(int participation_id, int question_id) {
        ParticipationQuestion participationQuestion = new ParticipationQuestion();

        String selectQuery = "SELECT * FROM " + TABLE_PARTICIPATIONQUESTION + " WHERE " + KEY_PARTICIPATION_ID + " = " + participation_id + " AND " + KEY_QUESTION_ID + " = " + question_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                participationQuestion.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    participationQuestion.setFieldParticipationid(Integer.parseInt(cursor.getString(1)));
                }
                if(!cursor.isNull(2)){
                    participationQuestion.setFieldQuestionid(Integer.parseInt(cursor.getString(2)));
                }
                if(!cursor.isNull(3)){
                    participationQuestion.setFieldPoints(Integer.parseInt(cursor.getString(3)));
                }
                if(!cursor.isNull(4)){
                    participationQuestion.setFieldAnswersjson(cursor.getString(4));
                }
                if(!cursor.isNull(5)){
                    participationQuestion.setFieldServerstart(Long.parseLong(cursor.getString(5)));
                }
                if(!cursor.isNull(6)){
                    participationQuestion.setFieldServerend(Long.parseLong(cursor.getString(6)));
                }
                if(!cursor.isNull(7)){
                    participationQuestion.setFieldClientstart(Long.parseLong(cursor.getString(7)));
                }
                if(!cursor.isNull(8)){
                    participationQuestion.setFieldClientend(Long.parseLong(cursor.getString(8)));
                }
                if(!cursor.isNull(9)){
                    participationQuestion.setFieldAnswertime(Integer.parseInt(cursor.getString(9)));
                }
                if(!cursor.isNull(10)){
                    participationQuestion.setFieldAnswertime(Integer.parseInt(cursor.getString(10)));
                }
            } while (cursor.moveToNext());
        }
        return participationQuestion;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();

        String selectQuery = "SELECT * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    question.setFieldText(cursor.getString(1));
                }
                if (!cursor.isNull(2)) {
                    question.setFieldUrl(cursor.getString(2));
                }
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

    public List<Answer> getAllQuestionAnswers(int question_id, int randomAnswers) {
        List<Answer> questionAnswersList = new ArrayList<Answer>();

        //TODO: colocar nomes das tabelas de que se obtêm dados
        String selectQuery = "SELECT " + KEY_ANSWER_ID + ","
                                       + KEY_QUESTION_ID  + ","
                                       + FIELD_ANSWER_TEXT  + ","
                                       + FIELD_ANSWER_URL
                                       + " FROM " + TABLE_ANSWER + " WHERE " + KEY_QUESTION_ID + " = " + question_id;

        if(randomAnswers == 1){
            selectQuery += " ORDER BY RANDOM()";
        }else{
            selectQuery += " ORDER BY " + FIELD_ANSWER_ORDER + " ASC";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setFieldId(Integer.parseInt(cursor.getString(0)));
                answer.setFieldQuestionid(Integer.parseInt(cursor.getString(1)));
                if(!cursor.isNull(2)){
                    answer.setFieldText(cursor.getString(2));
                }
                if(!cursor.isNull(3)){
                    answer.setFieldUrl(cursor.getString(3));
                }
                if(!cursor.isNull(4)){
                    answer.setFieldPoints(Integer.parseInt(cursor.getString(4)));
                }
                questionAnswersList.add(answer);
            } while (cursor.moveToNext());
        }
        return questionAnswersList;
    }

    public ArrayList validateGivenAnswer(int answer_id){

        ArrayList answerInfo = new ArrayList();

        String selectQuery = "SELECT " + FIELD_ANSWER_CORRECT + ", " + FIELD_ANSWER_POINTS+ " FROM " + TABLE_ANSWER + " WHERE " + KEY_ANSWER_ID + " = " + answer_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                answerInfo.add(0, Integer.parseInt(cursor.getString(0)));
                answerInfo.add(1, Integer.parseInt(cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        return answerInfo;

    }

    public int getQuizQuestionsNumber (int quiz_id){

        String selectQuery = "SELECT " + FIELD_QUIZ_QUESTIONSNUMBER + " FROM " + TABLE_QUIZ + " WHERE " + KEY_QUIZ_ID + " = " + quiz_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int quizQuestionsNumber = 0;
        if (cursor.moveToFirst()) {
            do {
                quizQuestionsNumber = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return quizQuestionsNumber;

    }

    public Participation getLastActiveParticipation() {

        int participant_id = MainActivity.PARTICIPANT_ID;

        Participation participation = new Participation();

        //obter a última participação sem data de fim para o participante recebido
        String selectQuery = "SELECT " + KEY_PARTICIPATION_ID + ", " + KEY_PARTICIPANT_ID + ", " + FIELD_PARTICIPATION_START + ", " + FIELD_PARTICIPATION_STATUS + " FROM " + TABLE_PARTICIPATION + " WHERE " + KEY_PARTICIPANT_ID + " = " + participant_id + " AND " + FIELD_PARTICIPATION_END + " IS NULL AND " + FIELD_PARTICIPATION_STATUS + " = \"started\" ORDER BY " + KEY_PARTICIPATION_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                participation.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    participation.setFieldParticipantid(Integer.parseInt(cursor.getString(1)));
                }
                if(!cursor.isNull(2)){
                    participation.setFieldStart(Long.parseLong(cursor.getString(2)));
                }
                if(!cursor.isNull(3)){
                    participation.setFieldStatus(cursor.getString(3));
                }
            } while (cursor.moveToNext());
        }

        if (participation.getFieldId() == null){

            participation.setFieldParticipantid(participant_id);
            participation.setFieldStart(System.currentTimeMillis());
            participation.setFieldStatus("started");
            Long insertedParticipationID = this.insertTableRecord("Participation", participation.getContentValues());
            //TODO: todas as keys deviam ser Long por causa da inserção. Alterar se houver tempo
            Integer i = (int) (long) insertedParticipationID;
            participation.setFieldId(i);

        }

        return participation;
    }

    public Participation getLastParticipation() {

        int participant_id = MainActivity.PARTICIPANT_ID;

        Participation participation = new Participation();

        String selectQuery = "SELECT " + KEY_PARTICIPATION_ID + ", " + KEY_PARTICIPANT_ID + ", " + FIELD_PARTICIPATION_TOTALTIME + ", " + FIELD_PARTICIPATION_POINTS + ", " + FIELD_PARTICIPATION_RANKING + " FROM " + TABLE_PARTICIPATION + " WHERE " + KEY_PARTICIPANT_ID + " = " + participant_id + " AND " + FIELD_PARTICIPATION_STATUS + " = \"completed\" ORDER BY " + KEY_PARTICIPATION_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                participation.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    participation.setFieldParticipantid(Integer.parseInt(cursor.getString(1)));
                }
                if(!cursor.isNull(2)){
                    participation.setFieldTotaltime(Integer.parseInt(cursor.getString(2)));
                }
                if(!cursor.isNull(3)){
                    participation.setFieldPoints(Integer.parseInt(cursor.getString(3)));
                }
                if(!cursor.isNull(4)){
                    participation.setFieldRanking(cursor.getString(4));
                }
            } while (cursor.moveToNext());
        }

        return participation;
    }

    public List<ParticipationQuestion> getActiveParticipationQuestions(Participation activeParticipation){

        List<ParticipationQuestion> activeParticipationQuestions = new ArrayList<ParticipationQuestion>();

        String selectQuery = "SELECT * FROM " + TABLE_PARTICIPATIONQUESTION + " WHERE " + KEY_PARTICIPATION_ID + " = " + activeParticipation.getFieldId() + " ORDER BY " + FIELD_PARTICIPATIONQUESTION_ORDER + " ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //TODO: remove - faz o dump do cursor
                DatabaseUtils dbu = new DatabaseUtils();
                dbu.dumpCurrentRow(cursor);

                ParticipationQuestion participationquestion = new ParticipationQuestion();
                participationquestion.setFieldId(Integer.parseInt(cursor.getString(0)));
                participationquestion.setFieldParticipationid(Integer.parseInt(cursor.getString(1)));
                participationquestion.setFieldQuestionid(Integer.parseInt(cursor.getString(2)));
                if(!cursor.isNull(3)){
                    participationquestion.setFieldPoints(Integer.parseInt(cursor.getString(3)));
                }
                if(!cursor.isNull(4)){
                    participationquestion.setFieldAnswersjson(cursor.getString(4));
                }
                if(!cursor.isNull(5)){
                    participationquestion.setFieldServerstart(Long.parseLong(cursor.getString(5)));
                }
                if(!cursor.isNull(6)){
                    participationquestion.setFieldServerend(Long.parseLong(cursor.getString(6)));
                }
                if(!cursor.isNull(7)){
                    participationquestion.setFieldClientstart(Long.parseLong(cursor.getString(7)));
                }
                if(!cursor.isNull(8)){
                    participationquestion.setFieldClientend(Long.parseLong(cursor.getString(8)));
                }
                if(!cursor.isNull(9)){
                    participationquestion.setFieldAnswertime(Integer.parseInt(cursor.getString(9)));
                }
                if(!cursor.isNull(10)){
                    participationquestion.setFieldOrder(Integer.parseInt(cursor.getString(10)));
                }
                activeParticipationQuestions.add(participationquestion);
            } while (cursor.moveToNext());
        }

        return activeParticipationQuestions;

    }

    public Map getNextQuestion(int quiz_id, String questionsAnswered_id, int lastQuestionAnsweredId){

        Map<String, Object> questionData = new HashMap<>();

        //obter o quiz para ver se é random, senão é a seguinte
        Quiz quiz = this.getQuiz(quiz_id);

        if(quiz.getFieldId() != null){

            String selectQuery = "SELECT " + KEY_QUESTION_ID + " FROM " + TABLE_QUESTION + " WHERE " + KEY_QUIZ_ID + " = " + quiz_id;

            if (questionsAnswered_id != ""){
                selectQuery += " AND " + KEY_QUESTION_ID + " NOT IN (" + questionsAnswered_id + ")";
            }

            if(quiz.getFieldQuestionsrandom() == 1){
                selectQuery += " ORDER BY RANDOM() LIMIT 1";
            }else if (quiz.getFieldQuestionsrandom() == 0 || quiz.getFieldQuestionsrandom() == null){
                if (lastQuestionAnsweredId != 0){
                    selectQuery += " AND " + KEY_QUESTION_ID + " = (SELECT " + KEY_QUESTION_ID + " FROM " + TABLE_QUESTION + "WHERE " + FIELD_QUESTION_ORDER + " > (SELECT " + FIELD_QUESTION_ORDER + " FROM " + TABLE_QUESTION + " WHERE " + KEY_QUESTION_ID + " = " + lastQuestionAnsweredId + "))";
                }else{
                    selectQuery += " AND " + KEY_QUESTION_ID + " = (SELECT " + KEY_QUESTION_ID + " FROM " + TABLE_QUESTION + "WHERE " + FIELD_QUESTION_ORDER + " = (SELECT MIN(" + FIELD_QUESTION_ORDER + ") FROM " + TABLE_QUESTION + " WHERE " + KEY_QUIZ_ID + " = " + quiz_id + "))";
                }
            }

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            Integer question_id = null;
            Question nextQuestion = null;

            if (cursor.moveToFirst()) do {
                //obtêm-se os dados da próxima pergunta e instanciam-se pela invoação da função getQuestion
                nextQuestion = getQuestion(Integer.parseInt(cursor.getString(0)));
                questionData.put("question", nextQuestion);

                //agora obtêm-se as respostas possíveis para a pergunta
                List<Answer> possibleAnswers = getAllQuestionAnswers(nextQuestion.getFieldId(), nextQuestion.getFieldAnswerrandom());

                questionData.put("answers", possibleAnswers);

            } while (cursor.moveToNext());
        }

        //se questionsAnswered_is for null é a primeira pergunta, senão são as not in questionAnswered_is
        return questionData;

    }

    public Long insertTableRecord(String tableName, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();

        Long insertedId;

        db.beginTransaction();
        try {
            insertedId = db.insert(tableName, null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return insertedId;

    }

    public int updateTableRecord(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs){

        SQLiteDatabase db = this.getWritableDatabase();

        int updatedId;

        db.beginTransaction();
        try {
            updatedId = db.update(tableName, contentValues, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return updatedId;

    }

    public boolean uploadJsonQuizIntoTables (JSONObject jsonQuiz) throws JSONException {

        Boolean uploaded = false;

        //fazer validações ao Json e se for invalido enviar msg para o interface a indicar

        Quiz quizRecord = new Quiz();
        if (jsonQuiz.get("quiz_name") instanceof String){
            quizRecord.setFieldName(jsonQuiz.getString("quiz_name"));
        }
        if (jsonQuiz.get("quiz_reference") instanceof String){
            quizRecord.setFieldReference(jsonQuiz.getString("quiz_reference"));
        }
        if (jsonQuiz.get("quiz_description") instanceof String){
            quizRecord.setFieldDescription(jsonQuiz.getString("quiz_description"));
        }
        if (jsonQuiz.getString("quiz_url") instanceof String){
            quizRecord.setFieldUrl(jsonQuiz.getString("quiz_url"));
        }
        if (jsonQuiz.getBoolean("quiz_questionsrandom")){
            quizRecord.setFieldQuestionsrandom(1);
        }else{
            quizRecord.setFieldQuestionsrandom(0);
        }
        if (jsonQuiz.get("quiz_questionsnumber") instanceof  Integer){
            quizRecord.setFieldQuestionsnumber(jsonQuiz.getInt("quiz_questionsnumber"));
        }
        if (jsonQuiz.getBoolean("quiz_considertime")){
            quizRecord.setFieldConsidertime(1);
        }else{
            quizRecord.setFieldConsidertime(0);
        }

        Long insertedQuizId = this.insertTableRecord("Quiz", quizRecord.getContentValues());

        if(insertedQuizId != -1){

            if (jsonQuiz.get("questions") instanceof JSONArray){
                JSONArray questions = jsonQuiz.getJSONArray("questions");

                for (int i = 0; i < questions.length(); i++){

                    JSONObject oneJsonQuestion = questions.getJSONObject(i);
                    Question oneQuestionRecord = new Question();

                    oneQuestionRecord.setFieldQuizid(insertedQuizId.intValue());
                    if (oneJsonQuestion.get("question_text") instanceof String){
                        oneQuestionRecord.setFieldText(oneJsonQuestion.getString("question_text"));
                    }
                    if (oneJsonQuestion.get("question_url") instanceof String){
                        oneQuestionRecord.setFieldUrl(oneJsonQuestion.getString("question_url"));
                    }
                    if (oneJsonQuestion.get("question_type") instanceof String){
                        oneQuestionRecord.setFieldType(oneJsonQuestion.getString("question_type"));
                    }
                    if (oneJsonQuestion.get("question_order") instanceof  Integer){
                        oneQuestionRecord.setFieldOrder(oneJsonQuestion.getInt("question_order"));
                    }
                    if (oneJsonQuestion.getBoolean("question_answerrandom")){
                        oneQuestionRecord.setFieldAnswerrandom(1);
                    }else{
                        oneQuestionRecord.setFieldAnswerrandom(0);
                    }
                    if (oneJsonQuestion.getBoolean("question_answercorrect")){
                        oneQuestionRecord.setFieldAnswercorrect(1);
                    }else{
                        oneQuestionRecord.setFieldAnswercorrect(0);
                    }
                    if (oneJsonQuestion.get("question_minpoints") instanceof  Integer){
                        oneQuestionRecord.setFieldMinpoints(oneJsonQuestion.getInt("question_minpoints"));
                    }
                    if (oneJsonQuestion.get("question_timelimit") instanceof  Integer){
                        oneQuestionRecord.setFieldTimelimit(oneJsonQuestion.getInt("question_timelimit"));
                    }

                    Long insertedQuestionId = this.insertTableRecord("Question", oneQuestionRecord.getContentValues());

                    if(insertedQuestionId != -1){

                        if (oneJsonQuestion.get("answers") instanceof JSONArray) {

                            JSONArray answers = oneJsonQuestion.getJSONArray("answers");

                            for (int j = 0; j < questions.length(); j++) {

                                JSONObject oneJsonAnswer = answers.getJSONObject(j);
                                Answer oneAnswerRecord = new Answer();

                                oneAnswerRecord.setFieldQuestionid(insertedQuestionId.intValue());
                                if (oneJsonAnswer.get("answer_text") instanceof String){
                                    oneAnswerRecord.setFieldText(oneJsonAnswer.getString("answer_text"));
                                }
                                if (oneJsonAnswer.get("answer_url") instanceof String){
                                    oneAnswerRecord.setFieldText(oneJsonAnswer.getString("answer_url"));
                                }
                                if (oneJsonAnswer.get("answer_points") instanceof  Integer){
                                    oneAnswerRecord.setFieldPoints(oneJsonAnswer.getInt("answer_points"));
                                }
                                if (oneJsonAnswer.get("answer_order") instanceof  Integer){
                                    oneAnswerRecord.setFieldOrder(oneJsonAnswer.getInt("answer_order"));
                                }
                                if (oneJsonAnswer.getBoolean("answer_correct")){
                                    oneAnswerRecord.setFieldAnswercorrect(1);
                                }else{
                                    oneAnswerRecord.setFieldAnswercorrect(0);
                                }

                                Long insertedAnswerId = this.insertTableRecord("Answer", oneAnswerRecord.getContentValues());

                                if (insertedAnswerId != -1 ){
                                    continue;
                                }

                            }

                        }

                    }

                }

            }
            uploaded = true;
        }

        return uploaded;

    }


}