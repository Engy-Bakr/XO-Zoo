
import java.util.Scanner;
import java.util.Vector;



public class TicTacToe
{
	// Grid variables
	//    0 for an empty square
	//    1 if the square contains X
	//    2 if the square contains O
	static int a1, a2, a3, a4, a5, a6, a7, a8, a9;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
            
           
            String choice="";
       

		// There are a maximum of nine plays, so a for loop keeps track of
		// the number of plays. The game is over after the ninth play.
		// Each time through the loop, both the human and the computer play.
		// So i is incremented in the body of the loop after the computer plays.
            // game between player and computer    
        
            System.out.println("Do you want to play with computer ? (yes/no)");
            choice=sc.nextLine();
            checkValidInput(choice);
            
        }    
            
        public static String getMove(String move)
            {
		String play;
		System.out.print(move);
		do
		{
                    play = sc.nextLine();
                    if (!isValidPlay(play))
			{
				System.out.println("That is not a valid play.");
			}
		} while (!isValidPlay(play));
		return play;
	
            }
        
        

	public static boolean isValidPlay(String play)
	{
            if (play.equalsIgnoreCase("a1") & a1 == 0)
		return true;
            if (play.equalsIgnoreCase("a2") & a2 == 0)
		return true;
            if (play.equalsIgnoreCase("a3") & a3 == 0)
		return true;
            if (play.equalsIgnoreCase("a4") & a4 == 0)
		return true;
            if (play.equalsIgnoreCase("a5") & a5 == 0)
		return true;
            if (play.equalsIgnoreCase("a6") & a6 == 0)
		return true;
            if (play.equalsIgnoreCase("a7") & a7 == 0)
		return true;
            if (play.equalsIgnoreCase("a8") & a8 == 0)
		return true;
            if (play.equalsIgnoreCase("a9") & a9 == 0)
		return true;
		return false;
	}

	/*public static void updateBoard(String play, int player)
	{
            if (play.equalsIgnoreCase("a1"))
		a1 = player;
            if (play.equalsIgnoreCase("a2"))
		a2 = player;
            if (play.equalsIgnoreCase("a3"))
		a3 = player;
            if (play.equalsIgnoreCase("a4"))
		a4 = player;
            if (play.equalsIgnoreCase("a5"))
		a5 = player;
            if (play.equalsIgnoreCase("a6"))
		a6 = player;
            if (play.equalsIgnoreCase("a7"))
		a7 = player;
            if (play.equalsIgnoreCase("a8"))
		a8 = player;
            if (play.equalsIgnoreCase("a9"))
		a9 = player;
	}*/
        public static void a1(String play,int player)
        {
            if (play.equalsIgnoreCase("a1"))
		a1 = player;
        }
        public static void a2(String play,int player)
        {
            if (play.equalsIgnoreCase("a2"))
		a2 = player;
        }
        public static void a3(String play,int player)
        {
            if (play.equalsIgnoreCase("a3"))
		a3 = player;
        }
        public static void a4(String play,int player)
        {
            if (play.equalsIgnoreCase("a4"))
		a4 = player;
        }
        public static void a5(String play,int player)
        {
            if (play.equalsIgnoreCase("a5"))
		a5 = player;
        }
        public static void a6(String play,int player)
        {
            if (play.equalsIgnoreCase("a6"))
		a6 = player;
        }
        public static void a7(String play,int player)
        {
            if (play.equalsIgnoreCase("a7"))
		a7 = player;
        }
        public static void a8(String play,int player)
        {
            if (play.equalsIgnoreCase("a8"))
		a8 = player;
        }
        public static void a9(String play,int player)
        {
            if (play.equalsIgnoreCase("a9"))
		a9 = player;
        }
         
         
        
        

	public static void displayBoard()
	{
            String line = "";
            System.out.println();
            line = " " + getXO(a1) + " | " + getXO(a2) + " | " + getXO(a3);
            System.out.println(line);
            System.out.println("-----------");
            line = " " + getXO(a4) + " | " + getXO(a5) + " | " + getXO(a6);
            System.out.println(line);
            System.out.println("-----------");
            line = " " + getXO(a7) + " | " + getXO(a8) + " | " + getXO(a9);
            System.out.println(line);
            System.out.println();
	}

	public static String getXO(int square)
	{
            if (square == 1)
		return "X";
            if (square == 2)
		return "O";
		return " ";
	}

	public static String getComputerMove()
	{
            if (a1 == 0)
		return "a1";
            if (a2 == 0)
		return "a2";
            if (a3 == 0)
		return  "a3";
            if (a4 == 0)
		return  "a4";
            if (a5 == 0)
		return  "a5";
            if (a6 == 0)
		return  "a6";
            if (a7 == 0)
		return  "a7";
            if (a8 == 0)
		return  "a8";
            if (a9 == 0)
		return  "a9";
		return "";
	}

	public static boolean isGameWon()
	{
            if (isRowWon(a1, a2, a3))
		return true;
            if (isRowWon(a4, a5, a6))
		return true;
            if (isRowWon(a7, a8, a9))
		return true;
            if (isRowWon(a1, a4, a7))
		return true;
            if (isRowWon(a2, a5, a8))
		return true;
            if (isRowWon(a3, a6, a9))
		return true;
            if (isRowWon(a1, a5, a9))
		return true;
            if (isRowWon(a3, a5, a9))
		return true;
		return false;
	}

	public static boolean isRowWon(int a, int b, int c)
	{
            return ((a == b) & (a == c) & (a != 0));
	}
        
        
        public static void checkValidInput(String c){
        
            String welcomeMsgP1 = "Welcome to Our Tic-Tac-Toe, Player1! Please enter your move: ";
            String welcomeMsgP2 = "welcome to Our Tic-Tac-Toe, Player2! Please enter your move: ";
            String player1Move = "";
            String player2Move = "";
            String computerMove = "";
            boolean gameIsWon = false;
            
            switch(c.toLowerCase()){
                
                case "yes":
                
                    for (int i = 1; i <=9; i++)
                    {
			// player1
                        player1Move = getMove(welcomeMsgP1);
                        //updateBoard(player1Move, 1);
                        a1(player1Move, 1);
                        a2(player1Move, 1);
                        a3(player1Move, 1);
                        a4(player1Move, 1);
                        a5(player1Move, 1);
                        a6(player1Move, 1);
                        a7(player1Move, 1);
                        a8(player1Move, 1);
                        a9(player1Move, 1);
                        
                        displayBoard();
                        //Record("player1",player1Move);
                        if (isGameWon())
                            {
                                System.out.println("You won!");
                                gameIsWon = true;
                                break;
                            }
			// Computer player
                        if (i < 9)
                            {
                                computerMove = getComputerMove();
                                //System.out.println("I will play at " + computerMove);
                                //updateBoard(computerMove, 2);
                                
                                a1(computerMove, 2);
                                a2(computerMove, 2);
                                a3(computerMove, 2);
                                a4(computerMove, 2);
                                a5(computerMove, 2);
                                a6(computerMove, 2);
                                a7(computerMove, 2);
                                a8(computerMove, 2);
                                a9(computerMove, 2);
                                
                                displayBoard();
                                //Record("computer",computerMove);
                                
                                if (isGameWon())
                                {
                                    System.out.println("I won!");
                                    gameIsWon = true;
                                    break;
                                }
                                welcomeMsgP1 = "Enter your next move: ";
                                i++;
                            }
                    }
                    if (!gameIsWon)
			System.out.println("It's a draw!");
                break;
                
                
                
                case "no":
                
                    //2-player game
                    for (int i = 1; i <=9; i++)
                    {
			// player1
                	player1Move = getMove(welcomeMsgP1);
			//updateBoard(player1Move, 1);
                        
                        a1(player1Move, 1);
                        a2(player1Move, 1);
                        a3(player1Move, 1);
                        a4(player1Move, 1);
                        a5(player1Move, 1);
                        a6(player1Move, 1);
                        a7(player1Move, 1);
                        a8(player1Move, 1);
                        a9(player1Move, 1);
                        
			displayBoard();
                        //Record("player1",player1Move);
                        
			if (isGameWon())
			{
				System.out.println("You won!");
				gameIsWon = true;
				break;
			}
                        
			// player2
			if (i < 9)
			{
                            player2Move = getMove(welcomeMsgP2);
                            //System.out.println("I will play at " + computerMove);
                            //updateBoard(player2Move, 2);
                            
                            a1(player2Move, 2);
                            a2(player2Move, 2);
                            a3(player2Move, 2);
                            a4(player2Move, 2);
                            a5(player2Move, 2);
                            a6(player2Move, 2);
                            a7(player2Move, 2);
                            a8(player2Move, 2);
                            a9(player2Move, 2);
                            
                            displayBoard();
                            //Record("player2",player2Move);
                            
                            if (isGameWon())
				{
                                    System.out.println("I won!");
                                    gameIsWon = true;
                                    break;
				}
                            welcomeMsgP1 = "Enter your next move, player1 ";
                            i++;
			}
                        welcomeMsgP2="Enter your next move, player2";
                    }
                    if (!gameIsWon)
			System.out.println("It's a draw!");
                break;
                        
                default:
                    System.out.println("invalid choice, please enter yes or no!");
                    c = sc.nextLine();
                    checkValidInput(c);
                    break;
                 
            }
        }
        
        public static String[] Record (String player, String nextMove){
            
            String gameRecord[]=null;
            new Thread(){
                public void run(){
                    while(true){
                    String playRecord = "";
                    playRecord.concat(player + " ").concat(nextMove + ",");
                    String gameRecord[] = playRecord.split(",");
                    
                }
            }
            }.start();       
            return gameRecord;
        }  
                
}

