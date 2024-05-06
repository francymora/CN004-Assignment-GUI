package com.example.cn004assignmentgui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.ResourceBundle;



public class ReportManagerController implements Initializable {
    @FXML
    public Label lblReportCount;
    @FXML
    public HBox FileShower;
    @FXML
    public VBox fileVBox;

    @FXML
    public Label lblCaseSolved;

    @FXML
    public TextField SearchBar;

    @FXML
    public StackPane StackPaneReportPage;
    @FXML
    public BorderPane MainBorderPaneReport;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        updateReportCount();
        updateCaseSolvedlbl();
        displayFilesInVBox();

    }


    private void updateReportCount() {
        try {
            Path path = Paths.get("report");  // Replace with the path to your folder
            long count = 0;
            if (Files.exists(path)) {
                count = Files.list(path).count();
            }
            lblReportCount.setText(String.valueOf(count));  // Replace lblReportCount with the fx:id of your Label
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void displayFilesInVBox() {
        File folder = new File("report");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            // Sort the array of files
            Arrays.sort(listOfFiles);

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    Label fileLabel = new Label(listOfFiles[i].getName());
                    fileLabel.getStyleClass().add("file-label");

                    Button solveButton = new Button();

                    try {
                        String content = Files.readString(listOfFiles[i].toPath());
                        if (content.contains("Emergency solved: yes")) {
                            solveButton.setText("Solved");
                            solveButton.getStyleClass().add("button-solved");
                            solveButton.setDisable(true);
                        } else {
                            solveButton.setText("To solve");
                            solveButton.getStyleClass().add("button-to-solve");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Add empty space between the button and the file name
                    Pane spacer = new Pane();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    // Add a mouse click event handler to the Label
                    int finalI = i;
                    fileLabel.setOnMouseClicked(event -> {
                        // Show a pop-up with the file content
                        showFileContentPopup(listOfFiles[finalI]);
                    });

                    // Add an event handler to the Button
                    solveButton.setOnAction(event -> {
                        // Show a pop-up to solve the case
                        showSolveCasePopup(listOfFiles[finalI], solveButton);
                    });

                    HBox hbox = new HBox(fileLabel, new Pane(), solveButton);
                    HBox.setHgrow(hbox.getChildren().get(1), Priority.ALWAYS);
                    hbox.setSpacing(15);
                    fileVBox.getChildren().add(hbox);
                }
            }
        } else {
            Label errorLabel = new Label("There is no File at the moment in the folder. Before to solve a case you need to create one");
            errorLabel.getStyleClass().add("file-label");
            fileVBox.getChildren().add(errorLabel);
        }
    }

    public void showFileContentPopup(File file) {
        // Create a new Stage for the pop-up
        Stage popupStage = new Stage();

        // Create a TextArea to display the file content
        TextArea fileContentArea = new TextArea();
        try {
            fileContentArea.setText(Files.readString(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a Button to close the pop-up
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            popupStage.close();
        });

        // Create a VBox to contain the TextArea and the Button
        VBox vbox = new VBox(10, fileContentArea, closeButton); // 10 is the spacing between elements

        // Set the VBox as the root of the Scene
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set the Scene on the Stage
        popupStage.setScene(scene);

        // Show the Stage
        popupStage.show();
    }

    public void showSolveCasePopup(File file, Button solveButton) {
        // Create a new Stage for the pop-up
        Stage popupStage = new Stage();

        // Create a Label to display the message
        Label messageLabel = new Label("Do you want to solve the case?");

        // Create a Button to close the pop-up
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            try {
                Files.writeString(file.toPath(), "\nEmergency solved: yes", StandardOpenOption.APPEND);
                solveButton.setText("Solved");
                updateCaseSolvedlbl();
                solveButton.getStyleClass().remove("button-to-solve");
                solveButton.getStyleClass().add("button-solved");
                solveButton.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            popupStage.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(event -> {
            popupStage.close();
        });

        // Create an HBox to contain the Buttons
        HBox buttonBox = new HBox(10, yesButton, noButton); // 10 is the spacing between buttons

        // Create a VBox to contain the Label and the HBox
        VBox vbox = new VBox(10, messageLabel, buttonBox); // 10 is the spacing between elements

        // Set the VBox as the root of the Scene
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set the Scene on the Stage
        popupStage.setScene(scene);

        // Show the Stage
        popupStage.show();
    }
    public void updateCaseSolvedlbl() {
        File folder = new File("report");
        File[] listOfFiles = folder.listFiles();

        int count = 0;

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        String content = Files.readString(file.toPath());
                        if (content.contains("Emergency solved: yes")) {
                            count++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lblCaseSolved.setText(String.valueOf(count));

    }



    @FXML
    private void goToHomePage(ActionEvent event) {
        // Load the content of the new page from an FXML file or create the content programmatically
        Pane newContent = loadContent("HomePageContent.fxml");

        // Add the content to the center of the BorderPane
        MainBorderPaneReport.setCenter(newContent);
    }

    @FXML
    private void RefreshPage(ActionEvent event) {
        // Load the content of the new page from an FXML file or create the content programmatically
        Pane newContent = loadContent("ReportManagerPageContent.fxml");

        // Add the content to the center of the BorderPane
        MainBorderPaneReport.setCenter(newContent);
    }

    private Pane loadContent(String filename) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
