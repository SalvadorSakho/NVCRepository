package NVC.Service;

import NVC.domain.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


/**
 * Created by ${BIM} on 19.02.2016.
 */
public class Controller {
    /*====================================================================== стандартные поля ======================================================================*/
    private SoudSelection ss = new SoudSelection();
    private MP3Sound mp3Sound = new MP3Sound();
    private ObservableList<InterfaceInfo> interfaceInfoData = ss.getInterfaceInfoData();
    private ObservableList<GroupsList> groupInterface = ss.getgroupsLists();
    private ObservableList<UserList> usersInterface = ss.getUserLists();
    private ObservableList<ContactResult> contactResults = ss.getcontactResults();
    private ObservableList<ProjectList> projectLists = ss.getProjectCaption();
    private String groupId;
    public Thread thead_1;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

/*====================================================================== FXML поля ======================================================================*/

    @FXML
    private Pane mainPane;

    @FXML
    private TextArea systemOutput;

    @FXML
    private TextArea caseIdArea;

    @FXML
    private TextArea phoneArea;

    @FXML
    private Label fortimeArea;

    @FXML
    private DatePicker calendarView;

    @FXML
    private DatePicker calendarViewEnd;

    @FXML
    public Slider sliderSound;

    @FXML
    private TableView<InterfaceInfo> tableInterfaceInfo;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn1;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn2;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn3;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn4;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn5;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn6;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn7;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn8;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn9;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn10;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn11;

    @FXML
    private TableColumn<InterfaceInfo, String> colomn12;

    @FXML
    private Button playBtn;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button rewindBtn;

    @FXML
    private Button forwardBtn;

    @FXML
    private Button deleteFiltersBtn;

    @FXML
    private Button stopSearchingBtn;

    @FXML
    private ComboBox groupListView;

    @FXML
    private ComboBox specialistListView;

    @FXML
    private ComboBox contactResultBtn;

    @FXML
    private ComboBox projectBtn;

    @FXML
    private ProgressIndicator progressIdicator;

    private InterfaceInfo row;

    private String SpecialistId;

    private ContextMenu cm;

    public String getSpecialistId() {
        return SpecialistId;
    }

    public void setSpecialistId(String specialistId) {
        SpecialistId = specialistId;
    }

    public Button getPlayBtn() {
        return playBtn;
    }

    public void setPlayBtn(Button playBtn) {
        this.playBtn = playBtn;
    }

    public Button getPauseBtn() {
        return pauseBtn;
    }

    public void setPauseBtn(Button pauseBtn) {
        this.pauseBtn = pauseBtn;
    }

/*====================================================================== Методы начало ======================================================================*/

