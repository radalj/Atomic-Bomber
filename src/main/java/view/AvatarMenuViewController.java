package view;

import controller.ApplicationController;
import controller.AvatarMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.User;

import java.net.URL;
import java.util.Objects;

public class AvatarMenuViewController {
    private static Scene scene;
    @FXML
    private ImageView avatarImage;
    @FXML
    private ChoiceBox<String> avatarChoiceBox;
    @FXML
    private Pane dragPane;
    private AvatarMenuController avatarMenuController;
    public void initialize() {
        avatarMenuController = new AvatarMenuController();
        Image image = User.getCurrentUser().getImage();
        avatarImage.setImage(image);
        avatarChoiceBox.getItems().addAll("woman1", "woman2", "man1", "man2");
        avatarChoiceBox.setValue("woman1");
        avatarChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String path = "file:/home/radal/Atomic-Bomber/src/main/resources/images/avatars/" + newValue + ".jpg";
            Image newImage = new Image(path);
            avatarMenuController.selectImage(newImage);
            avatarImage.setImage(newImage);
        });
        avatarMenuController.setupDragAndDrop(dragPane);
    }
    public void start() {
        URL url = Main.class.getResource("/FXML/AvatarMenu.fxml");
        assert url != null;
        Pane root;
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            return;
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }
}