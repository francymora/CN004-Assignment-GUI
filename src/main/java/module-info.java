module com.example.cn004assignmentgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.cn004assignmentgui to javafx.fxml;
    exports com.example.cn004assignmentgui;
}