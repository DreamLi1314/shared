package org.javafamily.jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FxMain1 extends Application {

   @Override
   public void start(Stage stage) throws Exception {
      stage.setTitle("Hello JavaFX!");

      Button btn = new Button("Button");

      StackPane root = new StackPane();
      root.getChildren().add(btn);
      stage.setScene(new Scene(root, 300, 250));
      stage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
