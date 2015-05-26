package com.daam.orquiz.data;

import android.content.ContentValues;

/**
 * Created by johnny on 02-05-2015.
 */
public class ParticipationQuestion {

    private Integer participation_id = null;
    private Integer question_id = null;
    private Integer participationquestion_points = null;
    private String participationquestion_answersjson = null;
    private Long participationquestion_serverstart = null;
    private Long participationquestion_serverend = null;
    private Long participationquestion_clientstart = null;
    private Long participationquestion_clientend = null;
    private Integer participationquestion_answertime = null;
    private Integer participationquestion_order = null;
    private ContentValues participationContentValues = new ContentValues();

    boolean selected = false;

    public ParticipationQuestion(){
    }

    public ParticipationQuestion(Integer participation_id, Integer question_id, Integer participationquestion_points, String participationquestion_answersjson, Long participationquestion_serverstart, Long participationquestion_serverend, Long participationquestion_clientstart, Long participationquestion_clientend, Integer participationquestion_answertime, Integer participationquestion_order){
        this.setFieldParticipationid(this.participation_id);
        this.setFieldQuestionid(this.question_id);
        this.setFieldPoints(this.participationquestion_points);
        this.setFieldAnswersjson(this.participationquestion_answersjson);
        this.setFieldServerstart(this.participationquestion_serverstart);
        this.setFieldServerend(this.participationquestion_serverend);
        this.setFieldClientstart(this.participationquestion_clientstart);
        this.setFieldClientend(this.participationquestion_clientend);
        this.setFieldAnswertime(this.participationquestion_answertime);
        this.setFieldOrder(this.participationquestion_order);
    }

    public void setFieldParticipationid(Integer participation_id) {
        this.participation_id = participation_id;
    }

    public void setFieldQuestionid(Integer question_id) {
        this.question_id = question_id;
    }

    public void setFieldPoints(Integer participationquestion_points) {
        this.participationquestion_points = participationquestion_points;
        this.participationContentValues.put("participationquestion_points", participationquestion_points);
    }

    public void setFieldAnswersjson(String participationquestion_answersjson) {
        this.participationquestion_answersjson = participationquestion_answersjson;
        this.participationContentValues.put("participationquestion_answersjson", participationquestion_answersjson);
    }

    public void setFieldServerstart(Long participationquestion_serverstart) {
        this.participationquestion_serverstart = participationquestion_serverstart;
        this.participationContentValues.put("participationquestion_serverstart", participationquestion_serverstart);
    }

    public void setFieldServerend(Long participationquestion_serverend) {
        this.participationquestion_serverend = participationquestion_serverend;
        this.participationContentValues.put("participationquestion_serverend", participationquestion_serverend);
    }

    public void setFieldClientstart(Long participationquestion_clientstart) {
        this.participationquestion_clientstart = participationquestion_clientstart;
        this.participationContentValues.put("participationquestion_clientstart", participationquestion_clientstart);
    }

    public void setFieldClientend(Long participationquestion_clientend) {
        this.participationquestion_clientend = participationquestion_clientend;
        this.participationContentValues.put("participationquestion_clientend", participationquestion_clientend);
    }

    public void setFieldAnswertime(Integer participationquestion_answertime) {
        this.participationquestion_answertime = participationquestion_answertime;
        this.participationContentValues.put("participationquestion_answertime", participationquestion_answertime);
    }

    public void setFieldOrder(Integer participationquestion_order) {
        this.participationquestion_answertime = participationquestion_order;
        this.participationContentValues.put("participationquestion_order", participationquestion_order);
    }

    public Integer getFieldParticipationid() {
        return participation_id;
    }

    public Integer getFieldQuestionid() {
        return question_id;
    }

    public Integer getFieldPoints() {
        return participationquestion_points;
    }

    public String getFieldAnswersjson() {
        return participationquestion_answersjson;
    }

    public Long getFieldServerstart() {
        return participationquestion_serverstart;
    }

    public Long getFieldServerend() {
        return participationquestion_serverend;
    }

    public Long getFieldClientstart() {
        return participationquestion_clientstart;
    }

    public Long getFieldClientend() {
        return participationquestion_clientend;
    }

    public Integer getFieldAnswertime() {
        return participationquestion_answertime;
    }

    public Integer getFieldOrder() {
        return participationquestion_order;
    }

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
