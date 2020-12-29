package br.com.bobsundyn.model;

public class Pessoa {

    public  String nome;
    public  String imagem;
    public  int idade;
    private String key;

    public Pessoa (){

    }

    public Pessoa(String nome, String imagem, int idade) {
        this.nome = nome;
        this.imagem = imagem;
        this.idade = idade;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
