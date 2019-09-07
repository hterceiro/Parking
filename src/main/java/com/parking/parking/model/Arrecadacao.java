package com.parking.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "arrecadacao")
@EntityListeners(AuditingEntityListener.class)
public class Arrecadacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArrecadacao;

    private Double valorArrecadado;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dataArrecadacao;

    public Double getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(Double valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public Date getDataArrecadacao() {
        return dataArrecadacao;
    }

    public void setDataArrecadacao(Date dataArrecadacao) {
        this.dataArrecadacao = dataArrecadacao;
    }

    public Long getIdArrecadacao() {
        return idArrecadacao;
    }
}
