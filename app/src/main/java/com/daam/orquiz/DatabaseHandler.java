package com.daam.orquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daam.orquiz.business.DownloadImagesTask;
import com.daam.orquiz.business.DownloadOneImageTask;
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
                + KEY_QUIZ_ID + " INTEGER,"
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
                + ") VALUES( 1, '" + Environment.getExternalStorageDirectory() + "/orquiz/quizes/quizOrange', 'Clockwork Orange', 'A Clockwork Orange takes place in a futuristic city governed by a repressive, totalitarian super-State. In this society, ordinary citizens have fallen into a passive stupor of complacency, blind to the insidious growth of a rampant, violent youth culture. The protagonist of the story is Alex, a fifteen-year-old boy who narrates in a teenage slang called nadsat, which incorporates elements of Russian and Cockney English. Alex leads a small gang of teenage criminals—Dim, Pete, and Georgie—through the streets, robbing and beating men and raping women. Alex and his friends spend the rest of their time at the Korova Milkbar, an establishment that serves milk laced with drugs, and a bar called the Duke of New York.', 'q1.jpg', 1, 5, 0"
                + ")";
        db.execSQL(sqlInsertQU1);

        String sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 1, 1, 'Who wrote the novel upon which the film A Clockwork Orange is based?', 'q1q1.jpg', 'multiplechoice', 1, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        String sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 1, 1, 'Stanley Kubrick', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 2, 1, 'Ken Kesey', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 3, 1, 'Anthony Burgess', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 4, 1, 'Malcolm McDowell', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END


        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 2, 1, 'Which members of society speak Nadsat, the slang Alex speaks?', 'q1default.jpg', 'multiplechoice', 2, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 5, 2, 'Criminals', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 6, 2, 'Police officers', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 7, 2, 'Homeless people', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 8, 2, 'Youth', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 3, 1, 'From which foreign language does Nadsat take the most words?', 'q1q3.jpg', 'multiplechoice', 3, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 9, 3, 'German', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 10, 3, 'Russian', null, 1, 2, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 11, 3, 'French', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 12, 3, 'Chinese', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 4, 1, 'What drink do Alex and his gang consume on the night the film opens?', 'q1q4.jpg', 'multiplechoice', 4, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 13, 4, 'Vodka', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 14, 4, 'Whiskey', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 15, 4, 'Milk', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 16, 4, 'Water', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 5, 1, 'In the scene in which Alex beats Mr. Alexander and rapes Mrs. Alexander, what else does he do?', 'q1q5.jpg', 'multiplechoice', 5, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 17, 5, 'Shouts orders', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 18, 5, 'Blasts his stereo', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 19, 5, 'Sings and dances', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 20, 5, 'Looks in the mirror', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 6, 1, 'After his first night of violence, Alex returns home. Where does he live?', 'q1default.jpg', 'multiplechoice', 6, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 21, 6, 'In a basement', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 22, 6, 'On the street', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 23, 6, 'In boarding school', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 24, 6, 'In his parents’ apartment', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 7, 1, 'Who first pays Alex a visit the next morning?', 'q1q7.jpg', 'multiplechoice', 7, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 25, 7, 'His teacher', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 26, 7, 'His probation officer', null, 1, 2, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 27, 7, 'His girlfriend', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 28, 7, 'The police', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 8, 1, 'Who is Alex’s favorite composer?', 'q1q8.jpg', 'multiplechoice', 8, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 29, 8, 'Mick Jagger', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 30, 8, 'John Lennon', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 31, 8, 'Wagner', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 32, 8, 'Beethoven', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 9, 1, 'What does Alex imagine when he hears music?', 'q1q9.jpg', 'multiplechoice', 9, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 33, 9, 'The countryside', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 34, 9, 'God', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 35, 9, 'Violence', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 36, 9, 'His childhood', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 10, 1, 'What crime does Alex get sent to prison for?', 'q1default.jpg', 'multiplechoice', 10, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 37, 10, 'Theft', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 38, 10, 'Rape', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 39, 10, 'Murder', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 40, 10, 'Public indecency', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 11, 1, 'What kind of animals does the victim of this crime keep as pets?', 'q1q11.png', 'multiplechoice', 11, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 41, 11, 'Dogs', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 42, 11, 'Cats', null, 1, 2, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 43, 11, 'Birds', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 44, 11, 'Snakes', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 12, 1, 'What object does Alex’s victim use to fight him?', 'q1q12.jpg', 'multiplechoice', 12, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 45, 12, 'A knife', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 46, 12, 'A statue of a cat', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 47, 12, 'A bust of Beethoven', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 48, 12, 'A statue of a penis', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 13, 1, 'What do the police do with Alex his first night in custody?', 'q1default.jpg', 'multiplechoice', 13, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 49, 13, 'Leave him alone in his cell', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 50, 13, 'Beat him up', null, 1, 2, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 51, 13, 'Make him stand in a lineup', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 52, 13, 'Strip-search him', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 14, 1, 'How much of his prison sentence does Alex serve?', 'q1default.jpg', 'multiplechoice', 14, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 53, 14, 'Fourteen years', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 54, 14, 'Fourteen months', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 55, 14, 'Two years', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 56, 14, 'Two months', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 15, 1, 'Who takes Alex under his wing while he is in prison?', 'q1q15.jpg', 'multiplechoice', 15, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 57, 15, 'A prison guard', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 58, 15, 'An older prisoner', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 59, 15, 'The prison chaplain', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 60, 15, 'The cook', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 16, 1, 'What book does Alex read while in prison?', 'q1q16.jpg', 'multiplechoice', 16, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 61, 16, 'A Clockwork Orange', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 62, 16, 'One Flew Over the Cuckoo’s Nest', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 63, 16, 'Dante’s Inferno', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 64, 16, 'The Bible', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 17, 1, 'During Alex’s treatment with Ludovico’s Technique, what is shown in the films he is made to watch?', 'q1q17.jpg', 'multiplechoice', 17, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 65, 17, 'Religious sermons', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 66, 17, 'Political speeches', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 67, 17, 'Rock and roll concerts', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 68, 17, 'Sex and violence', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 18, 1, 'How long does the treatment last?', 'q1default.jpg', 'multiplechoice', 18, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 69, 18, 'Two months', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 70, 18, 'Two years', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 71, 18, 'Two weeks', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 72, 18, 'Two days', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 19, 1, 'When Alex returns home, what surprise is waiting for him?', 'q1q19.jpg', 'multiplechoice', 19, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 73, 19, 'His parents have made him a welcome-home dinner', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 74, 19, 'He has a new probation office', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 75, 19, 'A lodger is living in his room', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 76, 19, 'His parents have moved', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 20, 1, 'Upon his release, Alex finds his friends working as what?', 'q1q20.jpg', 'multiplechoice', 20, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 77, 20, 'Factory hands', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 78, 20, 'Criminals', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 79, 20, 'Policemen', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 80, 20, 'Politicians', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 21, 1, 'The first night after his release, in whose house does Alex seek refuge?', 'q1default.jpg', 'multiplechoice', 21, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 81, 21, 'His parents’', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 82, 21, 'The prison chaplain’s', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 83, 21, 'Mr. Alexander’s', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 84, 21, 'Dim’s', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 22, 1, 'How does Alex escape?', 'q1default.jpg', 'multiplechoice', 22, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 85, 22, 'He runs away in the night', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 86, 22, 'He burns the house down', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 87, 22, 'He jumps out the window', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 88, 22, 'He calls the police', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 23, 1, 'What celebrity made famous the song “Singin’ in the Rain”?', 'q1q23.jpg', 'multiplechoice', 23, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 89, 23, 'Michael Jackson', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 90, 23, 'Humphrey Bogart', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 91, 23, 'Clark Gable', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 92, 23, 'Gene Kelly', null, 1, 4, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 24, 1, 'When the minister of the interior visits Alex in prison, what does he do for him?', 'q1q24.png', 'multiplechoice', 24, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 93, 24, 'Bathes him', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 94, 24, 'Sings to him', null, 0, 2, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 95, 24, 'Feeds him', null, 1, 3, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 96, 24, 'Reads him a letter', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

        sqlInsertQ1 = "INSERT INTO " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + ", " + KEY_QUIZ_ID +  ", " + FIELD_QUESTION_TEXT  + ", " + FIELD_QUESTION_URL + ", "
                + FIELD_QUESTION_TYPE + ", " + FIELD_QUESTION_ORDER + ", "
                + FIELD_QUESTION_ANSWERRANDOM + ", " + FIELD_QUESTION_MINPOINTS + ", "
                + FIELD_QUESTION_TIMELIMIT + ", " + FIELD_QUESTION_ANSWERCORRECT
                + ") VALUES( 25, 1, 'How is the end of the novel different from the end of the film?', 'q1q25.jpg', 'multiplechoice', 25, 1, 0, 10, 1"
                + ")";
        db.execSQL(sqlInsertQ1);

