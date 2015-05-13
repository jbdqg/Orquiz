package data;

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

    boolean selected = false;

    public Answer(){
        /*this.setAnswer_id(this.answer_id);
        this.setAnswerQuestion_id(this.answerquestion_id);
        this.setAnswer_text(this.answer_text);
        this.setAnswer_url(this.answer_url);*/
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public void setAnswerQuestion_id(Integer answerquestion_id) {
        this.answerquestion_id = answerquestion_id;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public void setAnswer_url(String answer_url) {
        this.answer_url = answer_url;
    }

    public void setAnswer_points(Integer answer_points{
        this.answer_points = answer_points;
    }

    public void setAnswer_order(Integer answer_order) {
        this.answer_order = answer_order;
    }

    public void setAnswer_correct(Integer answer_correct) {
        this.answer_correct = answer_correct;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public Integer getAnswerQuestion_id() {
        return answerquestion_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public String getAnswer_url() {
        return answer_url;
    }

    public Integer getAnswer_points() {
        return answer_points;
    }

    public Integer getAnswer_order() {
        return answer_order;
    }

    public Integer getAnswer_correct() {
        return answer_correct;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}