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
public class Server extends Application implements ServerInterface{
    ServerSocket ss = null;
    int p =5555;
    Socket s = null;
    String ip = "";
    DataInputStream in = null;
    PrintWriter out = null;
    Thread startThread = null ;
    
    static Vector<LogedPlayer> currentLogedPlayers = new Vector<LogedPlayer>();
    static  DataBaseClass dataBase = null;
    
    public void start(Stage primaryStage) {
    
        Button btn = new Button();
        btn.setText("start Server");
        btn.setOnAction(new EventHandler<ActionEvent>() {            
        @Override
        public void handle(ActionEvent event) {
            try {
                ss = new ServerSocket(p);
                // start background serves which will call accept 

                startServer();
                dataBase = new DataBaseClass("tic_tac_toe_zoo","root","");

                System.out.println("Server Started ");
                btn.setVisible(false);
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
            });
                
        Button stop = new Button();
        stop.setText("stop Server");
        stop.setOnAction(new EventHandler<ActionEvent>(){
            
            public void handle(ActionEvent event) {
                stopSErver();
                System.out.println("Server Stopped ");
            }
        }
                
        );
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
   public void acceptRequest() {
       new Thread(){
           public void run(){
           while(true){
               try {
                   String request = in.readLine();
               } catch (IOException ex) {
                   Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
               }
           }}
           }.start();
       
    }
    
    public String updateUserState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Vector playerList() {
        new Thread(){
            public void run(){
                while(true){
               //     onAndoffPlayers.add(this);
                } 
            }
        }.start();
       // return onAndoffPlayers;
       return null;
    }

    
    public String sendRequestP2() {
        
        String r1 = "you have a game request";
        new Thread(){
           public void run(){
            while(true){   
                
                out.write(r1);
           }}
            
        }.start();
        return r1;
    }

    
    public void receiveReply2() {
        new Thread(){
        public void run(){    
        while(true){
        try {
            String reply2 = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }
    }.start();
    }
    
    
    public String SendRply() {
        String reply = "player2 accepted your request";
        new Thread(){
            public void run(){
            while(true){
            out.write(reply);
                
            }}
        }.start();
        return reply;
    }

    
    public void DbConnection() {

    }


    public void startServer(){
        startThread =new Thread(){  
            public void stopThread(){
            }
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
     
     
    @Override
    public void SignOut() {
        
        new Thread(){
            public void run(){
                try {
                    out.close();
                    in.close();
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
            }
    
    
        }.start();
    }

    
    
    public String ShakeHand() {
        String ShakeMsg = "request delivered";
        new Thread(){
        public void run(){
            while(true){
            out.write(ShakeMsg);
            
            }}
        
        }.start();
       
        return ShakeMsg;
    }

    @Override
    public String SignIn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

class LogedPlayer extends Thread{

    Socket clientSocket;
    DataInputStream input = null;
    PrintStream output = null;
    String userName = null;
    String userPass = null;
    String player = null;
    
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
        while(true){
            try {
                String loginMsg = input.readLine();
                megHendler(loginMsg);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

        
        private String megHendler(String loginMsg) {
            //login:name=sdsdswd,passs=klnkj
            System.out.println("readed message "+loginMsg);
            String replay = loginMsg.split("\\:")[0];
            
            switch(replay){
            
                case "login" : {
                    //login:user=name,pass=pass
                    String msg = loginMsg.split("\\:")[1];
                    System.out.println("1 "+msg);    
                    String temp = msg.split("\\,")[0];
                    System.out.println("2 " +temp);
                    userName = temp.split("\\=")[1];
                    System.out.println("3 "+userName);
                    temp = msg.split("\\,")[1];
                    userPass = temp.split("\\=")[1];
                    System.out.println("3 "+userPass);
                    getPlayerData(userName,userPass);
                    return "true";  
                }
                case "signup":{
                    //request signip:user=name,pass=pass
                    String msg = loginMsg.split("\\:")[1];
                    System.out.println("1 "+msg);
                    String temp = msg.split("\\,")[0];
                    System.out.println("2 " +temp);
                    userName = temp.split("\\=")[1];
                    System.out.println("3 "+userName);
                    temp = msg.split("\\,")[1];
                    userPass = temp.split("\\=")[1];
                    System.out.println("3 "+userPass);
                    insertPlayer(userName,userPass);
                    return "true";
                }

                case "online" :{
                    //
                    String players = getAllPlayers();
                    output.println("online:");
                }
            }
            return "";
        }

        public void stopThread() throws Throwable {
            this.finalize();
        }

        private void insertPlayer(String userName, String userPass) {
           if(dataBase != null){
               Boolean inserted = DataBaseClass.insertPlayer(userName,userPass);
               System.out.println("signup:"+inserted);
               output.println("signup:"+inserted);   
           }
        }

        private void getPlayerData(String userName , String userPass) {
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
        
        private String  getAllPlayers(){
            String players = "";
            if(dataBase != null ){
               if(dataBase != null){
                       ResultSet rs = DataBaseClass.selectPlayers(userName,userPass);
                       String userdata = "";
                       if(rs != null){
                           try {
                               if(rs.next()){
                                   for(int i = 1 ; i < 4;i++){
                                       userdata += rs.getString(i)+",";
                                   }
                                   //check if player is online
                                   userdata += 
                                           checkPlayerOnline(userName) ? "1":"0";
                               }else{
                                   userdata ="false";
                                   System.out.println("no next");
                               }
                           } catch (SQLException ex) {
                               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           System.out.println("all player data "+userdata);
                           output.println("login:"+userdata);
                        } 
                }else{  
                    System.out.println("data base null");
                        //faild to get user data from database
                }
            }
            return players;
        }
        
        private boolean checkPlayerOnline(String name){
            return currentLogedPlayers.indexOf(this) != -1;
        }
        
        
    }



}

