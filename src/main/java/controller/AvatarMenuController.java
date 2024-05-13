package controller;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.User;

public class AvatarMenuController {
    public void selectImage(Image image) {
        User.getCurrentUser().setImage(image);
    }
    public void setupDragAndDrop(Pane dragPane) {
        dragPane.setOnDragOver(event -> {
            if (event.getGestureSource() != dragPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        dragPane.setOnDragDropped(event -> {
            javafx.scene.input.Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                String path = db.getFiles().get(0).toURI().toString();
                Image image = new Image(path);
                selectImage(image);
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
