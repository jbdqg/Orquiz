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
        this.setFieldParticipationid(this.participation_id);
        this.setFieldQuestionid(this.question_id);
        this.setFieldPoints(this.participationquestion_points);
        this.setFieldAnswersjson(this.participationquestion_answersjson);
        this.setFieldServerstart(this.participationquestion_serverstart);
        this.setFieldServerend(this.participationquestion_serverend);
        this.setFieldClientstart(this.participationquestion_clientstart);
        this.setFieldClientend(this.participationquestion_clientend);
        this.setFieldAnswertime(this.participationquestion_answertime);
    }

    public void setFieldParticipationid(Integer participation_id) {
        this.participation_id = participation_id;
    }

    public void setFieldQuestionid(Integer question_id) {
        this.question_id = question_id;
    }

    public void setFieldPoints(Integer participationquestion_points) {
        this.participationquestion_points = participationquestion_points;
    }

    public void setFieldAnswersjson(String participationQuestion_answersjson) {
        this.participation_id = participation_id;
    }

    public void setFieldServerstart(Integer participationquestion_serverstart) {
        this.participationquestion_serverstart = participationquestion_serverstart;
    }

    public void setFieldServerend(Integer participationquestion_serverend) {
        this.participationquestion_serverend = participationquestion_serverend;
    }

    public void setFieldClientstart(Integer participationquestion_clientstart) {
        this.participationquestion_clientstart = participationquestion_clientstart;
    }

    public void setFieldClientend(Integer participationquestion_clientend) {
        this.participationquestion_clientend = participationquestion_clientend;
    }

    public void setFieldAnswertime(Integer question_answertime) {
        this.participationquestion_answertime = question_answertime;
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

    public Integer getFieldServerstart() {
        return participationquestion_serverstart;
    }

    public Integer getFieldServerend() {
        return participationquestion_serverend;
    }

    public Integer getFieldClientstart() {
        return participationquestion_clientstart;
    }

    public Integer getFieldClientend() {
        return participationquestion_clientend;
    }

    public Integer getFieldAnswertime() {
        return participationquestion_answertime;
    }

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
