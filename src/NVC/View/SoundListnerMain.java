package NVC.View;

/**
 * Created by ${BIM} on 19.02.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Locale;

public class SoundListnerMain extends Application { /*Nerv vestibulocohlear*/

    public static void main(String[] args) {
        Locale.setDefault(Locale.CANADA);
        launch(args);
    }

    @Override
    public void start(Stage primaryStageUser1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/soundListnerView.fxml"));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        Pane pane = (Pane) root.lookup("#mainPane");
        TableView tableView = (TableView) root.lookup("#tableInterfaceInfo");
        Button btnSelect = (Button) root.lookup("#selectBTN");

        Button rewind = (Button) root.lookup("#rewindBtn");
        Button forward = (Button) root.lookup("#forwardBtn");

        ImageView imageView = (ImageView) root.lookup("#animation");
        ProgressIndicator progressIndicator = (ProgressIndicator) root.lookup("#progressIdicator");

        double sceneWidth = sSize.getWidth() - 7;
        pane.setPrefWidth(sceneWidth);
        tableView.setPrefWidth(sceneWidth);
        tableView.setLayoutX(3);

        btnSelect.setLayoutX(3);
        progressIndicator.setLayoutY(520);

        rewind.setStyle("-fx-background-image:  url('rewind2.png')");
        forward.setStyle("-fx-background-image: url('forward.png')");


        if (sSize.getWidth() == 1366.0) {
            imageView.setLayoutX(sceneWidth - 60);
            imageView.setLayoutY(5);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            progressIndicator.setPrefSize(85, 85);
            progressIndicator.setLayoutX(sceneWidth - 100);
            progressIndicator.setLayoutY(560);
        }

        Scene scene = new Scene(root, sceneWidth, 650);


        primaryStageUser1.setTitle("NVC");
        primaryStageUser1.setScene(scene);
        primaryStageUser1.setResizable(false);
        primaryStageUser1.show();

    }
}
