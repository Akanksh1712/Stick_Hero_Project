package com.aks.stick_hero_ap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SaveScreenController extends PauseController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private MusicController musicController;

    Image image = new Image(getClass().getResourceAsStream("BG-Save.jpg"));

    @FXML
    ImageView backgroundImageView;

    double targetWidth = 300;
    double targetHeight = 500;

    double scaleFactor = targetWidth / image.getWidth();

    //@FXML
    //AnchorPane pausePane;

//    @FXML
//    AnchorPane gamePane;

    //@FXML
    //Polygon resumeButton;

    public void saveGame(int serial){ // Pass values from 1-4 so that 1.txt to 4.txt is generated
        Player player = getPlayer();
        ObjectOutputStream out = null;
        String val = String.valueOf(serial);
        try{
            out = new ObjectOutputStream(new FileOutputStream("Saves\\" + val + ".txt"));
            out.writeObject(player);
//            System.out.println("Saved Successfully in " + val + ".txt");
        } catch (IOException e) {
            System.out.println("Unable to Save Game due to: " + e.getMessage());
        }
    }

    public void removeSaveGame(int serial){
        String saveFile = "";
        Path savePath = Paths.get(saveFile);
        try{
            Files.deleteIfExists(savePath);
//            System.out.println("Save File for Slot" + serial + " Deleted Successfully");
        } catch (IOException e) {
            System.out.println("Unable to Delete Save Game due to: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialiseSound(); // Setting up-Sound
        //pausePane.setVisible(true);
        //gamePane.setVisible(false);
        backgroundImageView.setImage(image);
        backgroundImageView.setFitWidth(targetWidth);
        backgroundImageView.setFitHeight(targetHeight);
        backgroundImageView.setPreserveRatio(false);

//        BoxBlur blur=new BoxBlur(10,10,3);
//        gamePane.setEffect(blur);


    }

    public void switchToPauseMenuScreen(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("PauseMenuScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 300, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        musicController.stopAudio(); // Stopping audio before changing scene
        stage.show();
    }

    @Override
    public void initialiseSound() {
        musicController = new MusicController(getClass().getResource("data.mp3").toExternalForm());
        muteUnmute();
    }

    public void muteUnmute() {
        if (AudioManager.isMuted()) { // if sound is muted
            musicController.stopAudio(); // stops the audio
        } else {
            musicController.playAudio(); //plays the audio
        }
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;
    }
}
