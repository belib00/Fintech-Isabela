// src/FintechApp.java
import controller.ContaController;
import view.FintechView;
import model.Conta; // Importar para poder listar contas

public class FintechApp {
    public static void main(String[] args) {
        ContaController controller = new ContaController();
        FintechView view = new FintechView();

        int opcao;
        do {
            view.exibirMenu();
            opcao = view.getOpcaoUsuario();

            switch (opcao) {
                case 1: // Criar Conta Corrente
                    String ccNum = view.pedirNumeroConta();
                    String ccTitular = view.pedirTitular();
                    double ccSaldo = view.pedirValor("saldo inicial");
                    double ccTaxa = view.pedirTaxaManutencao();
                    controller.criarContaCorrente(ccNum, ccSaldo, ccTitular, ccTaxa);
                    break;
                case 2: // Criar Conta Poupança
                    String cpNum = view.pedirNumeroConta();
                    String cpTitular = view.pedirTitular();
                    double cpSaldo = view.pedirValor("saldo inicial");
                    double cpTaxaJuros = view.pedirTaxaJuros();
                    controller.criarContaPoupanca(cpNum, cpSaldo, cpTitular, cpTaxaJuros);
                    break;
                case 3: // Depositar
                    String depNum = view.pedirNumeroConta();
                    double depValor = view.pedirValor("depósito");
                    controller.realizarDeposito(depNum, depValor);
                    break;
                case 4: // Sacar
                    String sacNum = view.pedirNumeroConta();
                    double sacValor = view.pedirValor("saque");
                    controller.realizarSaque(sacNum, sacValor);
                    break;
                case 5: // Exibir Detalhes da Conta
                    String detalhesNum = view.pedirNumeroConta();
                    controller.exibirDetalhesConta(detalhesNum);
                    break;
                case 6: // Aplicar Operação Específica
                    String opEspNum = view.pedirNumeroConta();
                    controller.aplicarOperacaoEspecifica(opEspNum);
                    break;
                case 7: // Listar Todas as Contas
                    view.exibirListaContas(controller.getTodasContas());
                    break;
                case 0:
                    view.exibirMensagem("Saindo da aplicação. Obrigado!");
                    break;
                default:
                    view.exibirMensagem("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        view.fecharScanner();
    }
}
