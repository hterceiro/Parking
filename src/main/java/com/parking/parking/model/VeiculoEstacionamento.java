package com.parking.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vaga_veiculo")
@EntityListeners(AuditingEntityListener.class)
public class VeiculoEstacionamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVagaVeiculo;

    private Long idVeiculo;
    private Long idVaga;

    @Column(name = "is_ocupada", columnDefinition = "boolean default true", nullable = false)
    private boolean isOcupada = true;

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Long getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Long idVaga) {
        this.idVaga = idVaga;
    }

    public boolean isOcupada() {
        return isOcupada;
    }

    public void setOcupada(boolean ocupada) {
        isOcupada = ocupada;
    }

    public Long getIdVagaVeiculo() {
        return idVagaVeiculo;
    }
}
