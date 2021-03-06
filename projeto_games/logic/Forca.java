package projeto_games.logic;

import projeto_games.Main.Game;

import java.util.Scanner;

public class Forca extends Game {

    static Scanner ed;


    public void gameStart() {
        ed = new Scanner(System.in);
        // Método para iniciar o jogo
        int erro = 0;
        String palavraTentada = "", letras = "";
        String palavra = palavraOculta();
        desenho(erro);
        sublin(palavra);
        while (true) {
            char letra = letra();
            if (letras.indexOf(letra) >= 0) {

                imprimir("Você já tentou essa letra!");

            } else {
                letras += letra;
                if (palavra.indexOf(letra) >= 0) {
                    imprimir("Você acertou!");
                    desenho(erro);

                    palavraTentada = attPalavraTentada(palavra, letras);

                    imprimir(palavraTentada);

                    String teste = "";
                    for (int i = 0; i < palavraTentada.length(); i++) {
                        if (palavraTentada.charAt(i) != ' ') {
                            teste += palavraTentada.charAt(i);
                        }
                    }

                    if (palavra.equals(teste)) {
                        imprimir("Fim de Jogo");
                        break;
                    }

                } else {
                    erro++;
                    imprimir("Você errou!\nRestam apenas " + (6 - erro) + " tentativas.");
                    desenho(erro);
                    imprimir(palavraTentada);
                    if (erro == 6) {
                        imprimir("Fim de Jogo");
                        imprimir("A palavra completa era " + palavra);
                        break;
                    }
                }
            }
        }
        jogVenc(erro);
    }

    private static String attPalavraTentada(String palavra, String letras) {
        // Método que vai criar uma string "vz" para atualizar uma variável que acompanha os acertos das letras.
        String vz = "";
        for (int i = 0; i < palavra.length(); i++) {
            vz += letras.indexOf(palavra.charAt(i)) >= 0 ? (palavra.charAt(i) + " ") : "_ ";
        }
        return vz;
    }


    public static void sublin(String palavra) {
        // Método para criar uma quantidade de sublinhas igual a quantidade de letras da
        // palavra oculta,
        // servindo como dica para o JOGADOR2
        for (int i = 0; i < palavra.length(); i++) {
            System.out.print("_ ");
        }
    }

    public static char letra() {
        // Método que captura as letras tentadas pelo JOGADOR2
        imprimir("\n JOGADOR2\n----------\nInforme a letra:");
        return ed.next().toUpperCase().charAt(0);
    }

    public void jogVenc(int erro) {
        // Método para definir qual foi o jogador vencedor
        if (erro == 6) {
            gameEnd("JOGADOR1");
        } else {
            gameEnd("JOGADOR2");
        }
    }

    public static String palavraOculta() {
        // Método para obter a palavra oculta do jogador 1
        imprimir(" JOGADOR1\n----------\nInforme a palavra a ser adivinhada pelo JOGADOR2:");

        return ed.nextLine().toUpperCase();
    }

    public static void desenho(int erro) {
        // Método para desenhar a forca a depender da progressão do erro
        String des ;
        switch (erro) {
            case 1:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     \r\n" + " |     \r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);
                break;
            case 2:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |      |\r\n" + " |     \r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);
                break;
            case 3:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |      |\\\r\n" + " |     \r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);

                break;
            case 4:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     \r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);

                break;
            case 5:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     /\r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);

                break;
            case 6:
                des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     /\\\r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);

                break;
            default:
                des = ("  ______\r\n" + " |      |\r\n" + " |      \r\n" + " |     \r\n" + " |     \r\n" + " |\r\n"
                        + " |	\r\n" + "_|___");
                imprimir(des);

                break;
        }
    }

}
