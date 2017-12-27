/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tec.tac.toe.zoo.server.v0.pkg0.pkg0;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
    PrintStream out = null;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Button btn = new Button();
        btn.setText("start Server");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    ss = new ServerSocket(p);
                    startSErver();
                    System.out.println("Server Started ");
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            /* while(true){
            try {
                s = ss.accept();
                System.err.println("some one here");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            new MultiUserListener(s);
            
            }*/
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public String acceptRequest() {
       //To change body of generated methods, choose Tools | Templates.
       return "";
    }

    @Override
    public String ShakeHand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateUserState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String playerList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendRequestP2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String receiveReply2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendRply() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DbConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String SignIn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void startSErver(){
        new Thread(){  
            public void run(){
                System.err.println("started");
               System.err.println("run");    
                while(true){
                    System.err.println("lise");    
                try {
                        s =  ss.accept();
                        System.err.println("some one");
                        in = new  DataInputStream(s.getInputStream());
                        out = new PrintStream(s.getOutputStream()); 
                        String r = in.readLine();
                       System.out.println(r);
                       out.println("login:Hi");
                } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

                }
            }    
        }.start();
      
    
    
    
    }
    @Override
    public void SignOut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

class MultiUserListener extends Thread{

    static Vector<MultiUserListener> Players = new Vector<MultiUserListener>();
    Socket clientSocket;
    DataInputStream input = null;
    PrintStream output = null;
    MultiUserListener(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            input = new DataInputStream(clientSocket.getInputStream());
            output = new PrintStream(clientSocket.getOutputStream());
            Players.add(this);
            
            start();
            
        } catch (IOException ex) {
            Logger.getLogger(MultiUserListener.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
}