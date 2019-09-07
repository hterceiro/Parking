package com.parking.parking.repository;

import com.parking.parking.model.Vaga;
import com.parking.parking.model.VeiculoEstacionamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends CrudRepository<Vaga, Long> {
    @Query(
            value = "select v.id_vaga, v.fileira, v.numero from vagas as v\n" +
                    "left join vaga_veiculo as vv\n" +
                    "on v.id_vaga = vv.id_vaga\n" +
                    "where vv.is_ocupada = 0 or vv.is_ocupada is null",
            nativeQuery = true)
    List<Vaga> consultarVagasDisponiveis();

    @Query(
            value = "select v.id_vaga, v.fileira, v.numero from vagas as v\n" +
                    "left join vaga_veiculo as vv\n" +
                    "on v.id_vaga = vv.id_vaga\n" +
                    "where vv.is_ocupada = 1",
            nativeQuery = true)
    List<Vaga> consultarVagasOcupadas();
}
