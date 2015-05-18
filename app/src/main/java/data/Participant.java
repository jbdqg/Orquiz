package data;

/**
 * Created by johnny on 02-05-2015.
 */
public class Participant {

    private Integer participant_id = null;
    private String participant_name = null;
    private String participant_url = null;
    private String participant_facebookid = null;

    boolean selected = false;

    public Participant(){
    }

    public Participant(Integer participant_id, String participant_name, String participant_url, String participant_facebookid){
        this.setFieldId(participant_id);
        this.setFieldName(participant_name);
        this.setFieldUrl(participant_url);
        this.setFieldFacebookid(participant_facebookid);
    }

    public void setFieldId(Integer participant_id) {
        this.participant_id = participant_id;
    }

    public void setFieldName(String participant_name) {
        this.participant_name = participant_name;
    }

    public void setFieldUrl(String participant_url) {
        this.participant_url= participant_url;
    }

    public void setFieldFacebookid(String participant_facebookid) {
        this.participant_facebookid = participant_facebookid;
    }

    public Integer getFieldId() {
        return participant_id;
    }

    public String getFieldName() {
        return participant_name;
    }

    public String getFieldUrl() {
        return participant_url;
    }

    public String getFieldFacebookid() {
        return participant_facebookid;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
