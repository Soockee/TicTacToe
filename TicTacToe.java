package V3;

/**
 * Created by Simon on 12.04.2017.
 */
public class TicTacToe {
    int map = 0b00_00_00_00_00_00_00_00_00; //8_7_6_5_4_3_2_1_0 //8x00
    // <<step per field access mask+=3 -> first two bits 10 = 0 ; 01 = x ; 00->"="
    final byte STEP = 2; // 0b00000011;
    final int FIELDELEMENTS = 9; //0-8 = 9 elements
    final int SIZE = 3;
    int moves = 0; //there can't be a winner before the 5th move
    //marking methods are not able to handle overwriting of existing chars
    void markCross(int idx) {
        int mask = 1;
        for (int i = 0; i < idx; i++) {
            mask <<= STEP;
        }
        map = map | mask;
        moves++;
    }

    void markCircle(int idx) {
        int mask = 2;
        for (int i = 0; i < idx; i++) {
            mask <<= STEP;
        }
        map = map | mask;
        moves++;
    }

    void printMap() {
        int temp = map;
        char[] field = new char[9];
        for (int mask = 0b11, i = 0; i < field.length; mask <<= STEP, i++) {
            temp = map;
            temp = temp & mask;
            int k = binlog(temp);
            //Falls binlog -1 -> leeres Feld, weil temp = leer. falls binlog%2 (also gerade binärstelle bzw ungerader integer) -> 'x' in allen anderen fällen (ungerade binärstelle bzw gerade zahl)--> 'O'
            if (k == -1) {
                field[i] = '=';
            }
            else {
                if (k % 2 == 0) {
                    field[i] = 'x';
                } else {
                    field[i] = 'O';
                }
            }
        }
        for (int j = 0; j < field.length; j++) {
            System.out.print(field[j] + " ");
            if (j == 2 || j == 5) System.out.println();
        }
        System.out.printf("%n");
    }

    public int binlog(int bits) {
        int log = 0;
        if (bits == 0) {
            return -1;
        }
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        } // Wenn der übergebene Parameter 16 0-Bit stellen(links->rechts) hat: Verschiebe diese 16 Bits ins Nirvana und hole die forderen nach, ausserdem setzte log = 16
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        } //>>> bedeutet unsigned shift--> kann zu vorzeichen fehlern kommen bei einem overflow
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        } //Es werden vorlaufend bitstellen abgeschnitten und dem log die abgeschnittenen stellen hinzuaddiert
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1); //
    }
    public void resetGame(){
        map = 0b00_00_00_00_00_00_00_00_00;
        moves = 0;
    }
    // -1 -> no winner yet ; 0 = 'O' won ; 1 = 'x' won
    int isWinner() {
        if (moves<5)return-1;
        int[] start = {0, 3, 6, 0, 1, 2, 0, 2};
        int[] increment = {1, 1, 1, 3, 3, 3, 4, 2};
        int mask = 0b11;
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < FIELDELEMENTS -1; i++) {
            for (int j = 0; j < SIZE; j++) {
                mask = 0b11;
                int k = start[i] + j * increment[i];
                for (int o = 0; o < k; o++) {
                    mask <<= STEP;
                }

                int temp = map & mask;
                int binPos = binlog(temp);

                if (binPos%2==1){
                    countO++;
                }
                else if(binPos%2==0){
                    countX++;
                }
            }
            if (countX == 3) {
                return 1;
            }
            else if(countO == 3){
                return 0;
            }
            countO = 0;
            countX = 0;
        }
        if (moves == 9 ){
            draw();
        }
        return -1;
    }

    void draw(){
        System.out.println("This is a draw! Refreshing the Battelground!");
        resetGame();
        printMap();
    }
}
