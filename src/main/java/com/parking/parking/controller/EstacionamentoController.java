package com.parking.parking.controller;

import com.parking.parking.model.Arrecadacao;
import com.parking.parking.model.Estacionamento;
import com.parking.parking.model.Vaga;
import com.parking.parking.model.Veiculo;
import com.parking.parking.service.EstacionamentoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @ApiOperation(value = "Cadastrar vaga")
	@CrossOrigin
	@PostMapping("/cadastrar-vaga")
	public String cadastrarVaga(@Valid @RequestBody Vaga vaga) {
		String retorno = "";
		try {
			estacionamentoService.cadastrarVaga(vaga);
			retorno = "Vaga cadastrada com sucesso.";
		} catch (Exception e) {
			retorno = "Não foi possivel cadastrar a vaga";
		}
		return retorno;
	}

	@ApiOperation(value = "Listar vagas")
	@CrossOrigin
	@GetMapping("/vagas")
	public List<Vaga> getAllVagas() {
		return estacionamentoService.getAllVagas();
	}

	@ApiOperation(value = "Listar veiculos")
	@CrossOrigin
	@GetMapping("/veiculos")
	public List<Veiculo> getAllVeiculos() {
		return estacionamentoService.getAllVeiculos();
	}

	@ApiOperation(value = "Listar arrecadacao")
	@CrossOrigin
	@GetMapping("/arrecadacao")
	public List<Arrecadacao> getArrecadacoes() {
		return estacionamentoService.getAllArrecadacoes();
	}

	@ApiOperation(value = "Listar tickets estacionamento")
	@CrossOrigin
	@GetMapping("/estacionamento")
	public List<Estacionamento> getEstacionamento() {
		return estacionamentoService.getAllEstacionamentoOcupados();
	}

	@ApiOperation(value = "Estacionar veiculo")
	@CrossOrigin
	@PostMapping("/estacionar-veiculo-vaga/{idVaga}")
	public String estacionarVeiculo(@PathVariable(value = "idVaga") Long idVaga, @RequestBody Veiculo veiculo) {
		String retorno = "";
		try {
			estacionamentoService.estacionarVeiculo(veiculo, idVaga);
			retorno = "Estacionado com sucesso.";
		} catch (Exception e) {
			retorno = "Não foi possivel estacionar";
		}
		return retorno;
	}

	@ApiOperation(value = "Listar vagas disponiveis")
	@CrossOrigin
	@GetMapping("/vagas-disponiveis")
	public List<Vaga> getVagasDisponiveis() {
		return estacionamentoService.consultarVagasDisponiveis();
	}

	@ApiOperation(value = "Pagar ticket")
	@CrossOrigin
	@PostMapping("/pagar-ticket")
	public String pagarEstacionamento(@Valid @RequestBody Estacionamento estacionamento) {
		String retorno = "";
		try {
			estacionamentoService.pagarEstacionamento(estacionamento);

		} catch (Exception e) {

		}
		return retorno;
	}

	@ApiOperation(value = "Relatorio arrecadacao")
	@CrossOrigin
	@GetMapping("/relatorio-arrecadacao")
	public String getValorArrecadado(@Valid @RequestBody Veiculo veiculo) {
		String retorno = "";

		return retorno;
	}

	@ApiOperation(value = "Listar vagas ocupadas")
	@CrossOrigin
	@GetMapping("/vagas-ocupadas")
	public List<Vaga> consultarVagasOcupadas() {
		return estacionamentoService.consultarVagasOcupadas();
	}

	@ApiOperation(value = "Buscar valor devido")
	@CrossOrigin
	@GetMapping("/valor-devido/{idVaga}")
	public Double getValorDevido(@PathVariable(value = "idVaga") Long idVaga) {
		return getValorDevido(idVaga);
	}

	@ApiOperation(value = "Buscar ticket por vaga")
	@CrossOrigin
	@GetMapping("/estacionamento-vaga/{idVaga}")
	public Estacionamento getEstacionamentoByVaga(@PathVariable(value = "idVaga") Long idVaga) {
		return estacionamentoService.getEstacionamentoByVaga(idVaga);
	}

	@ApiOperation(value = "Relatorio rendimentos")
	@CrossOrigin
	@GetMapping("/rendimentos-periodo/{dataInicio}/{dataFim}")
	public List<Object> consultarArrecadacaoPeriodo(@PathVariable(value = "dataInicio") Date dataInicio, @PathVariable(value = "dataFim") Date dataFim) {
		return estacionamentoService.getArrecadacaoPeriodo(dataInicio, dataFim);
	}
}
