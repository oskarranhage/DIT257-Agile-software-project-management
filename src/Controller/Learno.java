package Controller;

import View.View;

public class Learno {
    private DataBase db;
    private View commandLineView;

    private Learno(){
        this.db = new DataBase();
        this.commandLineView = new View(this.db);
    }

    public static void main(String[] args) {
        Learno l = new Learno();
        l.commandLineView.startMenu();
    }
}