//ANSWERS-START

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 97, 25, 'In the novel, Alex goes back to prison', null, 0, 1, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 98, 25, 'In the novel, Alex loses interest in violence', null, 1, 2, 1"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 99, 25, 'In the novel, Alex becomes a politician', null, 0, 3, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

        sqlInsertQ1A1 = "INSERT INTO " + TABLE_ANSWER + "(" + KEY_ANSWER_ID + ", " + KEY_QUESTION_ID + ", " + FIELD_ANSWER_TEXT + ", " + FIELD_ANSWER_URL + ", "
                + FIELD_ANSWER_POINTS + ", " + FIELD_ANSWER_ORDER + ", " + FIELD_ANSWER_CORRECT
                + ") VALUES( 100, 25, 'In the novel, Alex goes back to school', null, 0, 4, 0"
                + ")";
        db.execSQL(sqlInsertQ1A1);

//ANSWERS-END

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

    public List<Quiz> getAllQuiz() {
        List<Quiz> listOfQuiz = new ArrayList<>();

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_QUIZ, null);
        if ( cursor.moveToFirst() ) {
            do {
                Quiz quiz = new Quiz();
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

                listOfQuiz.add(quiz);
            } while (cursor.moveToNext());
        }

        return listOfQuiz;
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
        int quiz_id = MainActivity.QUIZ_ID;

        Participation participation = new Participation();

        //obter a última participação sem data de fim para o participante recebido
        String selectQuery = "SELECT " + KEY_PARTICIPATION_ID + ", " + KEY_PARTICIPANT_ID + ", " + KEY_QUIZ_ID + ", " + FIELD_PARTICIPATION_START + ", " + FIELD_PARTICIPATION_STATUS + " FROM " + TABLE_PARTICIPATION + " WHERE " + KEY_PARTICIPANT_ID + " = " + participant_id + " AND " + FIELD_PARTICIPATION_END + " IS NULL AND " + FIELD_PARTICIPATION_STATUS + " = \"started\" ORDER BY " + KEY_PARTICIPATION_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                participation.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    participation.setFieldParticipantid(Integer.parseInt(cursor.getString(1)));
                }
                if(!cursor.isNull(2)){
                    participation.setFieldQuizid(Integer.parseInt(cursor.getString(2)));
                }
                if(!cursor.isNull(3)){
                    participation.setFieldStart(Long.parseLong(cursor.getString(3)));
                }
                if(!cursor.isNull(4)){
                    participation.setFieldStatus(cursor.getString(4));
                }
            } while (cursor.moveToNext());
        }

        if (participation.getFieldId() == null){

            participation.setFieldParticipantid(participant_id);
            participation.setFieldQuizid(quiz_id);
            participation.setFieldStart(System.currentTimeMillis());
            participation.setFieldStatus("started");
            Long insertedParticipationID = this.insertTableRecord("Participation", participation.getContentValues());
            //todas as keys deviam ser Long por causa da inserção
            Integer i = (int) (long) insertedParticipationID;
            participation.setFieldId(i);

        }

        return participation;
    }

    public Participation getParticipation(int quiz_id, String participation_type) {

        int participant_id = MainActivity.PARTICIPANT_ID;

        Participation participation = new Participation();

        String selectQuery = "SELECT " + KEY_PARTICIPATION_ID + ", "
                                       + KEY_PARTICIPANT_ID + ", "
                                       + KEY_QUIZ_ID + ", "
                                       + FIELD_PARTICIPATION_START + ", "
                                       + FIELD_PARTICIPATION_END + ", "
                                       + FIELD_PARTICIPATION_TOTALTIME + ", "
                                       + FIELD_PARTICIPATION_POINTS + ", "
                                       + FIELD_PARTICIPATION_RANKING
                                + " FROM " + TABLE_PARTICIPATION
                                + " WHERE " + KEY_QUIZ_ID + " = " + quiz_id
                                + " AND " + KEY_PARTICIPANT_ID + " = " + participant_id
                                + " AND " + FIELD_PARTICIPATION_STATUS + " = \"completed\" ";

        if (participation_type == "last"){
            selectQuery += " ORDER BY " + KEY_PARTICIPATION_ID + " DESC LIMIT 1";
        }else if (participation_type == "best"){
            selectQuery += " ORDER BY " + FIELD_PARTICIPATION_POINTS + " DESC LIMIT 1";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                participation.setFieldId(Integer.parseInt(cursor.getString(0)));
                if(!cursor.isNull(1)){
                    participation.setFieldParticipantid(Integer.parseInt(cursor.getString(1)));
                }
                if(!cursor.isNull(2)){
                    participation.setFieldQuizid(Integer.parseInt(cursor.getString(2)));
                }
                if(!cursor.isNull(3)){
                    participation.setFieldStart(Long.parseLong(cursor.getString(3)));
                }
                if(!cursor.isNull(4)){
                    participation.setFieldEnd(Long.parseLong(cursor.getString(4)));
                }
                if(!cursor.isNull(5)){
                    participation.setFieldTotaltime(Integer.parseInt(cursor.getString(5)));
                }
                if(!cursor.isNull(6)){
                    participation.setFieldPoints(Integer.parseInt(cursor.getString(6)));
                }
                if(!cursor.isNull(7)){
                    participation.setFieldRanking(cursor.getString(7));
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
                //remove - faz o dump do cursor
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
        if (jsonQuiz.has("quiz_name") && jsonQuiz.get("quiz_name") instanceof String){
            quizRecord.setFieldName(jsonQuiz.getString("quiz_name"));
        }
        if (jsonQuiz.has("quiz_reference") && jsonQuiz.get("quiz_reference") instanceof String){
            quizRecord.setFieldReference(jsonQuiz.getString("quiz_reference"));
        }
        if (jsonQuiz.has("quiz_description") && jsonQuiz.get("quiz_description") instanceof String){
            quizRecord.setFieldDescription(jsonQuiz.getString("quiz_description"));
        }
        if (jsonQuiz.has("quiz_url") && jsonQuiz.getString("quiz_url") instanceof String){
            quizRecord.setFieldUrl(jsonQuiz.getString("quiz_url"));}
        if (jsonQuiz.has("quiz_questionsrandom") && jsonQuiz.get("quiz_questionsrandom") instanceof Boolean && jsonQuiz.getBoolean("quiz_questionsrandom")){
            quizRecord.setFieldQuestionsrandom(1);
        }else{
            quizRecord.setFieldQuestionsrandom(0);
        }
        if (jsonQuiz.has("quiz_questionsnumber") && jsonQuiz.get("quiz_questionsnumber") instanceof  Integer){
            quizRecord.setFieldQuestionsnumber(jsonQuiz.getInt("quiz_questionsnumber"));
        }
        if (jsonQuiz.has("quiz_considertime") && jsonQuiz.get("quiz_considertime") instanceof Boolean && jsonQuiz.getBoolean("quiz_considertime")){
            quizRecord.setFieldConsidertime(1);
        }else{
            quizRecord.setFieldConsidertime(0);
        }

        Long insertedQuizId = this.insertTableRecord("Quiz", quizRecord.getContentValues());

        if (insertedQuizId != -1 && jsonQuiz.getString("quiz_url") instanceof String){
            String[] urlInfo = new String[5];
            urlInfo[0] = "Quiz";
            urlInfo[1] = "quiz_id";
            urlInfo[2] = insertedQuizId.toString();
            urlInfo[3] = "quiz_url";
            urlInfo[4] = jsonQuiz.getString("quiz_url");

            new DownloadOneImageTask().execute(urlInfo);
        }

        if(insertedQuizId != -1){

            if (jsonQuiz.get("questions") instanceof JSONArray){
                JSONArray questions = jsonQuiz.getJSONArray("questions");

                for (int i = 0; i < questions.length(); i++){

                    JSONObject oneJsonQuestion = questions.getJSONObject(i);
                    Question oneQuestionRecord = new Question();

                    oneQuestionRecord.setFieldQuizid(insertedQuizId.intValue());
                    if (oneJsonQuestion.has("question_text") && oneJsonQuestion.get("question_text") instanceof String){
                        oneQuestionRecord.setFieldText(oneJsonQuestion.getString("question_text"));
                    }
                    if (oneJsonQuestion.has("question_url") && oneJsonQuestion.get("question_url") instanceof String){
                        oneQuestionRecord.setFieldUrl(oneJsonQuestion.getString("question_url"));
                    }
                    if (oneJsonQuestion.has("question_type") && oneJsonQuestion.get("question_type") instanceof String){
                        oneQuestionRecord.setFieldType(oneJsonQuestion.getString("question_type"));
                    }
                    if (oneJsonQuestion.has("question_order") && oneJsonQuestion.get("question_order") instanceof  Integer){
                        oneQuestionRecord.setFieldOrder(oneJsonQuestion.getInt("question_order"));
                    }
                    if (oneJsonQuestion.has("question_answerrandom") && oneJsonQuestion.get("question_answerrandom") instanceof Boolean && oneJsonQuestion.getBoolean("question_answerrandom")){
                        oneQuestionRecord.setFieldAnswerrandom(1);
                    }else{
                        oneQuestionRecord.setFieldAnswerrandom(0);
                    }
                    if (oneJsonQuestion.has("question_answercorrect") && oneJsonQuestion.get("question_answercorrect") instanceof Boolean && oneJsonQuestion.getBoolean("question_answercorrect")){
                        oneQuestionRecord.setFieldAnswercorrect(1);
                    }else{
                        oneQuestionRecord.setFieldAnswercorrect(0);
                    }
                    if (oneJsonQuestion.has("question_minpoints") && oneJsonQuestion.get("question_minpoints") instanceof  Integer){
                        oneQuestionRecord.setFieldMinpoints(oneJsonQuestion.getInt("question_minpoints"));
                    }
                    if (oneJsonQuestion.has("question_timelimit") && oneJsonQuestion.get("question_timelimit") instanceof  Integer){
                        oneQuestionRecord.setFieldTimelimit(oneJsonQuestion.getInt("question_timelimit"));
                    }

                    Long insertedQuestionId = this.insertTableRecord("Question", oneQuestionRecord.getContentValues());

                    if (insertedQuestionId != -1 && oneJsonQuestion.getString("question_url") instanceof String){
                        String[] urlInfo = new String[5];
                        urlInfo[0] = "Question";
                        urlInfo[1] = "question_id";
                        urlInfo[2] = insertedQuestionId.toString();
                        urlInfo[3] = "question_url";
                        urlInfo[4] = oneJsonQuestion.getString("question_url");

                        new DownloadOneImageTask().execute(urlInfo);
                    }

                    if(insertedQuestionId != -1){

                        if (oneJsonQuestion.get("answers") instanceof JSONArray) {

                            JSONArray answers = oneJsonQuestion.getJSONArray("answers");

                            for (int j = 0; j < answers.length(); j++) {

                                JSONObject oneJsonAnswer = answers.getJSONObject(j);
                                Answer oneAnswerRecord = new Answer();

                                oneAnswerRecord.setFieldQuestionid(insertedQuestionId.intValue());
                                if (oneJsonAnswer.has("answer_text") && oneJsonAnswer.get("answer_text") instanceof String){
                                    oneAnswerRecord.setFieldText(oneJsonAnswer.getString("answer_text"));
                                }
                                if (oneJsonAnswer.has("answer_url") && oneJsonAnswer.get("answer_url") instanceof String){
                                    oneAnswerRecord.setFieldText(oneJsonAnswer.getString("answer_url"));
                                }
                                if (oneJsonAnswer.has("answer_points") && oneJsonAnswer.get("answer_points") instanceof  Integer){
                                    oneAnswerRecord.setFieldPoints(oneJsonAnswer.getInt("answer_points"));
                                }
                                if (oneJsonAnswer.has("answer_order") && oneJsonAnswer.get("answer_order") instanceof  Integer){
                                    oneAnswerRecord.setFieldOrder(oneJsonAnswer.getInt("answer_order"));
                                }
                                if (oneJsonAnswer.has("answer_correct") && oneJsonAnswer.get("answer_correct") instanceof Boolean && oneJsonAnswer.getBoolean("answer_correct")){
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
