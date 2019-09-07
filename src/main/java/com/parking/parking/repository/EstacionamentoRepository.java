package com.parking.parking.repository;

import com.parking.parking.model.Arrecadacao;
import com.parking.parking.model.Estacionamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EstacionamentoRepository extends CrudRepository <Estacionamento, Long> {
    @Query(
            value = "select * from estacionamento where id_vaga = :idVaga and data_fim is null and valor_pago is null",
            nativeQuery = true)
    Estacionamento findEstacionamentoByIdVaga(@Param("idVaga") Long idVaga);

    @Query(
            value = "select * from estacionamento where data_fim is null and valor_pago = 0",
            nativeQuery = true)
    List<Estacionamento> findEstacionamentosOcupados();
}
