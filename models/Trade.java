package models;

public class Trade {
    int id;
    int amount;
    Player player1;
    Player player2;
    Trade(int id, int amount, Player player1, Player player2) {
        this.id = id;
        this.amount = amount;
        this.player1 = player1;
        this.player2 = player2;
    }
}
