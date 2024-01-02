package tempPackage;

import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class tempClass extends Application {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Window Title");

        this.button = new Button();
        button.setText("Button!");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 250, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
