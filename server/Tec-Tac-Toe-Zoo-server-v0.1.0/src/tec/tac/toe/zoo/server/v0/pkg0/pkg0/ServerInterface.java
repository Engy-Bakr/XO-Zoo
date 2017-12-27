/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tec.tac.toe.zoo.server.v0.pkg0.pkg0;



/**
 *
 * @author U
 *
 */
interface ServerInterface {

    String acceptRequest();
    //receive game request from player1 to ask player2 for game confirmation
    
    String ShakeHand();
    //server reply to player1 request
    
    String updateUserState();
    //update state of user online-offline
   
    String playerList();
    //show  list of players online or offline

    void sendRequestP2();
    //server send requst to player2

    String receiveReply2();
    //receive rply from player2 

    void SendRply();
    //send game confirmation from server to player1

    void DbConnection();
    //establish connection with database
	
    String SignIn();
    //accept sign in information from player

    void SignOut();
    //makes player return to the main page
}
