package NVC.domain;

/**
 * Created by a.saho on 14.04.2016.
 */
public class GroupsList {
    private int ID;
    private String GroupCaption;

    public GroupsList(){}

    public GroupsList(int ID, String groupCaption) {
        this.ID = ID;
        GroupCaption = groupCaption;
    }

    public String getGroupCaption() {
        return GroupCaption;
    }

    public void setGroupCaption(String groupCaption) {
        GroupCaption = groupCaption;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
