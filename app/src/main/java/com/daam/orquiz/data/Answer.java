package com.daam.orquiz.data;

import android.content.ContentValues;

/**
 * Created by johnny on 02-05-2015.
 */
public class Answer {

    private Integer answer_id = null;
    private Integer answerquestion_id = null;
    private String answer_text = null;
    private String answer_url = null;
    private Integer answer_points = null;
    private Integer answer_order = null;
    private Integer answer_correct = null;
    private ContentValues answerContentValues = new ContentValues();

    boolean selected = false;

    public Answer(){
    }

    public Answer(Integer answer_id, Integer answerquestion_id, String answer_text, String answer_url, Integer answer_points, Integer answer_order, Integer answer_correct){
        this.setFieldId(answer_id);
        this.setFieldQuestionid(answerquestion_id);
        this.setFieldText(answer_text);
        this.setFieldUrl(answer_url);
        this.setFieldPoints(answer_points);
        this.setFieldOrder(answer_order);
        this.setFieldAnswercorrect(answer_correct);
    }

    public void setFieldId(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public void setFieldQuestionid(Integer answerquestion_id) {
        this.answerquestion_id = answerquestion_id;
        this.answerContentValues.put("question_id", answerquestion_id);
    }

    public void setFieldText(String answer_text) {
        this.answer_text = answer_text;
        this.answerContentValues.put("answer_text", answer_text);
    }


    public void setFieldUrl(String answer_url) {
        this.answer_url = answer_url;
        this.answerContentValues.put("answer_url", answer_url);
    }

    public void setFieldPoints(Integer answer_points){
        this.answer_points = answer_points;
        this.answerContentValues.put("answer_points", answer_points);
    }

    public void setFieldOrder(Integer answer_order) {
        this.answer_order = answer_order;
        this.answerContentValues.put("answer_order", answer_order);
    }

    public void setFieldAnswercorrect(Integer answer_correct) {
        this.answer_correct = answer_correct;
        this.answerContentValues.put("answer_correct", answer_correct);
    }

    public Integer getFieldId() {
        return answer_id;
    }

    public Integer getFieldQuestionid() {
        return answerquestion_id;
    }

    public String getFieldText() {
        return answer_text;
    }

    public String getFieldUrl() {
        return answer_url;
    }

    public Integer getFieldPoints() {
        return answer_points;
    }

    public Integer getFieldOrder() {
        return answer_order;
    }

    public Integer getFieldAnswercorrect() {
        return answer_correct;
    }

    public ContentValues getContentValues() {
        return answerContentValues;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}