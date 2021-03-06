package projeto_games.entities;

import static projeto_games.Main.Game.imprimir;

public class Navio {
    private int tamanho;
    private String tipo;
    private int life;
    public Navio(String tipo,int tamanho){
        setTamanho(tamanho);
        setTipo(tipo);
        setLife(tamanho);
    }

    public int getTamanho() {
        return tamanho;
    }

    public String getTipo() {
        return tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public void damaged(){
        life--;
        imprimir("Acertou!!!");
        if(life==0){
            imprimir("Afundou o "+this+"!!!");
        }

    }

    @Override
    public String toString() {
        return getTipo();
    }
}
