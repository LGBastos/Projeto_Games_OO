package projeto_games.Main;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public abstract class Game {
    public abstract  void gameStart();
    protected void gameEnd(String string) {
        imprimir("Vitória de "+string+"!!!");
        this.jogarNovamente();


    }
    protected static void lineSelect(int[] arrayPonto) {
        imprimir("Informe a linha(de 0 à 9):");
        arrayPonto[0] = inputInt(0,9);
    }
    protected static void columSelect(int[] arrayPonto){
        imprimir("Informe a coluna(de 0 à 9):");
        arrayPonto[1]=inputInt(0,9);
    }

    protected void jogarNovamente() {
        // Método para repetição do método inicio
        System.out.println("\n-------------------------\nJogar novamente?\n(1)Sim\t(2)Não");
        int op = inputInt(1,2);
        if (op == 1) {
            this.gameStart();
        }

    }

    protected static int inputInt(int first, int last){
        int i;
        Scanner scan = new Scanner(in);
        try{
            i = scan.nextInt();
            if (!(i <= last && i >= first)){
                throw new Error();
            }
        } catch (Exception ie){
            imprimir("Por favor, inserir apenas números\n" +
                    "Informe novamente:");
            i = inputInt(first, last);
        }catch (Error e){
            imprimir("Valor inválido, valores aceitos no intervalo ["+first+", "+last+"]\n" +
                    "Informne novamente:");
            i = inputInt(first,last);
        }

        return i;

    }

    public static void imprimir(String string) {
        out.println(string);
    }
}
