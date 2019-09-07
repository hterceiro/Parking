package com.parking.parking.repository;

import com.parking.parking.model.Arrecadacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArrecadacaoRepository extends CrudRepository<Arrecadacao, Long> {

    @Query(
            value = "select * from arrecadacao where data_arrecadacao BETWEEN :dataInicio AND :dataFim",
            nativeQuery = true)
    List<Arrecadacao> buscarArrecadacaoPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

    @Query(
            value = "select sum(valor_arrecadado) from arrecadacao where data_arrecadacao BETWEEN :dataInicio AND :dataFim",
            nativeQuery = true)
    Double getValorArrecadado(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

}
