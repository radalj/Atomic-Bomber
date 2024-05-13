package view;

import controller.ApplicationController;
import controller.AvatarMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.User;

import java.io.File;
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

    public void setAvatarImage(Image image) {
        avatarMenuController.selectImage(image);
        avatarImage.setImage(image);
    }

    public void initialize() {
        avatarMenuController = new AvatarMenuController();
        Image image = User.getCurrentUser().getImage();
        avatarImage.setImage(image);
        avatarChoiceBox.getItems().addAll("woman1", "woman2", "man1", "man2");
        avatarChoiceBox.setValue("woman1");
        avatarChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String path = "file:/home/radal/Atomic-Bomber/src/main/resources/images/avatars/" + newValue + ".jpg";
            Image newImage = new Image(path);
            setAvatarImage(newImage);
        });
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

    public void dragDropped(DragEvent dragEvent) {
        Image image = avatarMenuController.setupDragAndDrop(dragEvent);
        if (image != null) {
            setAvatarImage(image);
        }
    }

    public void dragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != dragPane && dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an avatar");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(ApplicationController.getStage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            setAvatarImage(image);
        }
    }

    public void Back(ActionEvent actionEvent) {
        ApplicationController.setScene(ProfileMenuViewController.scene);
    }
}