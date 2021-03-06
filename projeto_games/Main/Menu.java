package projeto_games.Main;

import projeto_games.logic.Batalha_Naval;
import projeto_games.logic.Forca;
import projeto_games.logic.Jogo_Da_Velha;

import java.util.Scanner;

import static java.lang.System.in;

// TODO: 03/06/2021 implementar System.exit(1), no case 4, para terminar a execução e retirar o do-while da main
public class Menu {
    public static void main(String[] args) {
        int option;

        do {
            option = menu();
            caseOptions(option);
        } while (option != 4);

    }

    private static int menu(){
        int i;
        impressao("-----------------------------------------");
        impressao("Qual jogo você deseja jogar?");
        impressao("(1) Jogo da Forca\n(2) Batalha Naval\n(3) Jogo da Velha\n(4) Sair");
        Scanner scan = new Scanner(in);
        try{
            i = scan.nextInt();
        } catch (Exception ie){
            impressao("Por favor, insira um número:");
            i = menu();
        }
        return i;

    }

    private static void caseOptions(int option) {
        switch (option) {
            case 1:
                Forca F = new Forca();
                impressao(" \n" + " ");
                impressao("JOGO DA FORCA INICIADO\n" + "-----------------------------");
                F.gameStart();
                break;

            case 2:
                impressao(" \n" + " ");
                impressao("BATALHA NAVAL INICIADO\n" + "-----------------------------");
                new Batalha_Naval();

                break;

            case 3:
                impressao(" \n" + " ");
                impressao("JOGO DA VELHA INICIADO\n" + "-----------------------------");
                new Jogo_Da_Velha();
                break;

            case 4:
                impressao("Você saiu do jogo.");
//			System.exit(1);
                break;

            default:
                impressao("Número inválido");
                break;
        }
    }

    public static void impressao(String txt) {
        System.out.println(txt);

    }
}