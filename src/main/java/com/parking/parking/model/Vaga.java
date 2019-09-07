package com.parking.parking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vagas")
@EntityListeners(AuditingEntityListener.class)
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVaga;

    private Long numero;
    private String fileira;

    @ManyToMany(mappedBy = "vagas")
    @JsonBackReference
    List<Veiculo> veiculo;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public List<Veiculo> getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(List<Veiculo> veiculo) {
        this.veiculo = veiculo;
    }

    public Long getIdVaga() {
        return idVaga;
    }

    public String getFileira() {
        return fileira;
    }

    public void setFileira(String fileira) {
        this.fileira = fileira;
    }
}
