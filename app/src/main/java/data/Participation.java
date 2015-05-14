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

    public Participation(){
    }

    public void setParticipationid(Integer participation_id) {
        this.participant_id = participation_id;
    }

    public void setParticipantid(Integer participant_id) {
        this.participant_id = participant_id;
    }

    public void setParticipationstart(Integer participation_start) {
        this.participation_start = participation_start;
    }

    public void setParticipationend(Integer participation_end) {
        this.participation_end = participation_end;
    }

    public void setParticipationtotaltime(Integer participation_totaltime) {
        this.participation_totaltime = participation_totaltime;
    }

    public void setParticipationpoints(Integer participation_points) {
        this.participation_points = participation_points;
    }

    public void setParticipationranking(String participation_ranking) {
        this.participation_ranking = participation_ranking;
    }

    public Integer getAnswerid() {
        return answer_id;
    }

    public String getAnswertext() {
        return answer_text;
    }

    public String getAnswerurl() {
        return answer_url;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
