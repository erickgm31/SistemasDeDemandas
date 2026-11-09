package io.github.sistemademandaszoo.zoodemandas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "idUsuario")
    private String id;

    @Column(name = "emailUsuario")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senhaUsuario")
    private String senha;

    @Column(name = "nomeUsuario")
    private String nome;

    @Column(name = "idadeUsuario")
    private int idade;

    @Column(name = "cargoUsuario")
    private String cargo;

    @Column(name = "telefoneUsuario")
    private String telefone;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId(String Id) {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", Nome='" + getNome() + '\'' +
                ", Cargo='" + getCargo() + '\'' +
                ", Telefone='" + getTelefone() + '\'' +
                '}';
    }
}