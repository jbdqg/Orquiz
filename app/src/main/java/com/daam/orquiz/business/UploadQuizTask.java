package com.daam.orquiz.business;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.MyApplication;
import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Question;
import com.daam.orquiz.data.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by johnny on 30-05-2015.
 */
public class UploadQuizTask extends AsyncTask<JSONObject, Void, String> {

    JSONObject jsonContent = null;

    DatabaseHandler dbConnector = new DatabaseHandler(
            MyApplication.getAppContext());

    @Override
    protected String doInBackground(JSONObject... jsonobjects) {
        this.jsonContent = jsonobjects[0];

        String jsonQuizName = null;
        try {
            jsonQuizName = uploadQuiz(jsonContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonQuizName;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(MyApplication.getAppContext(), "The Quiz \"" + result + "\" was uploaded and its ready to be answered",
                Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String uploadQuiz(JSONObject jsonQuiz) throws JSONException {

        String quiz_name = null;

        //fazer validações ao Json e se for invalido enviar msg para o interface a indicar
        Quiz quizRecord = new Quiz();
        if (jsonQuiz.has("quiz_name") && jsonQuiz.get("quiz_name") instanceof String){
            quizRecord.setFieldName(jsonQuiz.getString("quiz_name"));
            quiz_name = jsonQuiz.getString("quiz_name");
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

        Long insertedQuizId = dbConnector.insertTableRecord("Quiz", quizRecord.getContentValues());

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

                    Long insertedQuestionId = dbConnector.insertTableRecord("Question", oneQuestionRecord.getContentValues());

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

                                Long insertedAnswerId = dbConnector.insertTableRecord("Answer", oneAnswerRecord.getContentValues());

                                if (insertedAnswerId != -1 ){
                                    continue;
                                }

                            }

                        }

                    }

                }
            }
        }

        return quiz_name;

    }
}