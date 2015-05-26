package com.daam.orquiz.data;

import android.content.ContentValues;

/**
 * Created by johnny on 02-05-2015.
 */
public class Question {

    private Integer question_id = null;
    private Integer question_quizid = null;
    private String question_text = null;
    private String question_url = null;
    private String question_type = null;
    private Integer question_order = null;
    private Integer question_answerrandom = null;
    private Integer question_answercorrect = null;
    private Integer question_minpoints = null;
    private Integer question_timelimit = null;
    private ContentValues participationContentValues = new ContentValues();

    public Question(){
    }

    public Question(Integer question_id, Integer questionquiz_id, String question_text, String question_url, String question_type, Integer question_order, Integer question_answerrandom, Integer question_minpoints, Integer question_timelimit, Integer question_answercorrect){
        this.setFieldId(question_id);
        this.setFieldQuizid(questionquiz_id);
        this.setFieldText(question_text);
        this.setFieldUrl(question_url);
        this.setFieldType(question_type);
        this.setFieldOrder(question_order);
        this.setFieldAnswerrandom(question_answerrandom);
        this.setFieldAnswercorrect(question_answercorrect);
        this.setFieldMinpoints(question_minpoints);
        this.setFieldTimelimit(question_timelimit);

    }

    public void setFieldId(Integer question_id) {
        this.question_id = question_id;
    }

    public void setFieldQuizid(Integer question_quizid) {
        this.question_quizid = question_quizid;
        this.participationContentValues.put("question_quizid", question_quizid);

    }

    public void setFieldText(String question_text) {
        this.question_text = question_text;
        this.participationContentValues.put("question_text", question_text);
    }

    public void setFieldUrl(String question_url) {
        this.question_url = question_url;
        this.participationContentValues.put("question_url", question_url);

    }

    public void setFieldType(String question_type) {
        this.question_type = question_type;
        this.participationContentValues.put("question_type", question_type);

    }

    public void setFieldOrder(Integer question_order) {
        this.question_order = question_order;
        this.participationContentValues.put("question_order", question_order);

    }

    public void setFieldAnswerrandom(Integer question_answerrandom) {
        this.question_answerrandom = question_answerrandom;
        this.participationContentValues.put("question_answerrandom", question_answerrandom);

    }

    public void setFieldAnswercorrect(Integer question_answercorrect) {
        this.question_answercorrect = question_answercorrect;
        this.participationContentValues.put("this.question_answercorrect = question_answercorrect;", question_answercorrect);

    }

    public void setFieldMinpoints(Integer question_minpoints) {
        this.question_minpoints = question_minpoints;
        this.participationContentValues.put("question_minpoints", question_minpoints);

    }

    public void setFieldTimelimit(Integer question_timelimit) {
        this.question_timelimit = question_timelimit;
        this.participationContentValues.put("question_timelimit", question_timelimit);

    }

    public Integer getFieldId() {
        return question_id;
    }

    public Integer getFieldQuizid() {
        return question_quizid;
    }

    public String getFieldText() {
        return question_text;
    }

    public String getFieldUrl() {
        return question_url;
    }

    public String getFieldType() {
        return question_type;
    }

    public Integer getFieldOrder() {
        return question_order;
    }

    public Integer getFieldAnswerrandom() {
        return question_answerrandom;
    }

    public Integer getFieldAnswercorrect() {
        return question_answercorrect;
    }

    public Integer getFieldMinpoints() {
        return question_minpoints;
    }

    public Integer getFieldTimelimit() {
        return question_timelimit;
    }

}
