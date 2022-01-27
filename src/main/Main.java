package main;

import utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dan Bowering
 * C195 - Software II
 * WGU Student ID#: 000811635
 * JavaDocs folder is located in "C195 Project/src/JavaDoc
 *
 *
 * The main class of the program.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        primaryStage.setTitle("Main Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * The main method of the program.
     * @param args
     */
    public static void main(String[] args) {

        JDBC.makeConnection();

        launch(args);
    }
}
