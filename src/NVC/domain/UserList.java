package NVC.domain;

/**
 * Created by a.saho on 15.04.2016.
 */
public class UserList {

    public UserList(){}

    public UserList(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String Name;

}
