package org.javafamily.jfx;

import javafx.application.Application;
import javafx.stage.Stage;

public class FxMain2 extends Application {

   @Override
   public void start(Stage stage) throws Exception {
      stage.setTitle("Hello JavaFX!");

      stage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
