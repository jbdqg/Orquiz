package com.daam.orquiz.data;

import android.content.ContentValues;

/**
 * Created by johnny on 02-05-2015.
 */
public class Quiz {

    private Integer quiz_id = null;
    private String quiz_reference = null;
    private String quiz_name = null;
    private String quiz_description = null;
    private String quiz_url = null;
    private Integer quiz_questionsrandom = null;
    private Integer quiz_questionsnumber = null;
    private Integer quiz_considertime= null;
    private ContentValues quizContentValues = new ContentValues();

    boolean selected = false;

    public Quiz(){
    }

    public Quiz(Integer quiz_id, String quiz_reference, String quiz_name, String quiz_description, String quiz_url, Integer quiz_questionsrandom, Integer quiz_questionsnumber, Integer quiz_considertime){
        this.setFieldId(quiz_id);
        this.setFieldReference(quiz_reference);
        this.setFieldName(quiz_name);
        this.setFieldDescription(quiz_description);
        this.setFieldUrl(quiz_url);
        this.setFieldQuestionsrandom(quiz_questionsrandom);
        this.setFieldQuestionsnumber(quiz_questionsnumber);
        this.setFieldConsidertime(quiz_considertime);
    }

    public void setFieldId(Integer quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setFieldReference(String quiz_reference) {
        this.quiz_reference = quiz_reference;
        this.quizContentValues.put("quiz_reference", quiz_reference);
    }

    public void setFieldName(String quiz_name) {
        this.quiz_name = quiz_name;
        this.quizContentValues.put("quiz_name", quiz_name);
    }

    public void setFieldDescription(String quiz_description) {
        this.quiz_description = quiz_description;
        this.quizContentValues.put("quiz_description", quiz_description);
    }

    public void setFieldUrl(String quiz_url) {
        this.quiz_url = quiz_url;
        this.quizContentValues.put("quiz_url", quiz_url);
    }

    public void setFieldQuestionsrandom(Integer quiz_questionsrandom) {
        this.quiz_questionsrandom = quiz_questionsrandom;
        this.quizContentValues.put("quiz_questionsrandom", quiz_questionsrandom);
    }

    public void setFieldQuestionsnumber(Integer quiz_questionsnumber) {
        this.quiz_questionsnumber = quiz_questionsnumber;
        this.quizContentValues.put("quiz_questionsnumber", quiz_questionsnumber);
    }

    public void setFieldConsidertime(Integer quiz_considertime) {
        this.quiz_considertime = quiz_considertime;
        this.quizContentValues.put("quiz_considertime", quiz_considertime);
    }

    public Integer getFieldId() {
        return quiz_id;
    }

    public String getFieldReference() {
        return quiz_reference;
    }

    public String getFieldName() {
        return quiz_name;
    }

    public String getFieldDescription() {
        return quiz_description;
    }

    public String getFieldUrl() {
        return quiz_url;
    }

    public Integer getFieldQuestionsrandom() {
        return quiz_questionsrandom;
    }

    public Integer getFieldQuestionsnumber() {
        return quiz_questionsnumber;
    }

    public Integer getFieldConsidertime() {
        return quiz_considertime;
    }

    public ContentValues getContentValues() {
        return quizContentValues;
    }

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
