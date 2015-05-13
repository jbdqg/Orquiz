package data;

/**
 * Created by johnny on 02-05-2015.
 */
public class Quiz {

    private Integer quiz_id = null;
    private String quiz_reference = null;
    private String quiz_name = null;
    private String quiz_description = null;
    private String quiz_url = null;
    private Integer quiz_questionsnumber= null;
    private Integer quiz_considertime= null;

    boolean selected = false;

    public Quiz(){
    }

    public Quiz(Integer quiz_id, String quiz_reference, String quiz_name, String quiz_description, String quiz_url, Integer quiz_questionsnumber, Integer quiz_considertime){
        this.setQuiz_id(quiz_id);
        this.setQuiz_reference(quiz_reference);
        this.setQuiz_name(quiz_name);
        this.setQuiz_description(quiz_description);
        this.setQuiz_url(quiz_url);
        this.setQuiz_questionsnumber(quiz_questionsnumber);
        this.setQuiz_considertime(quiz_considertime);
    }

    public void setQuiz_id(Integer quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setQuiz_reference(String quiz_reference) {
        this.quiz_reference = quiz_reference;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public void setQuiz_description(String quiz_description) {
        this.quiz_description = quiz_description;
    }

    public void setQuiz_url(String quiz_url) {
        this.quiz_url = quiz_url;
    }

    public void setQuiz_questionsnumber(Integer quiz_questionsnumber) {
        this.quiz_questionsnumber = quiz_questionsnumber;
    }

    public void setQuiz_considertime(Integer quiz_considertime) {
        this.quiz_considertime = quiz_considertime;
    }

    public Integer getQuiz_id() {
        return quiz_id;
    }

    public String getQuiz_reference() {
        return quiz_reference;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public String getQuiz_description() {
        return quiz_description;
    }

    public String getQuiz_url() {
        return quiz_url;
    }

    public Integer getQuiz_questionsnumber() {
        return quiz_questionsnumber;
    }

    public Integer getQuiz_considertime() {
        return quiz_considertime;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
