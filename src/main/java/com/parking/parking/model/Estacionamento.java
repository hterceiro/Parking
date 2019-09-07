package com.parking.parking.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "estacionamento")
@EntityListeners(AuditingEntityListener.class)
public class Estacionamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstacionamento;

    private double valorDevido;
    private double valorPago;
    private Date dataInicio;
    private Date dataFim;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonIdentityInfo (generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id_vaga" )
    @JoinColumn(name="id_vaga")
    private Vaga vaga;

    public double getValorDevido() {
        return valorDevido;
    }

    public void setValorDevido(double valor) {
        this.valorDevido = valor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Long getIdEstacionamento() {
        return idEstacionamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
}
