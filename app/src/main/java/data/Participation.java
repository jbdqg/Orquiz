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

    public void setParticipation_id(Integer participation_id) {
        this.participant_id = participation_id;
    }

    public void setParticipant_id(Integer participant_id) {
        this.participant_id = participant_id;
    }

    public void setParticipation_start(Integer participation_start) {
        this.participation_start = participation_start;
    }

    public void setParticipation_end(Integer participation_end) {
        this.participation_end = participation_end;
    }

    public void setParticipation_totaltime(Integer participation_totaltime) {
        this.participation_totaltime = participation_totaltime;
    }

    public void setParticipation_points(Integer participation_points) {
        this.participation_points = participation_points;
    }

    public void setParticipation_ranking(String participation_ranking) {
        this.participation_ranking = participation_ranking;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public String getAnswer_url() {
        return answer_url;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
