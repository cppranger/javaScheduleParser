package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Расписание ВятГУ");
        primaryStage.setScene(new Scene(root, 261, 355));
        primaryStage.setResizable(false);
        Image img = new Image(getClass().getResourceAsStream("media/ghosty.png"));
        primaryStage.getIcons().add(img);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
