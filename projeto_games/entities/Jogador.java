package projeto_games.entities;

import java.util.Scanner;

import static java.lang.System.in;

public class Jogador {
    private String nick;
    private int vida;
    private final Navio[][] naviosOnGame = new Navio[10][10];
    //cuida os pontos nos quais foram atirados
    private final int[][] memoria = new int[10][10];
    public Jogador(){
        setNick();
        vida = 0;
    }

    public boolean attVida() {
        vida--;
        return vida>0;

    }

    public int[][] getMemoria() {
        return memoria;
    }

    public String getNick() {
        return nick;
    }

    public void setNick() {
        Scanner sc = new Scanner(in);
        System.out.println("Escolha o apelido para o Player 1:");
        this.nick= sc.nextLine();
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Navio[][] getNaviosOnGame() {
        return naviosOnGame;
    }
}
