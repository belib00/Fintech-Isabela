// src/main/java/com/sinodal/FinTechIsabela/FinTechIsabelaApplication.java
import com.sinodal.FinTechIsabela.model.Cliente;
import com.sinodal.FinTechIsabela.model.Conta; 
import com.sinodal.FinTechIsabela.model.ContaBancaria; 
import com.sinodal.FinTechIsabela.model.TipoConta; 

import com.sinodal.FinTechIsabela.service.ClienteService;
import com.sinodal.FinTechIsabela.repository.ContaRepository; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class FinTechIsabelaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinTechIsabelaApplication.class, args);
    }

    // O Spring injeta automaticamente o ClienteService e ContaRepository aqui
    @Bean
    public CommandLineRunner run(ClienteService clienteService, ContaRepository contaRepository) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Bem-vindo(a) à Fintech Isabela! ---");

            try {
                // 1. Criar clientes com diferentes tipos de conta
                System.out.println("\n--- Criando Clientes e Contas ---");
                Cliente joao = clienteService.criarNovoClienteComConta(
                        "João Silva", "111.222.333-44", "joao@email.com",
                        "hash_senha_joao", "CC001", 1500.00, "corrente");
                System.out.println("Cliente João (Conta Corrente) criada: " + joao.getContaBancaria().getNumeroConta());

                Cliente maria = clienteService.criarNovoClienteComConta(
                        "Maria Souza", "555.666.777-88", "maria@email.com",
                        "hash_senha_maria", "CP001", 5000.00, "poupanca");
                System.out.println("Cliente Maria (Conta Poupança) criada: " + maria.getContaBancaria().getNumeroConta());

                Cliente pedro = clienteService.criarNovoClienteComConta(
                        "Pedro Rocha", "999.888.777-66", "pedro@email.com",
                        "hash_senha_pedro", "CI002", 750.00, "investimento");
                System.out.println("Cliente Pedro (Conta Investimento) criada: " + pedro.getContaBancaria().getNumeroConta());


                System.out.println("\n--- Processando Contas na Fintech ---");
              

                List<Cliente> todosClientes = clienteService.listarTodosClientes();
                for (Cliente cliente : todosClientes) {
                    System.out.println("\nDetalhes da Conta de " + cliente.getNome() + ":");
                    ContaBancaria cb = cliente.getContaBancaria();
                    if (cb != null) {
                        cb.exibirDetalhes(); // Exibe detalhes da ContaBancaria

                       
                        System.out.println("Depositando R$200.00...");
                        clienteService.realizarDeposito(cb.getNumeroConta(), 200.00);
                        System.out.println("Sacando R$50.00...");
                        clienteService.realizarSaque(cb.getNumeroConta(), 50.00);

                        Optional<Conta> contaEntityOpt = contaRepository.findByContaBancariaId(cb.getId());
                        if (contaEntityOpt.isPresent()) {
                            Conta contaEntity = contaEntityOpt.get();
                            System.out.println("Aplicando rendimento/taxa específica...");
                            clienteService.aplicarRendimentoConta(contaEntity.getId()); // Chamada polimórfica via serviço
                        } else {
                            System.out.println("Não foi possível encontrar a entidade 'Conta' para esta ContaBancaria.");
                        }
                    }
                }

                System.out.println("\n--- Saldos Finais ---");
                clienteService.listarTodosClientes().forEach(cliente -> {
                    if (cliente.getContaBancaria() != null) {
                        System.out.println("Conta " + cliente.getContaBancaria().getNumeroConta() +
                                           " Saldo Final: R$" + String.format("%.2f", cliente.getContaBancaria().getSaldo()));
                    }
                });

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println("Erro na operação: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace(); // Para depuração
            } finally {
                scanner.close();
                System.out.println("\n--- Fim da Demonstração da Fintech ---");
            }
        };
    }
}
