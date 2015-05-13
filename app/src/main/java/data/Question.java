package data;

import android.content.Context;

/**
 * Created by johnny on 02-05-2015.
 */
public class Question {

    private Integer question_id = null;
    private Integer questionquiz_id = null;
    private String question_text = null;
    private String question_url = null;
    private String question_type = null;
    private Integer question_order = null;
    private Integer question_answerrandom = null;
    private Integer question_minpoints = null;
    private Integer question_timelimit = null;
    private Integer question_answercorrect = null;

    public Question(){
    }

    public Question(Integer question_id, Integer questionquiz_id, String question_text, String question_url, String question_type, Integer question_order, Integer question_answerrandom, Integer question_minpoints, Integer question_timelimit, Integer question_answercorrect){
        this.setQuestion_id(question_id);
        this.setQuestionQuiz_id(questionquiz_id);
        this.setQuestion_text(question_text);
        this.setQuestion_url(question_url);
        this.setQuestion_type(question_type);
        this.setQuestion_order(question_order);
        this.setQuestion_answerrandom(question_answerrandom);
        this.setQuestion_answercorrect(question_answercorrect);
        this.setQuestion_minpoints(question_minpoints);
        this.setQuestion_timelimit(question_timelimit;

    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public void setQuestionQuiz_id(Integer questionquiz_id) {
        this.questionquiz_id = questionquiz_id;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public void setQuestion_url(String question_url) {
        this.question_url = question_url;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public void setQuestion_order(Integer question_order) {
        this.question_order = question_order;
    }

    public void setQuestion_answerrandom(Integer question_answerrandom) {
        this.question_answerrandom = question_answerrandom;
    }

    public void setQuestion_answercorrect(Integer question_answercorrect) {
        this.question_answercorrect = question_answercorrect;
    }

    public void setQuestion_minpoints(Integer question_minpoints) {
        this.question_minpoints = question_minpoints;
    }

    public void setQuestion_timelimit(Integer question_timelimit) {
        this.question_id = question_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public Integer getQuestionquiz_id() {
        return questionquiz_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public String getQuestion_url() {
        return question_url;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public Integer getQuestion_order() {
        return question_order;
    }

    public Integer getQuestion_answerrandom() {
        return question_answerrandom;
    }

    public Integer getQuestion_answercorrect() {
        return question_answercorrect;
    }

    public Integer getQuestion_minpoints() {
        return question_minpoints;
    }

    public Integer getQuestion_timelimit() {
        return question_timelimit;
    }

}
