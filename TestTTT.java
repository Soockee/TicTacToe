/**
 * Created by Simon on 26.04.2017.
 */
public class test {
    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();

        t.set.forEach(System.out::println);
        t.generateMoves();
        System.out.println("nodes : " + t.node + "leafs : " +t.leaf + " gesamt: " + (t.node+t.leaf));
    }
}
