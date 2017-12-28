/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tec.tac.toe.zoo.server.v0.pkg0.pkg0;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.print.attribute.standard.Severity;
import static tec.tac.toe.zoo.server.v0.pkg0.pkg0.DataBaseClass.rs;

/**
 *
 * @author Omnia
 */
public class Server extends Application {
    ServerSocket ss = null;
    int p =5555;
    Socket s = null;
    String ip = "";
    DataInputStream in = null;
    PrintWriter out = null;
    Thread startThread = null ;
    
    static Vector<LogedPlayer> currentLogedPlayers = new Vector<LogedPlayer>();
    static  DataBaseClass dataBase = null;
    Button stop ;
     Button start ;
    public void start(Stage primaryStage) {
    
        start = new Button();
        start.setText("start Server");
        start.setOnAction(new EventHandler<ActionEvent>() {            
        @Override
        public void handle(ActionEvent event) {
            try {
                ss = new ServerSocket(p);
                // start background serves which will call accept 
                startServer();
<<<<<<< HEAD
                dataBase = new DataBaseClass("tic-ttac-tooe","root","");
                System.out.println("Server Started ");
                start.setVisible(false);
                
=======
                dataBase = new DataBaseClass("tic-tac-tooe","root","");
                System.out.println("Server Started ");
                start.setVisible(false);
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
            });
                
         stop = new Button();
        stop.setText("stop Server");
        stop.setOnAction(new EventHandler<ActionEvent>(){
            
            public void handle(ActionEvent event) {
                stopSErver();
                System.out.println("Server Stopped ");
            }
        }
                
        );
        
        StackPane root = new StackPane();
        root.getChildren().add(start);
<<<<<<< HEAD
        Scene scene = new Scene(root, 300, 250);
=======
        
        Scene scene = new Scene(root, 300, 250);
        
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void startServer(){
        startThread =new Thread(){  
            public void run(){
               System.err.println("started");
               System.err.println("run");    
                while(true){
                    System.err.println("lise");    
                try {
                        s =  ss.accept();
                        System.err.println("some one");
                        new LogedPlayer(s);
                } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

                }
            }
               
        };
      
        startThread.start();
    }
    
    public void stopSErver(){
         //send message for each player that server closed 
         // and then clothe its streams, sockets and threads
         //slose server thread that accept sockets
         for(LogedPlayer i : currentLogedPlayers){
             try {
                 i.output.println("logout:server");
                 i.input.close();
                 i.output.close();
                 i.clientSocket.close();
                 //why it reads stopThread although it is private
                 i.stopThread();
             }
             
             catch (IOException ex) {
                 Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Throwable ex) {
                 Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         startThread.stop();
    }
    
class LogedPlayer extends Thread{

    Socket clientSocket;
    DataInputStream input = null;
    PrintStream output = null;
    String userName = null;
    String userPass = null;
    String player = null;
    boolean running =true;
    
        LogedPlayer(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            input = new DataInputStream(clientSocket.getInputStream());
            output = new PrintStream(clientSocket.getOutputStream());
            Server.currentLogedPlayers.add(this);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(LogedPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
        public void run(){
            while(running){
                try {
                    System.out.println("liss ....");
                    String loginMsg = input.readLine();
                    msgHendler(loginMsg);
                } catch (IOException ex) {
<<<<<<< HEAD
                   // Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    stopThread();
                    running = false;
=======
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                }
            }
        }
        
        public void stopThread(){ 
            try {
                this.input.close();
                this.output.close();
                this.clientSocket.close();
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private String msgHendler(String message) {
<<<<<<< HEAD
            System.out.println("msss "+message);
=======
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
            String replay = message.split("\\:")[0];
            switch(replay){
                case "login" : {
                    //login:user=name,pass=pass
                    String msg = message.split("\\:")[1];
                    String temp = msg.split("\\,")[0];
                    userName = temp.split("\\=")[1];
                    temp = msg.split("\\,")[1];
                    userPass = temp.split("\\=")[1];
                    getPlayerData(userName,userPass);
                    break;
                }
                case "online" :{
                    String players = getAllPlayers();
                    System.out.println("online:"+players);
                    output.println("online:"+players);
                    break;
                }
                case "signup":{
                    //request signip:user=name,pass=pass
                    String msg = message.split("\\:")[1];
                    String temp = msg.split("\\,")[0];
                    userName = temp.split("\\=")[1];
                    temp = msg.split("\\,")[1];
                    userPass = temp.split("\\=")[1];
                    insertPlayer(userName,userPass);
                    break;
                }
                case "logout":{
                      //logout:name,pass
                    String msg = message.split("\\:")[1];
                    String name = msg.split("\\,")[0];
                    String pass = msg.split("\\,")[1];
                    this.logOut(); 
                    break;
                }
<<<<<<< HEAD
                case "play":{
                    String playWith = message.split("\\:")[1];
                    sendRequest(playWith);
                    break;
                }
=======
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                default:
                    System.out.println(message);          
            }
            return "";
        }

        synchronized private void insertPlayer(String userName, String userPass) {
           if(dataBase != null){
               Boolean inserted = DataBaseClass.insertPlayer(userName,userPass);
               System.out.println("signup:"+inserted);
               output.println("signup:"+inserted);   
           }
        }

        synchronized private void getPlayerData(String userName , String userPass) {
            /*String loginMsg = input.readLine();
            megHendler(loginMsg);
            */
            if(userName != null && userPass != null){
                if(dataBase != null){
                       ResultSet rs = DataBaseClass.selectPlayers(userName,userPass);
                       String userdata = "";
                       if(rs != null){
                           try {
                               if(rs.next()){
                                   for(int i = 1 ; i < 4;i++){
                                       userdata += rs.getString(i)+",";
                                   }
                                   userdata +="1";//current user is active
                               }else{
                                   userdata ="false";
                                   System.out.println("no next");
                               }
                           } catch (SQLException ex) {
                               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           output.println("login:"+userdata);
                           updatePlayerStatus();
                        } 
                }else{  
                    System.out.println("data base null");
                        //faild to get user data from database
                }
                
            }else{
                System.out.println("fail to get data from client message");
                //faild to get User name and PAss
            }
      
        }
        
        synchronized private String  getAllPlayers(){
            String players = "";
<<<<<<< HEAD
               if(dataBase != null){
                       ResultSet rs = DataBaseClass.selectPlayers(null,null);
                       String userdata = "";
                       if(rs != null){
                           try {
                               while (rs.next()) { 
                                   userdata = "";
                                   for(int i = 1 ; i < 4;i++){
                                       if(i != 2)
=======
            if(dataBase != null ){
               if(dataBase != null){
                       ResultSet rs = DataBaseClass.selectPlayers(userName,userPass);
                       String userdata = "";
                       if(rs != null){
                           try {
                               if(rs.next()){
                                   for(int i = 1 ; i < 4;i++){
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                                       userdata += rs.getString(i)+"-";
                                   }
                                   //check if player is online
                                   userdata += 
                                           checkPlayerOnline(userName) ? "1":"0";
                                   players +=userdata+",";
<<<<<<< HEAD
                                   System.out.println(userdata);
=======
                               }else{
                                   userdata ="false";
                                   System.out.println("no next");
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                               }
                           } catch (SQLException ex) {
                               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                           }
<<<<<<< HEAD
=======
                           System.out.println("all player data "+userdata);
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                        } 
                }else{  
                    System.out.println("data base null");
                        //faild to get user data from database
                }
<<<<<<< HEAD
            
=======
            }
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
            return players;
        }
        
        synchronized private boolean checkPlayerOnline(String name){
            return currentLogedPlayers.indexOf(this) != -1;
        }

        synchronized private void logOut(){
<<<<<<< HEAD
            //logout this current player 
            //close thread
=======
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
            if(Server.currentLogedPlayers.remove(this)){
                System.out.println("removed : "+userName);
                running = false;
            };
            updatePlayerStatus();
            stopThread();
        }
        
        synchronized private void updatePlayerStatus(){
<<<<<<< HEAD
            //send message for all player 
            //that user userName is loged out 
            for(LogedPlayer i : Server.currentLogedPlayers){
                    if(i.userName != this.userName){
                        System.err.println("message to : " + i.userName);
=======
            for(LogedPlayer i : Server.currentLogedPlayers){
                    if(i.userName != this.userName){
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
                        i.output.println("update:"+userName);
                    }
                }
        }
<<<<<<< HEAD

        private void sendRequest(String playWith) {
            for(LogedPlayer i : currentLogedPlayers){
                
                  if(i.userName.equals(playWith)){
                      try {
                          i.output.println("play:request");
                          String requestResonce = i.input.readLine();
                      } catch (IOException ex) {
                          Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }
            
            }
        }
=======
>>>>>>> cc018a513287e760d7c47df297568773b10ae8d8
        
    }



}

