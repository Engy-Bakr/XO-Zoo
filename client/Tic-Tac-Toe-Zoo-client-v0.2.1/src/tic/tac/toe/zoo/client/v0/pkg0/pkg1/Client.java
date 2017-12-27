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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
    boolean vaildUser  = false;
    boolean userSigned  = false;
    
    
    //network Data
    public static final int port = 5555;
    public static final String ip = "10.140.200.120";
    Socket socket = null;
    DataInputStream inStream = null;
    PrintStream outStream = null ;
    Boolean serverAvilability = false;
    Thread serverListener ;
    
    
    
    Label loginValid = null;
    Button btn = null;
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
                        //mode 1 login mode 2 signup
    public void startServerConnection(String mode,String userName, String pass){      
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
                    
                    if(mode.equals("login")){
                        final String loginMsg = "login:name="+userName+",pass="+pass;
                        outStream.println(loginMsg);
                    }else if(mode.equals("signup")){
                        final String loginMsg = "signup:name="+userName+",pass="+pass;
                        outStream.println(loginMsg);
                    }
                    System.out.println("start read");
                    String reply = inStream.readLine();
                    String msg = serverMessageHandler(reply);
                   
                    if(mode.equals("login")){
                        if(msg.equals("false")){
                        vaildUser = false;
                        System.out.println("invalid user");
                        responseMsg = "invalid user";
                        }else{
                            vaildUser = true;
                            responseMsg = msg; 
                            //handel data
                        }
                    }else if(mode.equals("signup")){
                        if(msg.equals("false")){
                            
                            responseMsg = "Faild To Sign up username is used";
                            //  faild to  sign up 
                            userSigned = false;
                            
                        }else if(msg.equals("true")){
                            userSigned = true;
                            //data inserted
                        }
                    }
                } catch (IOException ex) {
                    serverAvilability = false;
                    responseMsg = "Faild to connect to server "+ ex.getMessage().toString();
                }finally{
                     try {
                        this.finalize();
                        Client.this.connectionResult(responseMsg);
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
        
        GridPane loginScene = new GridPane();
        loginScene.setAlignment(Pos.CENTER);

        loginScene.setHgap(10);
        loginScene.setVgap(10);
        loginScene.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome-text");
        loginScene.add(scenetitle, 0, 0, 2, 1);

        Label userNameLable = new Label("User Name:");
        userNameLable.setId("labeluser");
        loginScene.add(userNameLable, 0, 1);

        TextField userNameField = new TextField();
        loginScene.add(userNameField, 1, 1);

        Label userPassLable = new Label("Password:");
        userPassLable.setId("labelpass");
        loginScene.add(userPassLable, 0, 2);

        PasswordField userPassField = new PasswordField();
        loginScene.add(userPassField, 1, 2);

        btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        loginScene.add(hbBtn, 1, 4);

        loginValid = new Label();
        loginValid.setId("log");
        loginScene.add(loginValid, 1, 6);
        

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                btn.setVisible(false);
                userName = userNameField.getText();
                userPass = userPassField.getText();

                if (!(userName.equals("") && userPass.equals(""))) {
                    loginValid.setText("Connecting...");
                    startServerConnection("login", userName, userPass);
                } else {
                    loginValid.setText("Please Enter user Name And Pass");
                    btn.setVisible(true);
                }

            }

        });

        Scene scene = new Scene(loginScene, 700, 575);
        scene.getStylesheets().add(Client.class.getResource("css/Login.css").toExternalForm());

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
    
    public void createSignUpScene(){
        //create scene 
        //call startServerConnection on button btn click
        // handel errors in loginvalid
    }
    public void startServerListener(){
    
       serverListener  = new Thread(){
            public void run(){
                try {
                    String message = "";
                    //change to UTF
                    message = inStream.readLine();
                    String msg = serverMessageHandler(message);
                    
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
            
            case "signup":
                //signup:true|false
                replay = msg.split("\\:")[1];
                return replay;
                //logout:server
                //logout:name=uername,pass=pass
            case "logout":
                
            default:
                return "Undefined Message";
        }
      
    }
    
    
    public void login(String userName , String pass){
            
            System.out.println("connection stablished and try login connection ");
            System.out.println("ktabna");
            
                      
                }

    private void connectionResult( String errorMsg) { 
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
                
                if(Client.this.userSigned){
                
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
    
    
    
    private void Signup(String userName ,String userPass){
    
        String signUpMsg = "signup:name="+userName+",pass="+userPass;
        
    
    }
       
}
    
    
    
