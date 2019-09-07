package com.parking.parking.repository;

import com.parking.parking.model.VeiculoEstacionamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface VeiculoEstacionamentoRepository extends CrudRepository<VeiculoEstacionamento, Long> {

    @Query(
            value = "SELECT * FROM vaga_veiculo  WHERE id_vaga = :id_vaga and is_ocupada = true ",
            nativeQuery = true)
    VeiculoEstacionamento isVagaOcupada( @Param("id_vaga") Long idVaga);

    @Query(
            value = "SELECT * FROM vaga_veiculo  WHERE id_vaga = :id_vaga and data_fim is null ",
            nativeQuery = true)
    VeiculoEstacionamento getVeiculoEstacionamentoByIdVaga( @Param("id_vaga") Long idVaga);

    @Query(
            value = "select v.id_vaga, v.fileira, v.numero, ve.placa, vv.is_ocupada, e.data_inicio, e.valor_devido, e.valor_pago from vagas as v\n" +
                    "left join vaga_veiculo as vv\n" +
                    "on v.id_vaga = vv.id_vaga\n" +
                    "left join estacionamento as e\n" +
                    "on v.id_vaga = e.id_vaga\n" +
                    "left join veiculo as ve\n" +
                    "on vv.id_veiculo = ve.id_veiculo",
            nativeQuery = true)
    List<Object> listarEstacionamento();

}