    /*Поиск*/
    public void soundfinder() {

        ss.sqlProcedureFinal(caseIdArea.getText(), calendarView.getValue().toString(), calendarViewEnd.getValue().toString(), phoneArea.getText().replaceAll(",", "\',\'"), specialistListView.getSelectionModel().getSelectedItem().toString(), contactResultBtn.getSelectionModel().getSelectedItem().toString(), projectBtn.getSelectionModel().getSelectedItem().toString(), groupListView.getSelectionModel().getSelectedItem().toString());

        colomn1.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("LID"));
        colomn2.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("ProjectCaption"));
        colomn3.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("AuthorName"));
        colomn4.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("Owner"));
        colomn5.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("Result"));
        colomn6.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("Phonenumber"));
        colomn7.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("CallCreated"));
        colomn8.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("CallLength"));
        colomn9.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("Direction"));
        colomn10.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("TaskName"));
        colomn11.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("recordurl"));
        colomn12.setCellValueFactory(new PropertyValueFactory<InterfaceInfo, String>("StopSide"));

        if (tableInterfaceInfo.getItems().isEmpty()) {
            systemOutput.setText("Записи не найдены");
        }
        if (!tableInterfaceInfo.getItems().isEmpty()) {
            systemOutput.setText("Готово");
        }
        Thread thread_4 = Thread.currentThread();
        thread_4.interrupt();
        progressIdicator.setVisible(false);
    }

    /*======================================================= Основной метод (открывает паралельный поток \\ выводит таблу. на экран \\ заплняет пустые строки) =====================================*/
    @FXML
    public void select() {


        systemOutput.setText("Поиск");
        if (!interfaceInfoData.isEmpty()) {
            interfaceInfoData.clear();
        }
        if (caseIdArea.getText().isEmpty()) {
            caseIdArea.setText("");
        }

        if (phoneArea.getText().isEmpty()) {
            phoneArea.setText("");
        }

        if (specialistListView.getSelectionModel().getSelectedItem() == null) {
            specialistListView.setValue("");
        }

        if (contactResultBtn.getSelectionModel().getSelectedItem() == null) {
            contactResultBtn.setValue("Результат контакта");
        }
        if (projectBtn.getSelectionModel().getSelectedItem() == null) {
            projectBtn.setValue("");
        }
        if (groupListView.getSelectionModel().getSelectedItem() == null) {
            groupListView.setValue("Группа");
        }

        if (calendarView.getValue() == null) {
            calendarView.setValue(LocalDate.now().minusDays(90));
        }
        if (calendarViewEnd.getValue() == null) {
            calendarViewEnd.setValue(LocalDate.now());
        }

        if (calendarView.getValue().equals(LocalDate.now().minusDays(90)) && calendarViewEnd.getValue().equals(LocalDate.now()) && caseIdArea.getText().equals("") && phoneArea.getText().equals("") && groupListView.getSelectionModel().getSelectedItem().equals("Группа") && specialistListView.getSelectionModel().getSelectedItem().equals("") && contactResultBtn.getSelectionModel().getSelectedItem().equals("Результат контакта") && projectBtn.getSelectionModel().getSelectedItem().equals("")) {
            systemOutput.setText("Добавьте ограничения");
            progressIdicator.setVisible(false);
            return;
        }

        progressIdicator.setVisible(true);
        progressIdicator.setProgress(-1d);
        thead_1 = new
                Thread(()
                ->
        {
            soundfinder();
        });
        thead_1.start();
        tableInterfaceInfo.setItems(interfaceInfoData);
    }

    @FXML
    public void openProjectList() {
        projectLists.clear();
        projectBtn.getItems().clear();
        ss.sqlSelectProject();
        for (int i = 0; i < projectLists.size(); i++) {
            projectBtn.getItems().add(projectLists.get(i).getProjectCaption());
        }
    }

    @FXML
    public void openResultList() {
        contactResults.clear();
        ss.sqlSelectResult();
        contactResultBtn.getItems().clear();
        for (int i = 0; i < contactResults.size(); i++) {
            contactResultBtn.getItems().addAll(contactResults.get(i).getContactResult());
        }
    }

    @FXML
    public void openGroupList() {
        groupInterface.clear();
        groupListView.getItems().clear();
        ss.sqlSelectGroups();
        for (int i = 0; i < groupInterface.size(); i++) {
            groupListView.getItems().addAll(groupInterface.get(i).getGroupCaption());
        }
        specialistListView.getItems().clear();
        usersInterface.clear();
    }

    @FXML
    public void openUsersList() {
        usersInterface.clear();
        specialistListView.getItems().clear();

        if (groupListView.getValue() == null) {
            groupListView.setValue("Profit");
        }
        try {
            if (groupListView.getValue().toString().equals("Forcing")) {

                ss.sqlSelectSpecialists("16");
            }
            if (groupListView.getValue().toString().equals("VIP")) {

                ss.sqlSelectSpecialists("17");
            }
            if (groupListView.getValue().toString().equals("Inbound")) {

                ss.sqlSelectSpecialists("25");
            }
            if (groupListView.getValue().toString().equals("Profit")) {

                ss.sqlSelectSpecialists("27");
            }

            int spesialistIndex;

            if (specialistListView.getItems().isEmpty()) {
                for (int i = 0; i < usersInterface.size(); i++) {
                    specialistListView.getItems().addAll(usersInterface.get(i).getName());
                }
            }
            spesialistIndex = specialistListView.getSelectionModel().getSelectedIndex();
            if (spesialistIndex < 0) {
                spesialistIndex = 0;
            }
            setSpecialistId(String.valueOf(usersInterface.get(spesialistIndex).getID()));
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    @FXML
    public void menuRequest() {
        cm = new ContextMenu();
        MenuItem menu1 = new MenuItem("Копировать");
        cm.setHideOnEscape(true);
        cm.getItems().clear();
        cm.getItems().add(menu1);
        cm.show(tableInterfaceInfo, cm.getX(), cm.getY());
        try {
            menuAction(menu1);
        } catch (IndexOutOfBoundsException e) {
            systemOutput.setText("Запись не выбрана");
        }
    }

    public void menuAction(MenuItem menuItem) {
        TablePosition pos = tableInterfaceInfo.getSelectionModel().getSelectedCells().get(0);
        row = tableInterfaceInfo.getItems().get(pos.getRow());
        TableColumn col = pos.getTableColumn();
        String data = (String) col.getCellObservableValue(row).getValue();

        if (menuItem.getText().equals("Копировать")) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(data);
            clipboard.setContent(content);
        }
    }

    @FXML
    public void mousDoublePress() {
        tableInterfaceInfo.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if (playBtn.isDisable()) {
                        mp3Sound.pauseMusic();
                    }
                    mp3Sound.playSounds(tableInterfaceInfo.getSelectionModel().getSelectedItem().getRecordurl(), sliderSound, Double.parseDouble(tableInterfaceInfo.getSelectionModel().getSelectedItem().getCallLength()), fortimeArea);
                    playBtn.setDisable(true);
                    pauseBtn.setDisable(false);
                }
            }
        });
    }

    @FXML
    public void playSound() {
        try {
            InterfaceInfo interfaceInfo = tableInterfaceInfo.getSelectionModel().getSelectedItem();
            systemOutput.setText("Вопроизвидение записи");
            mp3Sound.playSounds(interfaceInfo.getRecordurl(), sliderSound, Double.parseDouble(interfaceInfo.getCallLength()), fortimeArea);
            playBtn.setDisable(true);
            pauseBtn.setDisable(false);
        } catch (NullPointerException e) {
            systemOutput.setText("Выберите запись для воспроизведения");
        }
    }

    @FXML
    public void pauseAction() {
        try {
            mp3Sound.pauseMusic();
            systemOutput.setText("Пауза");
            playBtn.setDisable(false);
            pauseBtn.setDisable(true);
        } catch (NullPointerException e) {
            systemOutput.setText("Запись не выбрана");
        }
    }

    @FXML
    public void saveFile() {
        try {
            Saver saver = new Saver();
            InterfaceInfo interfaceInfo = tableInterfaceInfo.getSelectionModel().getSelectedItem();
            String userName = System.getProperty("user.name");
            File f = new File("C:\\Users\\" + userName + "\\Desktop\\NVC_records");
            if (!f.exists()) {
                f.mkdir();
            }
            File file1 = new File(interfaceInfo.getRecordurl()).getAbsoluteFile();
            String fileName = interfaceInfo.getLID() + "_" + interfaceInfo.getCallCreated().replaceAll(" |\\.|:", "_");
            File file2 = new File("C:/Users/" + userName + "/Desktop/NVC_records/", fileName + ".mp3").getAbsoluteFile();
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saver.copyFileUsingChannel(file1, file2);
        } catch (NullPointerException e) {
            systemOutput.setText("Не выбрана запись для пересохранения");
        }
    }

    @FXML
    public void rewind() {
        try {
            mp3Sound.rewideAudio(sliderSound, fortimeArea);
            playBtn.setDisable(true);
            pauseBtn.setDisable(false);
        } catch (NullPointerException e) {
            systemOutput.setText("Запись не воспроизводится");
        }
    }

    @FXML
    public void forward() {
        try {
            mp3Sound.forwardAudio(sliderSound, fortimeArea);
            playBtn.setDisable(true);
            pauseBtn.setDisable(false);
        } catch (NullPointerException e) {
            systemOutput.setText("Запись не воспроизводится");
        }
    }


    @FXML
    public void deleteFilters() {
        caseIdArea.setText("");
        phoneArea.setText("");
        specialistListView.setValue("");
        contactResultBtn.setValue("Результат контакта");
        projectBtn.setValue("");
        groupListView.setValue("Группа");
        calendarView.setValue(null);
        calendarViewEnd.setValue(null);
    }

    @FXML
    public void stopSearching() {
        ss.stopSelection();
    }

/*    public void systemOutputChanges(){
        systemOutput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    systemOutput.setStyle("-fx-border-color: blue;");
            }
        });
    }*/
}

