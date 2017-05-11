import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by Simon on 19.04.2017.
 */
public class TicTacToe {
    //Board:
    //012
    //345
    //678

    //246_048_258_147_036_876_543_321; boards[0] = 'x' boards[1] = 'o'
    int[] boards;
    int bitfilter = 0b001_001_001_001_001_001_001_001;
    int bitfilterForBoard = 0b111_111_111;
    int moveCounter = 0;
    int node = 0;
    int leaf = 0;
    Stack<Integer> history = new Stack<>();
    Collection<Integer> set = new HashSet<>();

    int[] bitpattern = {
            0b000_001_000_000_001_000_000_001,
            0b000_000_000_001_000_000_000_010,
            0b001_000_001_000_000_000_000_100,
            0b000_000_000_000_010_000_001_000,
            0b010_010_000_010_000_000_010_000,
            0b000_000_010_000_000_000_100_000,
            0b100_000_000_000_100_001_000_000,
            0b000_000_000_100_000_010_000_000,
            0b000_100_100_000_000_100_000_000
    };

    public TicTacToe(){
        boards = new int[2];
        boards[0] = 0b000_000_000_000_000_000_000_000;
        boards[1] = 0b000_000_000_000_000_000_000_000;

    }


    public boolean isWin() {
        if (moveCounter < 5) return false;
        int currentBoard = boards[moveCounter-1 & 1];
        return ((currentBoard & (currentBoard >> 1) & (currentBoard >> 2)) & bitfilter) > 0;
    }

    public void makeMove(int move) {
        history.push(boards[moveCounter&1]);
        boards[moveCounter&1] |= bitpattern[move];
        moveCounter++;
    }
    public void makeMove(int...move){
        for(int i : move){
            makeMove(move);
        }
    }

    public ArrayList<Integer> moves(){
        ArrayList<Integer> lst = new ArrayList<>();
        int res = ~((boards[0]&bitfilterForBoard) | (boards[1] & bitfilterForBoard));
        for (int i = 0; i<=8 ; i++) {
            if (((res >> i) & 1) ==1) {
                lst.add(i);
            }
        }
        return lst;
    }
    public void checkSymmetrie(){
            //nothing here yet
    }
    public void undoMove(){
        moveCounter--;
        boards[moveCounter&1] = history.pop();
    }
    public int getCurrentPlayer(){
        // x starts
        //-1 = 'x' ; 1 = 'o';
        int res =  moveCounter&1;
        if (res==0) res = -1;
        return res;
    }
    public int boardToHash(){
        //bitfilterForBoard: Damit sind die ersten 8 Bit der Boardrepresäntation gemeint, also von 0 (oben links) bis 8 (unten rechts)
        int board0ToHash = (boards[0]&bitfilterForBoard)<<9;
        int board1ToHash = (boards[1]&bitfilterForBoard);
        return (board0ToHash|board1ToHash);
    }
    public void generateMoves(){

        for (int i : moves()){
            makeMove(i);
            if (!(set.add(boardToHash()))){
                undoMove();
                continue;
            }
            if (isWin() || moveCounter==9){
                leaf++;
                undoMove();
                continue;

            }
            node++;
            generateMoves();
            undoMove();
        }
    }

    //not finished yet
    public int minimax(int node){
        int bestValue;
        makeMove(node);
        if (isWin()){
            undoMove();
            return 1*getCurrentPlayer();
        }
        if (getCurrentPlayer()==1){
            bestValue = Integer.MIN_VALUE;
            for (int i : moves()){
                int v = minimax(i);
                bestValue = Math.max(bestValue,v);
            }
        }
        else { /*minimizing player*/
            bestValue = Integer.MIN_VALUE;
            for (int i : moves()){
                int v = minimax(i);
                bestValue = Math.min(bestValue,v);
            }
        }
        undoMove();
        return bestValue;
    }
    //expecting 9 bits example:  0b000_000_000
    //not finished yet
    public int flip(int board){
        return 0;
    }

    @Override
    public String toString(){
        char[] board = new char[9];
        int boardX = boards[0] & bitfilterForBoard;
        int boardO = boards[1] & bitfilterForBoard;
        for (int i = 0; i < 9; i++) {
            if (((boards[0] >> i)&1) > 0) board[i] = 'x';
            else if (((boards[1] >> i)&1) > 0) board[i] = 'o';
            else {
                board[i] = '=';
            }
        }
        StringBuilder sb = new StringBuilder();
        char[] field = board;
        for (int j = 0; j < field.length; j++) {
            sb.append(field[j] + " ");
            if (j == 2 || j == 5) sb.append("\n");
        }
        sb.append("\n");
        return "\n"+sb.toString();
    }
}
