package data;

/**
 * Created by johnny on 02-05-2015.
 */
public class Answer {

    private Integer answer_id = null;
    private Integer answerquestion_id = null;
    private String answer_text = null;
    private String answer_url = null;

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

    public void setAnswer_text(String question_text) {
        this.answer_text = answer_text;
    }

    public void setAnswer_url(String question_url) {
        this.answer_url = answer_url;
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
}
