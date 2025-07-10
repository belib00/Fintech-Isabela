import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importe a classe Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; // Importe a anotação RequestParam

@Controller // Indica que esta classe é um controlador de requisições web
public class LoginController {

    @GetMapping("/login") // Mapeia requisições GET para /login
    public String showLoginForm() {
        // Retorna o nome da view (arquivo HTML/JSP) que contém o formulário de login
        return "login";
    }

    @PostMapping("/login") // Mapeia requisições POST para /login
    public String login(
            @RequestParam String username, // Captura o parâmetro 'username' do formulário
            @RequestParam String password, // Captura o parâmetro 'password' do formulário
            Model model // Injete o objeto Model para adicionar atributos para a view
    ) {
        if ("admin".equals(username) && "password".equals(password)) {
            // Adiciona uma mensagem de sucesso ao modelo
            model.addAttribute("message", "Login bem-sucedido!");
            // Retorna o nome da view para a página de boas-vindas
            return "welcome";
        } else {
            // Adiciona uma mensagem de erro ao modelo
            model.addAttribute("message", "Credenciais inválidas.");
            // Retorna para a página de login para que o usuário possa tentar novamente
            return "login";
        }
    }
}