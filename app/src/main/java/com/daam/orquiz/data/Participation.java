package com.daam.orquiz.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.daam.orquiz.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by johnny on 02-05-2015.
 */
public class Participation {

    private Integer participation_id = null;
    private Integer participant_id = null;
    private Long participation_start = null;
    private Long participation_end = null;
    private Integer participation_totaltime = null;
    private Integer participation_points = null;
    private String participation_ranking = null;
    private String participation_status = null;
    private ContentValues participationContentValues = new ContentValues();
    boolean selected = false;
    boolean empty = false;

    public Participation(){
    }

    public Participation(Integer participant_id, Long participation_start, Long participation_end, Integer participation_totaltime, Integer participation_points, String participation_ranking, String participation_status){
        this.setFieldId(participation_id);
        this.setFieldParticipantid(participant_id);
        this.setFieldStart(participation_start);
        this.setFieldEnd(participation_end);
        this.setFieldTotaltime(participation_totaltime);
        this.setFieldPoints(participation_points);
        this.setFieldRanking(participation_ranking);
        this.setFieldStatus(participation_status);
    }

    public void setFieldId(Integer participation_id) {
        this.participation_id = participation_id;
    }

    public void setFieldParticipantid(Integer participant_id) {
        this.participant_id = participant_id;
        this.participationContentValues.put("participant_id", participant_id);
    }

    public void setFieldStart(Long participation_start) {
        this.participation_start = participation_start;
        this.participationContentValues.put("participation_start", participation_start);
    }

    public void setFieldEnd(Long participation_end) {
        this.participation_end = participation_end;
        this.participationContentValues.put("participation_end", participation_end);
    }

    public void setFieldTotaltime(Integer participation_totaltime) {
        this.participation_totaltime = participation_totaltime;
        this.participationContentValues.put("participation_totaltime", participation_totaltime);
    }

    public void setFieldPoints(Integer participation_points) {
        this.participation_points = participation_points;
        this.participationContentValues.put("participation_points", participation_points);
    }

    public void setFieldRanking(String participation_ranking) {
        this.participation_ranking = participation_ranking;
        this.participationContentValues.put("participation_ranking", participation_ranking);
    }

    public void setFieldStatus(String participation_status) {
        this.participation_status = participation_status;
        this.participationContentValues.put("participation_status", participation_status);
    }

    public Integer getFieldId() {
        return participation_id;
    }

    public Integer getFieldParticipantid() {
        return participant_id;
    }

    public Long getFieldStart() {
        return participation_start;
    }

    public Long getFieldEnd() {
        return participation_end;
    }

    public Integer getFieldTotaltime() {
        return participation_totaltime;
    }

    public Integer getFieldPoints() {
        return participation_points;
    }

    public String getFieldRanking() {
        return participation_ranking;
    }

    public String getFieldStatus() {
        return participation_status;
    }

    public ContentValues getContentValues() {
        return participationContentValues;
    }

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
