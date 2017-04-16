package V3;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Simon on 13.04.2017.
 */
public class TestTTT {
    public static void main(String[] args) {
        Random r = new Random();
        int currentPlayer =  r.nextInt(2);
        TicTacToe ttt = new TicTacToe();
        String  num;
        Scanner sc = new Scanner(System.in);
        printStartDialog();
        Pattern patter = Pattern.compile("\\d");
       if (currentPlayer==0) System.out.println("Cross-Player begins!");
       else{
           System.out.println("Circle-player begins!");
       }

        while (!(num = sc.next()).equalsIgnoreCase("exit")){
            if (num.equalsIgnoreCase("map")) {
                ttt.printMap();
                continue;
            }
            else if ( !(patter.matcher(num).matches())){
                System.out.println("Sadly this is not a correct field. Please try again =)");
                continue;
            }

            if (currentPlayer == 0){
                ttt.markCross(Integer.parseInt(num));
                currentPlayer = 1;
                System.out.println("Circle-players's turn");
            }
            else{
                System.out.println("Cross-players's turn");
                ttt.markCircle(Integer.parseInt(num));
                currentPlayer = 0;
            }
            ttt.printMap();
            int gameSituation = ttt.isWinner();
            if (gameSituation>-1){
                if (gameSituation==1){
                    System.out.println("Cross-Player Won!");
                }
                else{
                    System.out.println("Cirlce-Player Won!");
                }
                ttt.resetGame();
            }
        }
    }

    static void printStartDialog(){
        System.out.println(" __     __    _");
        System.out.println("/ / /\\ \\ \\___| | ___ ___  _ __ ___   ___");
        System.out.println("\\ \\/  \\/ / _ \\ |/ __/ _ \\| '_`  _ \\ / _ \\");
        System.out.println(" \\  /\\  /  __/ | (_| (_) | | | | | |  __/");
        System.out.println("  \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|");


        System.out.println("This is the TicTacToe Game");
        System.out.println("There are two Players, each one has to set his mark on the field.\nThe first who has 3 symbols in a row wins.");
        System.out.println("To place a mark type the number of the field into the console");

        for (int j = 0; j < 9; j++) {
            System.out.print("|"+j);
            if (j == 2 || j == 5) System.out.printf("|%n");
        }
        System.out.println("|\nIf you want to end the game type \"exit\"");
    }
}
