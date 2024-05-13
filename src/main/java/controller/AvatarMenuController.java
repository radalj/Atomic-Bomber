package controller;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import model.User;

import java.io.File;

public class AvatarMenuController {
    public void selectImage(Image image) {
        User.getCurrentUser().setImage(image);
    }

    public Image setupDragAndDrop(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        File file;
        if (dragboard.hasFiles()) {
            file = dragboard.getFiles().get(0);
            Image image = new Image(file.toURI().toString());
            return image;
        }
        return null;
    }
}
