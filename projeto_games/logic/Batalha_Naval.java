package projeto_games.logic;

import projeto_games.Main.Game;
import projeto_games.entities.Jogador;
import projeto_games.entities.Navio;

import java.util.ArrayList;

import static java.lang.System.*;

public class Batalha_Naval extends Game {
    private static boolean GAME_ON;
    private final Jogador player1;
    private final Jogador player2;
    private final ArrayList<Navio> navios = new ArrayList<>();

    public Batalha_Naval(){
        player1 = new Jogador();
        player2 = new Jogador();
//        navios.add(new Navio("navio-teste", 6));
        navios.add(new Navio("porta-aviões", 5));
        navios.add(new Navio("navio-tanque", 4));
        navios.add(new Navio("contra-torpedeiro", 3));
        navios.add(new Navio("submarino ", 2));
        gameStart();
    }


    public void gameStart() {
        posicionamentoDeNavios(player1);
        posicionamentoDeNavios(player2);
        GAME_ON = true;
        while(GAME_ON) {
            //turno do player 1
            gameTurn(player1,player2);
            //turno do player 2
            gameTurn(player2, player1);

        }



    }


    /*inicia o turno do jogador "apelido", caso o jogo ainda não tenha acabado*/
    private void gameTurn(Jogador playerDaVez, Jogador oponente) {
        if (GAME_ON) {
            takeShot(playerDaVez,oponente);
        }


    }


    //	recebe o ponto atirado e avalia se acertou navio ou água
//	se acertou um navio, atualiza a vida e repete
    private void takeShot(Jogador playerDaVez, Jogador oponente) {
        String apelido = playerDaVez.getNick();
        int[][] memoria = playerDaVez.getMemoria();
        Navio[][] naviosDoOponente = oponente.getNaviosOnGame();
        boolean playAgain;
        do {
            displayHP();
            drawBoard(memoria, oponente.getNaviosOnGame());
            imprimir(apelido + " escolha onde deseja atirar, ex: 5 5");

            int[] tiro = new int[2];
            lineSelect(tiro);
            columSelect(tiro);
            wasShot(memoria, naviosDoOponente, tiro);
//		persiste a informação que esse ponto ja foi atirado.
            memoria[tiro[0]][tiro[1]]=1;

            playAgain = damageCheck(oponente, playerDaVez, tiro);
        } while (playAgain);


    }
    //		depois de dado o tiro, checa se existia um navio no ponto e decrementa a vida caso sim e imprime "Água!" caso não.
    private boolean damageCheck(Jogador oponente, Jogador playerDaVez, int[] tiro) {
        boolean playAgain;
        Navio navio = oponente.getNaviosOnGame()[tiro[0]][tiro[1]];

        if(navio !=null) {
            navio.damaged();
            boolean opponentStillAlive = oponente.attVida();
            if (!opponentStillAlive) {
                gameEnd(playerDaVez);
            }else{
                imprimir("Jogue novamente!");
            }
            playAgain = opponentStillAlive;
        }else {
            imprimir("Água!");
            playAgain = false;
        }
        return playAgain;
    }

    //		cuida para que o tiro ocorra num ponto que ainda não foi alvo (memoria[tiro[0]][tiro[1]] == 0)
    private void wasShot(int[][] memoria, Navio[][] navios, int[] tiro) {
        while(memoria[tiro[0]][tiro[1]]==1) {
            imprimir("Já atirou em : "+ tiro[0]+" "+ tiro[1]);
            drawBoard(memoria, navios);
            imprimir("escolha outro alvo:");
            lineSelect(tiro);
            columSelect(tiro);
        }
    }

    private void displayHP() 	{
        for (int i=0 ; i<55 ; i++){
            out.print(" ");
        }
        imprimir(player1.getNick() +"  HP:"+player1.getVida());
        for (int i=0 ; i<55 ; i++){
            out.print(" ");
        }
        imprimir(player2.getNick() +"  HP:"+player2.getVida());
    }
    //	desenha o tabuleiro, exibindo apenas as posiçoes determinadas pelo array memoria

