/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.zoo.client.v0.pkg0.pkg0;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    
    
    //player Data
    Stage stage ;
    
    
    //network Data
    public static final int port = 5005;
    public static final String ip = "127.0.0.1";
    Socket socket = null;
    DataInputStream inStream = null;
    PrintStream outStream = null ;
    Boolean serverAvilability = false;
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
    
    @Override
    public void start(Stage primaryStage) throws SocketException {
        stage = primaryStage;
        stage.setTitle("Tic-Tac-Toe-Zoo!");
        stage.setScene(createLoginScene());
        stage.show();
        
        /*scene.getStylesheets().add(getClass()
                .getResource("../style/login.css").toString());
        */
        
    }

    public void startServerConnection(){
          try {
            socket = new Socket(ip,port);
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new PrintStream(socket.getOutputStream());
            serverAvilability = true;
            
        }catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              System.err.println("connection stablished");
          }
    }
    
    public Scene createLoginScene(){
    
        //create Login view
        
        FlowPane rigestPane = new FlowPane();
        
        
        Label userNameLable= new Label("User Name ");
        TextField userNameField = new TextField();
        
        
        Label userPassLable = new Label("User PassWord ");
        TextField userPassField = new TextField();
   
        
        rigestPane.getChildren().add(userNameLable);
        rigestPane.getChildren().add(userNameField);
        
        rigestPane.getChildren().add(userPassLable);
        rigestPane.getChildren().add(userPassField);
        
        
        
        Button btn = new Button("Login");
        rigestPane.getChildren().add(btn);
        
         Label loginValid = new Label();
         rigestPane.getChildren().add(loginValid);
        
         btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {   
                //start server Connection when user Clicke
               startServerConnection();
                if(serverAvilability){
                    
                    String userName = userNameField.getText();
                    String userPass = userPassField.getText();
        
                    System.out.println(userName+userPass);
                    //if server Connected star login 
                    if(!(userName.equals("") && userPass.equals(""))){
                        startServerConnection();
                        login(userName,userPass);
                    }else{
                        
                         loginValid.setText("Enter UserName ANd Pass");
                    }
                   
                }else{
                    
                    loginValid.setText("Fail To Login");
                }
            }
        
        });
        
        Scene scene = new Scene(rigestPane, 300, 250);
       return scene;
    }
   
    public Scene createGameStage(){
    
          Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        return scene;
    }
    
    
    public void dynamicIp(){
    /*
               Enumeration e = NetworkInterface.getNetworkInterfaces();
            
            while(e.hasMoreElements())
            {
                 
                NetworkInterface n = (NetworkInterface) e.nextElement();
                 System.out.println(n.getName());
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostName()+" "+i.getHostAddress());
                }
            }
           
            
            
            
            try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
            
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
     
    
    }
    
    public void startServerListener(){
    
       new Thread(){
       
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
    
    }
    public String serverMessageHandler(String message){
   
        String cal = message.split("\\:")[0];
        if(cal.equals("login")){
            String replay =  message.split("\\:")[1];
            return replay;
        }
        
        return "";
    }
    public void login(String userName , String pass){
      
        
            System.out.println("ktabna");
            final String loginMsg = "login:name="+userName+",pass="+pass;
             outStream.println(loginMsg);
}
       /* try {
            String reply = inStream.readLine();
            String  user= serverMessageHandler(reply);
            if(user.equals("true")){
                System.out.println("User Loged");
                /*
                Scene s = createGameStage();
                Stage t =new Stage();
                t.setScene(s);
                t.show();
                stage.close();
                stage = t;
                
                startServerListener();
            }else{
             System.out.println("User Not Found");
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
       
    }
    
    
    
