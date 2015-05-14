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
    }

    public Answer(Integer answer_id, Integer answerquestion_id, String answer_text, String answer_url, Integer answer_points, Integer answer_order, Integer answer_correct){
        this.setFieldAnswerid(answer_id);
        this.setFieldAnswerQuestionid(answerquestion_id);
        this.setFieldText(answer_text);
        this.setFieldUrl(answer_url);
        this.setFieldPoints(answer_points);
        this.setFieldOrder(answer_order);
        this.setFieldAnswercorrect(answer_correct);
    }

    public void setFieldAnswerid(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public void setFieldAnswerQuestionid(Integer answerquestion_id) {
        this.answerquestion_id = answerquestion_id;
    }

    public void setFieldAnswertext(String answer_text) {
        this.answer_text = answer_text;
    }

    public void setFieldAnswerurl(String answer_url) {
        this.answer_url = answer_url;
    }

    public void setFieldAnswerpoints(Integer answer_points){
        this.answer_points = answer_points;
    }

    public void setFieldAnswerorder(Integer answer_order) {
        this.answer_order = answer_order;
    }

    public void setFieldAnswercorrect(Integer answer_correct) {
        this.answer_correct = answer_correct;
    }

    public Integer getFieldAnswerid() {
        return answer_id;
    }

    public Integer getFieldAnswerQuestionid() {
        return answerquestion_id;
    }

    public String getFieldAnswertext() {
        return answer_text;
    }

    public String getFieldAnswerurl() {
        return answer_url;
    }

    public Integer getFieldAnswerpoints() {
        return answer_points;
    }

    public Integer getFieldAnswerorder() {
        return answer_order;
    }

    public Integer getFieldAnswercorrect() {
        return answer_correct;
    }

    public void setFieldSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}