package data;

import android.content.Context;

/**
 * Created by johnny on 02-05-2015.
 */
public class Question {

    private Integer question_id = null;
    private String question_text = null;
    private String question_url = null;

    public Question(){
        /*this.setQuestion_id(this.question_id);
        this.setQuestion_text(this.question_text);
        this.setQuestion_url(this.question_url);*/
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public void setQuestion_url(String question_url) {
        this.question_url = question_url;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public String getQuestion_url() {
        return question_url;
    }
}
