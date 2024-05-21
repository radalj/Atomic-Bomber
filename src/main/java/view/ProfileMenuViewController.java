package view;

import controller.ApplicationController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ProfileMenuViewController {
    public static void makeAlert(String title, String header, String content, String type) {
        Alert alert = switch (type) {
            case "ERROR" -> new Alert(Alert.AlertType.ERROR);
            case "INFORMATION" -> new Alert(Alert.AlertType.INFORMATION);
            case "WARNING" -> new Alert(Alert.AlertType.WARNING);
            case "CONFIRMATION" -> new Alert(Alert.AlertType.CONFIRMATION);
            default -> new Alert(Alert.AlertType.NONE);
        };
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage mainStage = ApplicationController.getStage();
        mainStage.setAlwaysOnTop(false);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true);
        alert.showAndWait();
        mainStage.setAlwaysOnTop(true);
    }
}
