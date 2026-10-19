package io.github.cursodsousa.zoodemandas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "demanda")
public class Demanda {

    @Id
    @Column(name = "id")
    private String id;

    // Resumo curto da demanda (ex: "Animal mancando no recinto 4")
    @Column(name = "titulo")
    private String titulo;

    // Cargo que pode resolver (ex: "Médico veterinário")
    @Column(name = "cargo_responsavel")
    private String cargoResponsavel;

    @Column(name = "descricao")
    private String descricao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCargoResponsavel() {
        return cargoResponsavel;
    }

    public void setCargoResponsavel(String cargoResponsavel) {
        this.cargoResponsavel = cargoResponsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Demanda{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", cargoResponsavel='" + cargoResponsavel + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
