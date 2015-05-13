package data;

/**
 * Created by johnny on 02-05-2015.
 */
public class ParticipationQuestion {

    private Integer participation_id = null;
    private Integer question_id = null;
    private Integer participationquestion_points = null;
    private String participationquestion_answersjson = null;
    private Integer participationquestion_serverstart = null;
    private Integer participationquestion_serverend = null;
    private Integer participationquestion_clientstart = null;
    private Integer participationquestion_clientend = null;
    private Integer participationquestion_answertime = null;

    boolean selected = false;

    public ParticipationQuestion(){
    }

    public ParticipationQuestion(Integer participation_id, Integer question_id, Integer participationquestion_points, String participationquestion_answersjson, Integer participationquestion_serverstart, Integer participationquestion_serverend, Integer participationquestion_clientstart, Integer participationquestion_clientend, Integer participationquestion_answertime){
        this.setParticipation_id(this.participation_id);
        this.setQuestion_id(this.question_id);
        this.setParticipationQuestion_points(this.participationquestion_points);
        this.setParticipationQuestion_answersjson(this.participationquestion_answersjson);
        this.setParticipationQuestion_serverstart(this.participationquestion_serverstart);
        this.setParticipationQuestion_serverend(this.participationquestion_serverend);
        this.setParticipationQuestion_clientstart(this.participationquestion_clientstart);
        this.setParticipationQuestion_clientend(this.participationquestion_clientend);
        this.setParticipationQuestion_answertime(this.participationquestion_answertime);
    }

    public void setParticipation_id(Integer participation_id) {
        this.participation_id = participation_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public void setParticipationQuestion_points(Integer participationquestion_points) {
        this.participationquestion_points = participationquestion_points;
    }

    public void setParticipationQuestion_answersjson(String participationQuestion_answersjson) {
        this.participation_id = participation_id;
    }

    public void setParticipationQuestion_serverstart(Integer participationquestion_serverstart) {
        this.participationquestion_serverstart = participationquestion_serverstart;
    }

    public void setParticipationQuestion_serverend(Integer participationquestion_serverend) {
        this.participationquestion_serverend = participationquestion_serverend;
    }

    public void setParticipationQuestion_clientstart(Integer participationquestion_clientstart) {
        this.participationquestion_clientstart = participationquestion_clientstart;
    }

    public void setParticipationQuestion_clientend(Integer participationquestion_clientend) {
        this.participationquestion_clientend = participationquestion_clientend;
    }

    public void setParticipationQuestion_answertime(Integer question_answertime) {
        this.participationquestion_answertime = question_answertime;
    }

    public Integer getParticipation_id() {
        return participation_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public Integer getParticipationQuestion_points() {
        return participationquestion_points;
    }

    public String getParticipationQuestion_answersjson() {
        return participationquestion_answersjson;
    }

    public Integer getParticipationQuestion_serverstart() {
        return participationquestion_serverstart;
    }

    public Integer getParticipationQuestion_serverend() {
        return participationquestion_serverend;
    }

    public Integer getParticipationQuestion_clientstart() {
        return participationquestion_clientstart;
    }

    public Integer getParticipationQuestion_clientend() {
        return participationquestion_clientend;
    }

    public Integer getParticipationQuestion_answertime() {
        return participationquestion_answertime;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
