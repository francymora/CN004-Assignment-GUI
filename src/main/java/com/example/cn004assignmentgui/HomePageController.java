package com.example.cn004assignmentgui;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    public Label lblReportCount;
    @FXML
    public StackPane StackPaneHome;
    @FXML
    private Label lblError;
    @FXML
    private ChoiceBox<String> serviceChoice;

    @FXML
    private CheckBox chkPolice;

    @FXML
    private CheckBox chkAmbulance;

    @FXML
    private CheckBox chkFireBrigade;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextArea txtProblemDescription;


    @FXML
    public BorderPane MainBoardPaneHome;
    @FXML
    public Label lblCaseSolved;



    // List to store selected services
    private List<CheckBox> selectedServices = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        updateReportCount();
        // Add or remove CheckBox change listeners
        ChangeListener<Boolean> changeListener = (obs, wasSelected, isSelected) -> {
            CheckBox checkBox = (CheckBox) ((BooleanProperty) obs).getBean();
            if (isSelected) {
                if (selectedServices.size() < Integer.parseInt(serviceChoice.getValue())) {
                    selectedServices.add(checkBox);
                } else {
                    checkBox.setSelected(false);
                }
            } else {
                selectedServices.remove(checkBox);
            }
        };

        chkPolice.selectedProperty().addListener(changeListener);
        chkAmbulance.selectedProperty().addListener(changeListener);
        chkFireBrigade.selectedProperty().addListener(changeListener);

        // Add input validation to text fields
        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("^[a-zA-Z]+$")) {
                Platform.runLater(() -> {
                    txtName.clear();
                    lblError.setText("Invalid input. Please enter only letters for your name.");
                });
            } else {
                lblError.setText(""); // Hide the error label if the field is empty or contains only letters
            }
        });


        txtLastName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("^[a-zA-Z]+$")) {
                Platform.runLater(() -> {
                    txtLastName.clear();
                    lblError.setText("Invalid input. Please enter only letters for your surname.");
                });
            } else {
                lblError.setText(""); // Hide the error label if the field is empty or contains only letters
            }
        });

        txtPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("[0-9]+")) {
                Platform.runLater(() -> {
                    txtPhoneNumber.clear();
                    lblError.setText("Invalid input. Please enter a 10-digit phone number without spaces or special characters.");
                });
            } else {
                lblError.setText(""); // Hide the error label if the field is empty or contains only numbers
            }
        });

        txtAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("[a-zA-Z0-9 ]+")) {
                Platform.runLater(() -> {
                    txtAddress.clear();
                    lblError.setText("Invalid input. Please enter only letters, numbers, and spaces for your address.");
                });
            } else {
                lblError.setText(""); // Hide the error label if the field is empty or contains only letters, numbers, and spaces
            }
        });

        txtProblemDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("[a-zA-Z0-9 ,.:;!?']+")) {
                Platform.runLater(() -> {
                    txtProblemDescription.clear();
                    lblError.setText("Invalid input. Please enter only letters, numbers, and the following symbols: , . : ; ! ?");
                });
            } else {
                lblError.setText(""); // Hide the error label if the field is empty or contains only letters, numbers, and specified symbols
            }
        });


        updateCaseSolvedlbl();


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


    @FXML
    private void handleSubmit() {
        // Check if all input fields are filled
        if (txtName.getText().isEmpty() || txtLastName.getText().isEmpty() ||
                txtAddress.getText().isEmpty() || txtPhoneNumber.getText().isEmpty() ||
                txtProblemDescription.getText().isEmpty()) {
            showAlert("Error", "All fields are required.", Alert.AlertType.ERROR);
            return;
        }
        if (txtPhoneNumber.getText().length() != 10) {
            lblError.setText("Invalid input. Please enter a 10-digit phone number without spaces or special characters.");
            return; // Exit the method if the phone number is invalid
        } else {
            lblError.setText(""); // Restore the error label if the phone number is valid
        }
        // Create Callerinformation object
        Callerinformation.CreateCaller(txtName.getText(), txtLastName.getText(), txtAddress.getText(), txtPhoneNumber.getText(), txtProblemDescription.getText());

        // Create a list of selected services
        List<String> services = new ArrayList<>();
        for (CheckBox checkBox : selectedServices) {
            services.add(checkBox.getText());
        }
        String servicesString = String.join(", ", services);
        Callerinformation callerinformation = Callerinformation.CreateCaller(txtName.getText(), txtLastName.getText(), txtAddress.getText(), txtPhoneNumber.getText(), txtProblemDescription.getText());

        // Create the report
        String report = "The caller: " + callerinformation.getName() + " " + callerinformation.getLastname() +
                "\nwho called from this phone number: " + "(+44)" + callerinformation.getPhonumber() +
                "\nhas requested the following services: " + servicesString +
                "\nAt the following address: " + callerinformation.getAddress() + "." +
                "\nFor the following issue: " + callerinformation.getDescription();

        // Print the report
        System.out.println(report);
        ReportCreate.CreateFolder();
        ReportCreate.generateNextReport(report);
        updateReportCount();
        // Reset fields after submission
        clearFields();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    // Helper method to clear input fields after submission
    private void clearFields() {
        serviceChoice.getSelectionModel().clearSelection();
        chkPolice.setSelected(false);
        chkAmbulance.setSelected(false);
        chkFireBrigade.setSelected(false);
        txtName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        txtProblemDescription.clear();
    }




    @FXML
    private void goToReportPage(ActionEvent event) {
        // Load the content of the new page from an FXML file or create the content programmatically
        Pane newContent = loadContent("ReportManagerPageContent.fxml");

        // Add the content to the center of the BorderPane
        MainBoardPaneHome.setCenter(newContent);
    }

    @FXML
    private void RefreshPage(ActionEvent event) {
        // Load the content of the new page from an FXML file or create the content programmatically
        Pane newContent = loadContent("HomePageContent.fxml");

        // Add the content to the center of the BorderPane
        MainBoardPaneHome.setCenter(newContent);
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


}
