/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.zoo.client.v0.pkg0.pkg1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    String active;
    boolean Active;
    static List<Player> client = new Vector<Player>();
    static List<String> records = new Vector<String>();
    static List<Game> games = new Vector<Game>();
    
    
    //network Data
    public static final int port = 5555;
    public static final String ip = "10.140.200.81";
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
       // createSignUpScene();
       
    }
                        //mode 1 login mode 2 signup
    public void startServerConnection(String mode,String name, String pass){      
        if(!serverAvilability){
            new Thread(){
                 
                 //this thread to try to connect to server 
                 //it will connect to server and close in case of connection succesed or faild
                 //thread should wait a replay
                 public void run(){
                  String responseMsg = null;
                try {
                    
                    socket = new Socket(ip,port);
                    inStream = new DataInputStream(socket.getInputStream());
                    outStream = new PrintStream(socket.getOutputStream());
                    serverAvilability = true;

                    
                    if(mode.equals("login")){
                        //send login message
                        final String loginMsg = "login:name="+name+",pass="+pass;
                        outStream.println(loginMsg);
                    }else if(mode.equals("signup")){
                        //send signup message
                        final String loginMsg = "signup:name="+name+",pass="+pass;
                        outStream.println(loginMsg);
                    }
                    
                    
                    //reade server stream 
                    System.out.println("start read");
                    String reply = inStream.readLine();
                    
                    //handel server replay message 
                    String msg = serverMessageHandler(reply);
                   
                    if(mode.equals("login")){
                        if(msg.equals("false")){
                            vaildUser = false;
                            userSigned = false ;
                            serverAvilability =false;
                            userName = null;
                            userPass = null;
                            responseMsg = "invalid user";
                        }else{
                            vaildUser = true;
                            userSigned = false;
                            userName = name;
                            userPass = pass;
                            responseMsg = msg; 
                        }
                    }else if(mode.equals("signup")){
                        if(msg.equals("false")){
                            //  faild to  sign up 
                            serverAvilability = false;
                            userSigned = false;
                            userName = null;
                            userPass = null;
                            responseMsg = "Faild To Sign up username is used Before";
                        }else if(msg.equals("true")){
                            userSigned = true;
                            vaildUser = false;
                            Client.this.userName = name;
                            Client.this.userPass = pass;
                            //data inserted
                        }
                    }
                } catch (IOException ex) {
                    serverAvilability = false;
                    vaildUser = false;
                    userSigned = false;
                    responseMsg = "Faild to connect to server "+ ex.getMessage().toString();
                }finally{
                     try {
                        this.finalize();
                        Client.this.runtimeUIUpdates(responseMsg);
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
                String name = userNameField.getText();
                String pass = userPassField.getText();
                btn.setVisible(false);
                if (!(name.equals("") && pass.equals(""))) {
                    loginValid.setText("Connecting...");
                    startServerConnection("login", name,
                            pass);
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
    
        
        startServerListener();
        FlowPane root = new FlowPane();
         
        Button btn = new Button();
        btn.setText("Hello "+u);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                
                outStream.println("online:");
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
        
        Pane root = new Pane();
        Label userName = new Label("User Name:");
        root.getChildren().add(userName);

        TextField userTextField = new TextField();
        userTextField .setId("fild");
        root.getChildren().add(userTextField);

        Label pw = new Label("Password:");
        root.getChildren().add(pw);

        PasswordField pwBox = new PasswordField();
         pwBox.setId("fild");
        root.getChildren().add(pwBox);

        btn = new Button("Sign Up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        root.getChildren().add(hbBtn);

        loginValid = new Label();
        loginValid.setId("signLable");
        root.getChildren().add(loginValid);
        loginValid.setId("actiontarget");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String name = userTextField.getText();
                String pass = pwBox.getText();
                btn.setVisible(false);
                if(!(name.equals("") && pass.equals(""))){
                    startServerConnection("signup", name,pass);
                }else{
                    btn.setVisible(true);
                    loginValid.setText("User Name and Pass must not be empty");
                }
                
                
            }
        });

        Scene scene = new Scene(root, 800, 575);

        userName.setLayoutX(320);
        userName.setLayoutY(140);
        userTextField.setLayoutX(340);
        userTextField.setLayoutY(170);
        pw.setLayoutX(320);
        pw.setLayoutY(230);
        pwBox.setLayoutX(340);
        pwBox.setLayoutY(260);
        hbBtn.setLayoutX(330);
        hbBtn.setLayoutY(330);
        loginValid.setLayoutX(330);
        loginValid.setLayoutY(430);
        
        
        stage.setResizable(false);
        stage.setScene(scene);
        scene.getStylesheets().add(SignUp.class.getResource("css/signup.css").toExternalForm());
        stage.show();
                
        //create scene 
        //call startServerConnection on button btn click
        // handel errors in loginvalid
    }
    
    public void startServerListener(){
       serverListener  = new Thread(){
            public void run(){
                while(true){
                    try {
                        String message = "";
                        message = inStream.readLine();
                        String msg = serverMessageHandler(message);

                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
           
       };
       
       serverListener.start();
    
    }
    
    synchronized public String serverMessageHandler(String msg){
        
        
        String message = msg.split("\\:")[0];
        System.out.println(msg);
        switch(message){    
            case "login":
                //login:name=username,pass=pass
                //login:false
                String replay =  msg.split("\\:")[1];
                return replay;
                
                
            case "signup":{
            
                    //signup:true|false
                replay = msg.split("\\:")[1];
                return replay;
             
                //logout:server
                //logout:name=uername,pass=pass
            
            
            }
                
            case "play":{
                replay = msg.split("\\:")[1];
                return replay;
            
            }
                
            case"online":{
                replay = msg.split("\\:")[1];
                String[] players=replay.split("\\,");
                
                for(int i=0;i<=players.length;i++){
                String[] player=players[i].split("\\-");
                Player p= new Player (player[0],player[1],Boolean.parseBoolean(player[2]),null,null);
                  //  for( i=0;i<=players.length;i++){
                        client.add(p);
                   // }
                 return replay;
            }}
            
            case "update":{
                replay = msg.split("\\:")[1];
                System.err.println("up msg "+msg+"   "+replay);
                for(Player p: client){
                    if(p.userName.equals(replay))                       
                        {
                        p.active=!p.active;
                        System.out.println("Player " + p.userName + "logoed out " + p.active);
                        break;
                        }  
                 }
                //update UI
                return replay;
            }
                
               
                    
            case "record":{
                    replay = msg.split("\\:")[1];
                for(int i=0;i<=records.size();i++){
                    records.add(replay.split("\\,")[i]);
                }
                return replay;
            
            }
                
            case"game":{
                replay = msg.split("\\:")[1];
                String[] gm=replay.split("\\,");
                
                for(int i=0;i<=gm.length;i++){
                String[] game=gm[i].split("\\-");
                Game g= new Game (game[0],game[1],game[2],Integer.parseInt(game[3]));
                games.add(g);
                }
                return replay;
                
            }
            case "logout":{
                
            }
                
                
            default:
                return "Undefined Message";
                }
      
    }
    
    private void runtimeUIUpdates( String errorMsg) { 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                if(errorMsg != null){
                    loginValid.setText(errorMsg);
                    btn.setVisible(true);
                }
                
                if(Client.this.vaildUser){
                    Client.this.createGameStage(userName);
                }else if(Client.this.userSigned){

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
    synchronized public void requestPlay (String name){
        try {
            final String loginMsg = "play:request"+name;
            outStream.println(loginMsg);
            String reply = inStream.readLine();
            String msg = serverMessageHandler(reply);
            if (msg.equals("accepted")){
                Game G=new Game(userName, name);
            }
            if (msg.equals("rejected")){
                //send msg (prompt that reuest reused)
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    synchronized public void getPlayers (){
        try {
            final String loginMsg = "online:players";
            outStream.println(loginMsg);
            String reply = inStream.readLine();
            String msg = serverMessageHandler(reply);
            for(Player p: client){
               //print result in screen
                
            }   
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
 
    
    synchronized public void getRecords (){
        try {
            final String loginMsg = "records:";
            outStream.println(loginMsg);
            String reply = inStream.readLine();
            String msg = serverMessageHandler(reply);
            for(int i=0;i<=records.size();i++){
                records.get(i);//print result in screen
            }    
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    synchronized public void getgames (){
        try {
            final String loginMsg = "games:";
            outStream.println(loginMsg);
            String reply = inStream.readLine();
            String msg = serverMessageHandler(reply);
            for(int i=0;i<=games.size();i++){
                games.get(i);//print result in screen
            }    
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    private void Signup(String userName ,String userPass){
    
        String signUpMsg = "signup:name="+userName+",pass="+userPass;
        
    
    }
       
}
    
    
    
