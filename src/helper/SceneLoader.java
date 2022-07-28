package helper;

import javafx.event.ActionEvent;

import java.io.IOException;

@FunctionalInterface
public interface SceneLoader {
    void setScene(String path, String title, ActionEvent actionEvent);
}
