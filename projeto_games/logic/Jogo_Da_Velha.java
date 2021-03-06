package projeto_games.logic;


import projeto_games.Main.Game;

import java.util.Arrays;

import static java.lang.System.out;

public class Jogo_Da_Velha extends Game {
    private final char[][] tabuleiro;
    public Jogo_Da_Velha(){
        tabuleiro = new char[3][3];
        Arrays.fill(tabuleiro, ' ');
        gameStart();
    }
    public char verificar() {
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0] == tabuleiro[i][1]) &&
                    (tabuleiro[i][0] == tabuleiro[i][2])) {
                if (tabuleiro[i][0] != 0) {
                    return tabuleiro[i][0];
                }
            }
        }
        for (int j = 0; j < 3; j++) {
            if ((tabuleiro[0][j] == tabuleiro[1][j]) &&
                    (tabuleiro[0][j] == tabuleiro[2][j]) &&
                    (tabuleiro[0][j] != 0)) {
                return tabuleiro[0][j];
            }
        }
        if ((tabuleiro[0][0] == tabuleiro[1][1]) &&
                (tabuleiro[0][0] == tabuleiro[2][2]) &&
                (tabuleiro[0][0] != 0)) {
            return tabuleiro[0][0];
        }
        if ((tabuleiro[0][2] == tabuleiro[1][1]) &&
                (tabuleiro[0][2] == tabuleiro[2][0]) &&
                (tabuleiro[0][2] != 0)) {
            return tabuleiro[0][2];
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return ' ';
                }
            }
        }
        return 'E';
    }

    private static void drawBoard(char[][] posicao) {
//		out.print("  ");
//		for (int j = 0; j <posicao.length; j++) {
//			out.print("   "+j+"  "); imprimiria o numero das colunas
//		}
        out.println();
        for (int i = 0; i < posicao.length+1; i++) {

            for (int j = 0; j < posicao.length*6+1; j++) {
                out.print("-");
            }

            out.println();
            if(i<posicao.length) {
//				out.print(i+" "); imprimiria os numeros das linhas
                for (int j = 0; j < posicao.length+1; j++) {
                    if(j<posicao.length) {
                        out.print("|  "+posicao[i][j]+"  ");

                    }

                }
                out.println("|");

            }
        }
        out.println("\n");

    }


    boolean efetuarJogada(char jogador, int linha, int coluna) {
        if ((linha < 0) || (linha > 2)) {
            return false;
        }
        if ((coluna < 0) || (coluna > 2)) {
            return false;
        }
        if (tabuleiro[linha][coluna] != ' ') {
            return false;
        }
        tabuleiro[linha][coluna] = jogador;
        return true;
    }

    public void gameStart() {
        char jogador = 'X';
        char vencedor = verificar();
        drawBoard(tabuleiro);
        while (vencedor == ' ') {
            imprimir("Jogador " + jogador);
            int[] jogada = new int[2];
            lineSelect(jogada);
            columSelect(jogada);
            if (!efetuarJogada(jogador, jogada[0]-1, jogada[1]-1)) {
                imprimir("Jogada inválida...");
            } else {
                jogador = (jogador == 'X') ? 'O' : 'X';
            }
            drawBoard(tabuleiro);
            vencedor = verificar();
        }
        gameEnd(String.valueOf(vencedor));
    }

}