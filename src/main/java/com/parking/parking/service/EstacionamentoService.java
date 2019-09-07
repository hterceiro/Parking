package com.parking.parking.service;

import com.parking.parking.exception.ResourceNotFoundException;
import com.parking.parking.model.*;
import com.parking.parking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class EstacionamentoService {

    @Autowired
    EstacionamentoRepository estacionamentoRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    VagaRepository vagaRepository;

    @Autowired
    VeiculoEstacionamentoRepository veiculoEstacionamentoRepository;

    @Autowired
    ArrecadacaoRepository arrecadacaoRepository;

    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {

        return entityManagerFactory.createEntityManager();
    }

    public void cadastrarVaga(Vaga vaga) {
        vagaRepository.save(vaga);
    }

    public void deletarVaga(Vaga vaga) {
        vagaRepository.delete(vaga);
    }

    public List<Vaga> getAllVagas() {
       return (List<Vaga>) vagaRepository.findAll();
    }

    public Vaga getVagaById(Long id) {
        return vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga", "idVaga", id));
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void deletarVeiculo(Veiculo veiculo) {
        veiculoRepository.delete(veiculo);
    }

    public List<Veiculo> getAllVeiculos() {
        return (List<Veiculo>) veiculoRepository.findAll();
    }

    public Veiculo getVeiculoById(Long id) {
        return (Veiculo) veiculoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo", "idVeiculo", id));
    }

    public void cadastrarTicketEstacionamento(Vaga vaga) {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setVaga(vaga);
        estacionamento.setValorDevido(7.0);
        estacionamento.setDataInicio(new Date());

        estacionamentoRepository.save(estacionamento);
    }

    public void pagarEstacionamento(Estacionamento estacionamento) throws Exception {
        double valorPago = estacionamento.getValorPago();

        estacionamento = getEstacionamentoById(estacionamento.getIdEstacionamento());
        estacionamento.setValorDevido(calcularValorEstacionamento(estacionamento));
        estacionamentoRepository.save(estacionamento);

        estacionamento.setValorPago(valorPago);

        if (estacionamento.getValorPago() < estacionamento.getValorDevido()){
            throw new Exception("O Valor pago é menor que o valor devido.");
        }

        estacionamento.setDataFim(new Date());
        estacionamentoRepository.save(estacionamento);
        Vaga vaga = estacionamento.getVaga();
        VeiculoEstacionamento veiculoEstacionamento = veiculoEstacionamentoRepository.isVagaOcupada(vaga.getIdVaga());

        veiculoEstacionamento.setOcupada(false);

        veiculoEstacionamentoRepository.save(veiculoEstacionamento);

        Arrecadacao arrecadacao = new Arrecadacao();
        arrecadacao.setValorArrecadado(estacionamento.getValorPago());

        arrecadacaoRepository.save(arrecadacao);
    }

    public Estacionamento getEstacionamentoById(Long id) {
        return (Estacionamento) estacionamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento", "idEstacionamento", id));
    }

    public double calcularValorEstacionamento(Estacionamento estacionamento) {

        int horaAtual = Integer.valueOf(new SimpleDateFormat("HH").format(new Date()));
        int horaEstacionamento = Integer.valueOf( new SimpleDateFormat("HH").format(estacionamento.getDataInicio().getTime()));

        if ((horaAtual - horaEstacionamento) == 3 && estacionamento.getValorPago() != 0){
            estacionamento.setValorDevido(estacionamento.getValorDevido() + 3);
        }

        return estacionamento.getValorDevido();
    }

    public void updateEstacionamento(Estacionamento estacionamento) {
        //update generico
        if (null != estacionamento.getIdEstacionamento()) {
            estacionamentoRepository.save(estacionamento);
        }
    }

    public void estacionarVeiculo(Veiculo veiculo, Long idVaga) throws Exception {
        VeiculoEstacionamento ve= veiculoEstacionamentoRepository.isVagaOcupada(idVaga);

        if (null != ve && ve.isOcupada()) {
            throw new Exception("Esta vaga já encontra-se ocupada.");
        }

        Vaga vaga = getVagaById(idVaga);
        List<Vaga> listaVaga = new ArrayList<>();
        listaVaga.add(vaga);
        veiculo.setVagaVeiculos(listaVaga);

        veiculoRepository.save(veiculo);

        cadastrarTicketEstacionamento(vaga);
    }

    public VeiculoEstacionamento findVeiculoEstacionamentoById(Long id) {
        return veiculoEstacionamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VeiculoEstacionamento", "", id));
    }

    public List<Vaga> consultarVagasDisponiveis () {
        return vagaRepository.consultarVagasDisponiveis();
    }

    public Integer consultarQuantidadeVagasDisponiveis () {
        return consultarVagasDisponiveis().size();
    }

    public List<Object> getEstacionamento() {
        return veiculoEstacionamentoRepository.listarEstacionamento();
    }

    public List<Vaga> consultarVagasOcupadas() {
        return vagaRepository.consultarVagasOcupadas();
    }

    public Double getValorDevido(Long idVaga) {
        Estacionamento estacionamento = getEstacionamentoByVaga(idVaga);

        return calcularValorEstacionamento(estacionamento);
    }

    public List<Arrecadacao> getAllArrecadacoes() {
        return (List<Arrecadacao>) arrecadacaoRepository.findAll();
    }

    public List<Estacionamento> getAllEstacionamento() {
        return (List<Estacionamento>) estacionamentoRepository.findAll();
    }

    public List<Estacionamento> getAllEstacionamentoOcupados() {
        List<Estacionamento> estacionamentos = estacionamentoRepository.findEstacionamentosOcupados();

        for (Estacionamento estacionamento : estacionamentos) {
            estacionamento.setValorDevido(calcularValorEstacionamento(estacionamento));
            estacionamentoRepository.save(estacionamento);
        }

        return estacionamentoRepository.findEstacionamentosOcupados();
    }

    public Estacionamento getEstacionamentoByVaga(Long idVaga) {
        Estacionamento estacionamento = estacionamentoRepository.findEstacionamentoByIdVaga(idVaga);

        return estacionamento;
    }

    public List<Object> getArrecadacaoPeriodo (Date dataInicio, Date dataFim) {
        List<Arrecadacao> arrecadacoes = arrecadacaoRepository.buscarArrecadacaoPorPeriodo(dataInicio, dataFim);
        Double valor = arrecadacaoRepository.getValorArrecadado(dataInicio, dataFim);
        List<Object> lista = new ArrayList<>();
        lista.add(arrecadacoes);
        lista.add(valor);

        return lista ;
    }
}
