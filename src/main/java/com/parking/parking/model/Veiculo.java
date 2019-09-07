package com.parking.parking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "veiculo")
@EntityListeners(AuditingEntityListener.class)
public class Veiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculo;

    private String placa;

    @ManyToMany
    @JoinTable(
            name = "vaga_veiculo",
            joinColumns = @JoinColumn(name = "idVeiculo"),
            inverseJoinColumns = @JoinColumn(name = "idVaga"))
    @JsonBackReference
    List<Vaga> vagas;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<Vaga> getVagaVeiculos() {
        return vagas;
    }

    public void setVagaVeiculos(List<Vaga> vagaVeiculos) {
        this.vagas = vagaVeiculos;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }
}
