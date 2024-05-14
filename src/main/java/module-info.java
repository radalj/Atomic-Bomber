module graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    opens view to javafx.fxml;
    opens model to javafx.base;
}