/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.zoo.client.v0.pkg0.pkg1;

/**
 *
 * @author lw
 */
  class Game {
    String firstPlayer ;
    String seconedPlayer ;
    String winnerPlayer ;
    int requestId;
<<<<<<< HEAD
    
    public Game(String first, String seconed)
    {
        firstPlayer=first;
        seconedPlayer=seconed;
    }
=======

>>>>>>> sara
   
    public Game(String first, String seconed, String winner, int request)
        {
            firstPlayer=first;
            seconedPlayer=seconed;
            winnerPlayer=winner;
            requestId=request;
        }
    }

