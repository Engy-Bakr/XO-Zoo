/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.zoo.client.v0.pkg0.pkg1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author abdelmun3m
 */
public class Client extends Application {
    
    
    //"logout:name="+userName+",pass="+userPass
    //"logout:name="+userName+",pass="+userPass
    
    //player Data
    String userName;
    String userPass;
    Stage stage ;
    boolean vaildUser ;
    
    
    //network Data
    public static final int port = 5555;
    public static final String ip = "10.140.200.226";
    Socket socket = null;
    DataInputStream inStream = null;
    PrintStream outStream = null ;
    Boolean serverAvilability = false;
    Thread serverListener ;
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
    
    @Override
    public void start(Stage primaryStage) throws SocketException {
        stage = primaryStage;
        createLoginScene();
       
    }

    public void startServerConnection(Button btn,Label loginValid,String userName, String pass){      
        if(!serverAvilability){
             new Thread(){
              public void run(){
                  String responseMsg = null;
                try {
                    
                    socket = new Socket(ip,port);
                    inStream = new DataInputStream(socket.getInputStream());
                    outStream = new PrintStream(socket.getOutputStream());
                    serverAvilability = true;

                    System.out.println(userName);
                    final String loginMsg = "login:name="+userName+",pass="+pass;
                    outStream.println(loginMsg);

                    System.out.println("start read");
                    String reply = inStream.readLine();
                    String msg = serverMessageHandler(reply);
                    if(msg.equals("false")){
                        vaildUser = false;
                        System.out.println("invalid user");
                        responseMsg = "invalid user";
                    }else{
                        vaildUser = true;
                        responseMsg = msg; 
                        //handel data
                    }
                } catch (IOException ex) {
                    serverAvilability = false;
                    responseMsg = "Faild to connect to server "+ ex.getMessage().toString();
                }finally{
                     try {
                        this.finalize();
                        Client.this.connectionResult(btn,loginValid,responseMsg);
                        System.out.println("connection thread finalized");
                    } catch (Throwable ex2) {
                        System.err.println("Faild to close connection thread "+ ex2.getMessage());
                    }
                }


            }


             }.start();
        }


    }
    
    public void createLoginScene(){
    
        //create Login view
        
        FlowPane loginScene = new FlowPane();
        loginScene.setOrientation(Orientation.VERTICAL);
        
        
        Label userNameLable= new Label("User Name ");
        TextField userNameField = new TextField();
        
        
        Label userPassLable = new Label("User PassWord ");
        TextField userPassField = new TextField();
   
        
        loginScene.getChildren().add(userNameLable);
        loginScene.getChildren().add(userNameField);
        
        loginScene.getChildren().add(userPassLable);
        loginScene.getChildren().add(userPassField);
        
        
        
        Button btn = new Button("Login");
        loginScene.getChildren().add(btn);
        
        Label loginValid = new Label();
        loginScene.getChildren().add(loginValid);
        
         btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {   
                 btn.setVisible(false);
                 userName = userNameField.getText();
                 userPass = userPassField.getText();
   
                if(!(userName.equals("") && userPass.equals(""))){  
                    loginValid.setText("Connecting...");
                    startServerConnection(btn,loginValid,userName,userPass);
                }else{ 
                    loginValid.setText("Please Enter user Name And Pass");
                    btn.setVisible(true);
                }  
                
            }
        
        });
        
        Scene scene = new Scene(loginScene, 300, 250);
        
        stage.close();
        stage = new Stage();
        stage.setTitle("Tic-Tac-Toe-Zoo!");
        stage.setScene(scene);
        stage.show();
    }
   
    public void createGameStage(String u){
    
        FlowPane root = new FlowPane();
         
        Button btn = new Button();
        btn.setText("Hello "+u);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
       
        Button logOut = new Button();
        logOut.setText("Log out");
        logOut.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               logOut(userName,userPass);
            }
        });
        
        
        
        root.getChildren().add(btn);
        root.getChildren().add(logOut);
        
        Scene scene = new Scene(root, 300, 250);
       
        stage.close();
        stage = new Stage();
        stage.setTitle("Tic-Tac-Toe-Zoo!");
        stage.setScene(scene);
        stage.show();
        
        //return scene;
        
        
    }
    
   
    public void startServerListener(){
    
       serverListener  = new Thread(){
            public void run(){
                try {
                    String message = "";
                    //change to UTF
                    message = inStream.readLine();
                    serverMessageHandler(message);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
       };
       
       serverListener.start();
    
    }
    
    public String serverMessageHandler(String msg){
   
        String message = msg.split("\\:")[0];
        switch(message){
            
            case "login":
            String replay =  msg.split("\\:")[1];
            return replay;
            
            
            default:
                return "Undefined Message";
        }
      
    }
    
    
    public void login(String userName , String pass){
            
            System.out.println("connection stablished and try login connection ");
            System.out.println("ktabna");
            
                      
                }

    private void connectionResult(Button btn,Label loginValid, String errorMsg) { 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(errorMsg != null){
                    loginValid.setText(errorMsg);
                    btn.setVisible(true);
                }
                
                if(Client.this.vaildUser){

                    Client.this.createGameStage(userName);
                    
                
                }
            }
        });
       
    }

      
    private void logOut(String userName, String userPass) {
        if(serverAvilability){
                final String loOutMsg = "logout:name="+userName+",pass="+userPass;
                outStream.println(loOutMsg);
                try {                    
                    socket.close();
                    inStream.close();
                    outStream.close();
                    serverAvilability = false;
                    userName = null;
                    userPass = null;
                    vaildUser = false;
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    createLoginScene();
                }
            }
    }
       
}
    
    
    