    protected static void drawBoard(Navio[][] posicao) {
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
                        out.print("|  "+shipOrWater(posicao[i][j])+"  ");

                    }

                }
                out.println("|");

            }
        }
        out.println("\n");

    }


    private static void drawBoard(int[][] memoria, Navio[][] navios) {
        out.print("  ");
        for (int j = 0; j <navios.length; j++) {
            out.print("   "+j+"  ");
        }
        out.println();
        for (int i = 0; i < navios.length+1; i++) {
            out.print("  ");
            for (int j = 0; j < 61; j++) {
                out.print("-");
            }

            out.println();
            if(i<navios.length) {
                out.print(i+" ");
                for (int j = 0; j < navios.length+1; j++) {
                    if(j<10&&memoria[i][j]==1) {
                        out.print("|  "+shipOrWater(navios[i][j])+"  ");

                    }else {
                        out.print("|     ");
                    }

                }
                out.println();

            }
        }
        out.println("\n");

    }

    private static String shipOrWater(Navio navio) {
            if(navio!=null){
                return"X";
            }else{
                return "O";
            }
    }



    /*retorna false, caso o jogo deva acabar*/


    protected void gameEnd(Jogador playerVencedor) {
        drawBoard(playerVencedor.getNaviosOnGame());
        GAME_ON = false;
        super.gameEnd(playerVencedor.getNick());


    }

    //	inrforma ao usuario quais navios faltam ser posicionados e recebe qual o próximo ele deseja posicionar
    private void posicionamentoDeNavios(Jogador player) {
        String apelido = player.getNick();
        //		numero total de naios
        final int NAVIOS = navios.size();
        if (isTesting(apelido)) {
            int[][] extreminades;
            for (Navio navio : navios) {
                extreminades = definirExtremidades(player, navio);
                colocarNavio(player, extreminades[0], extreminades[1], navio);
            }

        } else {
            int naviosCont = NAVIOS;
            //controla quais navios são mostrados na tela, independente da ordem de escolha, e a escolha correspondente ao input do usuario.
            int[] escolhaArray = getEscolhaArray(NAVIOS);
            //cuida para que 2 navios não ocupem um mesmo ponto
            while(naviosCont != 0) {

                int escolha = getEscolha(apelido, NAVIOS, naviosCont, escolhaArray);

                Navio navio = navios.get(escolha);
                int[][] extreminades = definirExtremidades(player,  navio);
                colocarNavio(player, extreminades[0], extreminades[1], navio);
                naviosCont--;
            }
        }



    }

    private int[] getEscolhaArray(int NAVIOS) {
        int[] cont = new int[NAVIOS];
        for (int i = 0; i < NAVIOS; i++) {
            cont[i]=i;
        }
        return cont;
    }

    private int getEscolha(String apelido, final int NAVIOS, int naviosCont, int[] escolhaArray) {
        imprimir(apelido +" Escolha qual navio deseja posicionar:");

        for(int j = 1; j<= naviosCont; j++) {
            Navio currNavio = navios.get(escolhaArray[j - 1]);
            imprimir("("+j+")"+ currNavio.getTipo()+"("+ currNavio.getTamanho()+" quadrados)");
        }

        int input = inputInt(1, naviosCont);
//			o input é baseado no que é mostrado em tela sendo assim diferente da escolha real, que é o numero no array cont, no index input-1
        int escolha = escolhaArray[input-1];
//			remove o numero referente ao navio escolhido do array cont e 'recua' os valores seguintes ex:
//			se input = 2 quando cont={1,2,3,4}, o for resultaria em cont={1,3,4,4};
        arraycopy(escolhaArray, input, escolhaArray, input - 1, NAVIOS - input);
        return escolha;
    }

    private static boolean isTesting(String apelido) {
        return (apelido.equalsIgnoreCase("teste1") || apelido.equalsIgnoreCase("teste2"));
    }

    // chama os métodos que definem os pontos inicial e final e depois o que posiciona os navios
    // retorna o valor final da vida
    private static int[][] definirExtremidades(Jogador player, Navio navio) {
        /*Caso os apelidos tenhão sido iniciados com "teste1" e teste2"(ignorando caixa alta) esta etapa é pulada e os navios
         * são setados em uma posição padrão de testes*/
        String apelido = player.getNick();
        String tipoNavio = navio.getTipo();
        Navio[][] naviosJaPosicionados = player.getNaviosOnGame();
        int tamanho = navio.getTamanho();
        int[] pontoInicial;
        int[] pontoFinal;
        if (isTesting(apelido)) {
            pontoInicial = new int[]{0, tamanho};
            pontoFinal = new int[]{tamanho - 1, tamanho};
        } else {
            imprimir(apelido+" definia a posiçao do "+tipoNavio+"\n" +
                    "Informe o ponto onde ficará a primeira extremidade:");
            pontoInicial = checarPontoInicial(naviosJaPosicionados);
            pontoFinal = checarPosicoesValidas(pontoInicial, tamanho, naviosJaPosicionados);
            while(pontoFinal[0]==-1) {
                imprimir("Escolha um novo ponto de inicio:");
                pontoInicial = checarPontoInicial(naviosJaPosicionados);
                pontoFinal = checarPosicoesValidas(pontoInicial, tamanho, naviosJaPosicionados);
            }
        }
        return new int[][]{pontoInicial, pontoFinal};


    }

    /*recebe do usuario uma linha e uma coluna e checa para que a posição esteja vazia,
     * retorna um array com as cordenadas da posição*/
    private static int[] checarPontoInicial( Navio[][] navios) {
        int[] pontoInicial = new int[2];
        lineSelect(pontoInicial);
        columSelect(pontoInicial);
        while(navios[pontoInicial[0]][pontoInicial[1]]!=null){
            imprimir("Outro navio já ocupa esse espaço.\n"
                    + "Escolha outro ponto inicial: ");
            lineSelect(pontoInicial);
            columSelect(pontoInicial);
        }


        return pontoInicial;
    }


    //avalia se existem opções válidas
    //imprime as posiçoes possíveis
    //recebe o input e avalia se é uma das opções
    //retorna um array[2] com a extremidade final do navio ou um array={-1,0} caso não existam opções válidas.
    private static int[] checarPosicoesValidas(int[] posicaoInicial, int tamanhoNavio, Navio[][] naviosJaPosicionados) {
//		soma ou subtrai 1 para contar com a posicao inicial ex: 3-2 = 1, porem um navio de tamanho 2 seria posicionado inicialmente em 3 e depois em 2
        int paraEsquerda = posicaoInicial[1]-tamanhoNavio+1;
        int paraDireita = posicaoInicial[1]+tamanhoNavio-1;
        int paraCima = posicaoInicial[0]-tamanhoNavio+1;
        int paraBaixo = posicaoInicial[0]+tamanhoNavio-1;
        int posicaoEscolhida, cont = 0;
        int[][] posicoesValidas = new int[4][2];
        boolean[] check= {false, false, false, false};

        do {
            if(cont>0) {
                imprimir("Opção inválida!");
                cont = 0;
            }

            if(paraEsquerda>=0) {
                for(int i = posicaoInicial[1]; i>=paraEsquerda ; i--) {
                    if(naviosJaPosicionados[posicaoInicial[0]][i]!=null) {
                        check[0]= false;
                        break;
                    }else{
                        check[0]= true;
                    }
                }
                if(check[0]) {
                    posicoesValidas[cont][0]=posicaoInicial[0];
                    posicoesValidas[cont][1]=paraEsquerda;
                    cont++;
                    imprimir("Agora, escolha entre os possíveis pontos de fim: ");
                    imprimir("("+cont+")virado para esquerda:"+posicaoInicial[0] + " " + paraEsquerda);
                }
            }
            if(paraDireita<=9) {
                for(int i = posicaoInicial[1]; i<=paraDireita ; i++) {
                    if(naviosJaPosicionados[posicaoInicial[0]][i]!=null) {
                        check[1] = false;
                        break;
                    }else{
                        check[1]= true;
                    }
                }
                if(check[1]) {
                    posicoesValidas[cont][0]=posicaoInicial[0];
                    posicoesValidas[cont][1]=paraDireita;
                    cont++;
                    if(cont==1) {
                        imprimir("Agora, escolha entre os possíveis pontos de fim: ");
                    }
                    imprimir("("+cont+")virado para direita:"+posicaoInicial[0] + " " + paraDireita);

                }
            }
            if(paraCima>=0) {
                for(int i = posicaoInicial[0]; i>=paraCima; i--) {
                    if(naviosJaPosicionados[i][posicaoInicial[1]]!=null) {
                        check[2] = false;
                        break;
                    }else{
                        check[2]= true;
                    }
                }
                if(check[2]) {
                    posicoesValidas[cont][0]=paraCima;
                    posicoesValidas[cont][1]=posicaoInicial[1];
                    cont++;
                    if(cont==1) {
                        imprimir("Agora, escolha entre os possíveis pontos de fim: ");
                    }
                    imprimir("("+cont+")virado para cima:"+paraCima + " " + posicaoInicial[1]);

                }
            }
            if(paraBaixo<=9) {
                for(int i = posicaoInicial[0]; i<=paraBaixo; i++) {
                    if(naviosJaPosicionados[i][posicaoInicial[1]]!=null) {
                        check[3] = false;
                        break;
                    }else{
                        check[3]= true;
                    }
                }
                if(check[3]) {
                    posicoesValidas[cont][0]=paraBaixo;
                    posicoesValidas[cont][1]=posicaoInicial[1];
                    cont++;
                    if(cont==1) {
                        imprimir("Agora, escolha entre os possíveis pontos de fim: ");
                    }
                    imprimir("("+cont+")virado para baixo:"+paraBaixo + " " + posicaoInicial[1]);


                }
            }
            if(!(check[0]||check[1]||check[2]||check[3])) {
                imprimir("Não existem posições válidas!\n");

                return new int[]{-1,0};
            }

            posicaoEscolhida = inputInt(1,cont);
        }while(posicaoEscolhida<=0||posicaoEscolhida>cont);

        return posicoesValidas[posicaoEscolhida-1];
    }

    //coloca 'X' nos pontos entre pontoInicial e pontoFinal
    //incrementa a vida passada como parametro em 1, para cada 'X' marcado no array
    private static void colocarNavio(Jogador player, int[] pontoInicial, int[] pontoFinal, Navio navio) {

        int vida=0, distancia, index0, index1;
        //index = 1 quando pontoFinal for maior que inicial e -1 quando for menor.
        if (pontoInicial[0] != pontoFinal[0]) {
            //se a diferença for na linha
            distancia = pontoFinal[0] - pontoInicial[0];
            if (distancia > 0) {
                index0 = 1;
            } else {
                index0 = -1;
                distancia *= -1;
            }
            index1 = 0;
        } else {
            // se a diferença for na coluna
            distancia = pontoFinal[1] - pontoInicial[1];
            if (distancia > 0) {
                index1 = 1;
            } else {
                index1 = -1;
                distancia *= -1;
            }
            index0 = 0;

        }
        //Quando index = 0 não altera a linha/coluna, quando index=1 começa no ponto
        //Final e diminui até o Inicial, quando index=-1 começa no ponto Final e aumenta até o Inicial

        for (int i = 0; i <= distancia; i++) {
            player.getNaviosOnGame()[pontoFinal[0] + (-i * index0)][pontoFinal[1] + (-i * index1)] = navio;
            //a linha seguinte mostraria os 'X' sendo desenhados


            vida++;
        }
        drawBoard(player.getNaviosOnGame());
        player.setVida(vida+player.getVida());
    }




}
