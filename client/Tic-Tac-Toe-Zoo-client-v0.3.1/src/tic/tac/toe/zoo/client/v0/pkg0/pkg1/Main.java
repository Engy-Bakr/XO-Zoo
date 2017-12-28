/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.zoo.client.v0.pkg0.pkg1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        primaryStage.setTitle("TicTacToeZoe");
        Pane root = new Pane();
        
        Label login = new Label("Login");
        login.setId("login");
         login.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
              
        
                
                
                
            }
        
        
        });

        
        
        
        
        
        
        root.getChildren().add(login);

        Label signup = new Label("Sign Up");
        signup.setId("sign");
        root.getChildren().add(signup);
        
        
        
        
        
        
        
        
        login.setLayoutX(50);
        login.setLayoutY(448);
        
        signup.setLayoutX(570);
        signup.setLayoutY(448);
        
        

        Scene scene = new Scene(root, 700, 570);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("css/style.css").toExternalForm());
        primaryStage.show();
        
        
        
        
    }

   
    public static void main(String[] args) {
        launch(args);
    }
    
}
