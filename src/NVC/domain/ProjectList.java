package NVC.domain;

/**
 * Created by a.saho on 23.05.2016.
 */
public class ProjectList {

    private int id;
    private String projectCaption;

    public ProjectList(String projectCaption) {
                this.projectCaption = projectCaption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectCaption() {
        return projectCaption;
    }

    public void setProjectCaption(String projectCaption) {
        this.projectCaption = projectCaption;
    }
}
