// src/main/java/com/sinodal/FinTechIsabela/controller/ClienteController.java
package test.java.sinodal.com.FintechIsabela.controller;

import com.sinodal.FinTechIsabela.dto.ClienteDTO;
import com.sinodal.FinTechIsabela.dto.CriarClienteRequest;
import com.sinodal.FinTechIsabela.dto.TransacaoRequest;
import com.sinodal.FinTechIsabela.model.Cliente;
import com.sinodal.FinTechIsabela.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // Indica que esta classe é um Controller REST
@RequestMapping("/api/clientes") // Define o caminho base para todos os endpoints neste Controller
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired // Injeção de dependência do serviço
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Endpoint para criar um novo cliente com conta
    @PostMapping // Mapeia requisições POST para /api/clientes
    public ResponseEntity<?> criarCliente(@RequestBody CriarClienteRequest request) {
        try {
            // No serviço, a senha deve ser hasheada antes de ser salva!
            // Exemplo simples: String senhaHash = new BCryptPasswordEncoder().encode(request.getSenha());
            Cliente novoCliente = clienteService.criarNovoClienteComConta(
                    request.getNome(),
                    request.getCpf(),
                    request.getEmail(),
                    request.getSenha(), // Lembre-se de hashear no serviço!
                    request.getNumeroContaBancaria(),
                    request.getSaldoInicial(),
                    request.getTipoPrimeiraConta()
            );

            // Converte a entidade Cliente para ClienteDTO para a resposta
            ClienteDTO responseDTO = new ClienteDTO(
                    novoCliente.getId(),
                    novoCliente.getNome(),
                    novoCliente.getCpf(),
                    novoCliente.getEmail(),
                    novoCliente.getContaBancaria() != null ? novoCliente.getContaBancaria().getNumeroConta() : null,
                    novoCliente.getContaBancaria() != null ? novoCliente.getContaBancaria().getSaldo() : null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED); // Retorna 201 Created
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
        } catch (Exception e) {
            return new ResponseEntity<>("Erro interno ao criar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 Internal Server Error
        }
    }

    // Endpoint para buscar um cliente por ID
    @GetMapping("/{id}") // Mapeia requisições GET para /api/clientes/{id}
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id)
                .map(cliente -> {
                    ClienteDTO dto = new ClienteDTO(
                            cliente.getId(),
                            cliente.getNome(),
                            cliente.getCpf(),
                            cliente.getEmail(),
                            cliente.getContaBancaria() != null ? cliente.getContaBancaria().getNumeroConta() : null,
                            cliente.getContaBancaria() != null ? cliente.getContaBancaria().getSaldo() : null
                    );
                    return new ResponseEntity<>(dto, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND)); // Retorna 404 Not Found
    }

    // Endpoint para listar todos os clientes
    @GetMapping // Mapeia requisições GET para /api/clientes
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        List<ClienteDTO> clientesDTO = clienteService.listarTodosClientes().stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getEmail(),
                        cliente.getContaBancaria() != null ? cliente.getContaBancaria().getNumeroConta() : null,
                        cliente.getContaBancaria() != null ? cliente.getContaBancaria().getSaldo() : null
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientesDTO, HttpStatus.OK);
    }

    // Endpoint para realizar um depósito
    @PostMapping("/depositar") // Mapeia POST para /api/clientes/depositar
    public ResponseEntity<String> depositar(@RequestBody TransacaoRequest request) {
        try {
            clienteService.realizarDeposito(request.getNumeroConta(), request.getValor());
            return new ResponseEntity<>("Depósito realizado com sucesso!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao realizar depósito: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para realizar um saque
    @PostMapping("/sacar") // Mapeia POST para /api/clientes/sacar
    public ResponseEntity<String> sacar(@RequestBody TransacaoRequest request) {
        try {
            clienteService.realizarSaque(request.getNumeroConta(), request.getValor());
            return new ResponseEntity<>("Saque realizado com sucesso!", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao realizar saque: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para aplicar rendimento (demonstrando polimorfismo)
    @PostMapping("/aplicar-rendimento/{contaId}") // Mapeia POST para /api/clientes/aplicar-rendimento/{id da entidade 'Conta'}
    public ResponseEntity<String> aplicarRendimento(@PathVariable Long contaId) {
        try {
            clienteService.aplicarRendimentoConta(contaId);
            return new ResponseEntity<>("Rendimento aplicado com sucesso!", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao aplicar rendimento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Exemplo: Endpoint para deletar um cliente (CUIDADO em produção!)
    @DeleteMapping("/{id}") // Mapeia DELETE para /api/clientes/{id}
    public ResponseEntity<String> deletarCliente(@PathVariable Long id) {
        try {
            // Em um cenário real, você teria lógica para remover a conta bancária e outros dados relacionados
            // ou desativar o cliente em vez de deletar completamente.
            clienteService.deletarCliente(id); // Supondo que você adicione este método no ClienteService
            return new ResponseEntity<>("Cliente e dados relacionados removidos com sucesso!", HttpStatus.NO_CONTENT); // 204 No Content
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}