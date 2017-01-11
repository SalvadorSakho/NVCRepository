package NVC.Service;

/**
 * Created by ${BIM} on 19.02.2016.
 */

import NVC.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class SoudSelection {
    private Connection con;
    private ResultSet rs;
    private String server = "DELTA";
    private String database = "ucgreports";
    private String port = "1433";
    private String user = "oktellrecords";
    private String password = "oktellrecords12345";
    private String jdbcUrl = "jdbc:sqlserver://" + server + ":" + port + ";user=" + user + ";password=" + password + ";databaseName=" + database;
    private PreparedStatement pstmt;

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }


    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    private ObservableList<InterfaceInfo> interfaceInfoData = FXCollections.observableArrayList();

    public ObservableList<InterfaceInfo> getInterfaceInfoData() {
        return interfaceInfoData;
    }

    private ObservableList<GroupsList> groupsLists = FXCollections.observableArrayList();

    public ObservableList<GroupsList> getgroupsLists() {
        return groupsLists;
    }

    private ObservableList<UserList> userLists = FXCollections.observableArrayList();

    public ObservableList<UserList> getUserLists() {
        return userLists;
    }

    private ObservableList<ContactResult> contactResults = FXCollections.observableArrayList();

    public ObservableList<ContactResult> getcontactResults() {
        return contactResults;
    }

    private ObservableList<ProjectList> projectLists = FXCollections.observableArrayList();

    public ObservableList<ProjectList> getProjectCaption() {
        return projectLists;
    }


    /*====================================== Вызов методов ====================================================*/

    public ObservableList<ProjectList> sqlSelectProject() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(jdbcUrl);
            Statement st = con.createStatement();
            setRs(st.executeQuery("select distinct ProjectCaption from ucgreports.dbo.SoundListnerTab"));
            int i = 0;
            while (getRs().next()) {
                projectLists.add(i, new ProjectList(getRs().getString(1)));
                i++;
            }
            getRs().close();
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<GroupsList> sqlSelectGroups() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(jdbcUrl);
            Statement st = con.createStatement();
            setRs(st.executeQuery("select id, Caption from max.dbo.usersgroups where id in (17,16,27,25)"));
            int i = 0;
            while (rs.next()) {
                groupsLists.add(i, new GroupsList(rs.getInt(1), rs.getString(2)));
                i++;
            }
            getRs().close();
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<ContactResult> sqlSelectResult() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(jdbcUrl);
            Statement st = con.createStatement();
            setRs(st.executeQuery("select distinct result from ucgreports.dbo.SoundListnerTab"));
            int i = 0;
            while (getRs().next()) {
                contactResults.add(i, new ContactResult(rs.getString(1)));
                i++;
            }
            getRs().close();
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<UserList> sqlSelectSpecialists(String GroupID) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(jdbcUrl);
            Statement st = con.createStatement();
            setRs(st.executeQuery("select id,Name from max.dbo.users where Deleted is null and GroupID in(" + Integer.parseInt(GroupID) + ")"));
            int i = 0;
            while (rs.next()) {
                userLists.add(i, new UserList(rs.getInt(1), rs.getString(2)));
                i++;
            }
            getRs().close();
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sqlProcedureFinal(String lid, String dateStart, String dateEnd, String phonenumber, String specialistName, String contactResult, String projectCaption, String groupCaption) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(jdbcUrl);
            setPstmt(con.prepareStatement("{call a_SoundListnerProcedure(?, ?, ?, ?, ?, ?, ?, ?)}"));
            getPstmt().setString(1, lid);
            getPstmt().setString(2, dateStart);
            getPstmt().setString(3, dateEnd);
            getPstmt().setString(4, phonenumber);
            getPstmt().setString(5, specialistName);
            getPstmt().setString(6, contactResult);
            getPstmt().setString(7, projectCaption);
            getPstmt().setString(8, groupCaption);
            setRs(pstmt.executeQuery());
            int i = 0;
            while (getRs().next()) {
                interfaceInfoData.add(i, new InterfaceInfo(getRs().getString(1), getRs().getString(2), getRs().getString(3), getRs().getString(4), getRs().getString(5), getRs().getString(6), getRs().getString(7), getRs().getString(8), getRs().getString(9), getRs().getString(10), getRs().getString(11), getRs().getString(12)));
                i++;
            }
            getRs().close();
            getPstmt().close();
        } catch (SQLException e) {
            //    e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            //e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                getRs().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            }
        }
    }

    public void stopSelection() {
        try {
            getPstmt().cancel();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {

        }
    }
}