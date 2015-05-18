package data;

/**
 * Created by johnny on 02-05-2015.
 */
public class Participation {

    private Integer participation_id = null;
    private Integer participant_id = null;
    private Integer participation_start = null;
    private Integer participation_end = null;
    private Integer participation_totaltime = null;
    private Integer participation_points = null;
    private String participation_ranking = null;

    boolean selected = false;

    public Participation(Integer participation_id, Integer participant_id, Integer participation_start, Integer participation_end, Integer participation_totaltime, Integer participation_points, String participation_ranking){
        this.setFieldId(participation_id);
        this.setFieldParticipantid(participant_id);
        this.setFieldStart(participation_start);
        this.setFieldEnd(participation_end);
        this.setFieldTotaltime(participation_totaltime);
        this.setFieldPoints(participation_points);
        this.setFieldRanking(participation_ranking);
    }

    public void setFieldId(Integer participation_id) {
        this.participant_id = participation_id;
    }

    public void setFieldParticipantid(Integer participant_id) {
        this.participant_id = participant_id;
    }

    public void setFieldStart(Integer participation_start) {
        this.participation_start = participation_start;
    }

    public void setFieldEnd(Integer participation_end) {
        this.participation_end = participation_end;
    }

    public void setFieldTotaltime(Integer participation_totaltime) {
        this.participation_totaltime = participation_totaltime;
    }

    public void setFieldPoints(Integer participation_points) {
        this.participation_points = participation_points;
    }

    public void setFieldRanking(String participation_ranking) {
        this.participation_ranking = participation_ranking;
    }

    public Integer getFieldId() {
        return participation_id;
    }

    public Integer getFieldParticipantid() {
        return participant_id;
    }

    public Integer getFieldStart() {
        return participation_start;
    }

    public Integer getFieldEnd() {
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

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
