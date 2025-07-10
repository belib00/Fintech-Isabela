package test.java.sinodal.com.FintechIsabela.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes") // Boa prática para mapear o nome da tabela no DB
@Data // Anotação do Lombok para gerar automaticamente getters, setters, toString(), equals() e hashCode()
@NoArgsConstructor // Construtor padrão sem argumentos (necessário para JPA)
@AllArgsConstructor // Construtor com todos os argumentos
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usar IDENTITY para auto-incremento em DBs comuns
    private Long id;

    @Column(nullable = false, length = 100) // Nome é obrigatório e limitado a 100 caracteres
    private String nome;

    @Column(unique = true, nullable = false, length = 14) // CPF é único, obrigatório e limitado a 14 caracteres (com pontos e traço)
    private String cpf;

    @Column(unique = true, nullable = false, length = 100) // Email é único, obrigatório e limitado a 100 caracteres
    private String email;

    @Column(nullable = false) // Senha hasheada é obrigatória (não armazene senhas em texto puro!)
    private String senhaHash; // Senhas devem ser armazenadas como hash (String)

    // Associação: Um Cliente tem uma Conta Bancária (OneToOne)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // CASCADE.ALL: operações de persistência (salvar, deletar) no Cliente cascateiam para ContaBancaria
    // orphanRemoval = true: se a ContaBancaria for desvinculada do Cliente, ela é deletada
    // fetch = FetchType.LAZY: carrega a ContaBancaria apenas quando acessada (otimização)
    @JoinColumn(name = "conta_bancaria_id", unique = true, nullable = false) // Nome da coluna de chave estrangeira
    private ContaBancaria contaBancaria;

    // Relação: Um Cliente pode ter múltiplos Papéis/Roles (ManyToMany)
    @ManyToMany(fetch = FetchType.EAGER) // Carrega os roles junto com o cliente (EAGER ou LAZY dependendo da necessidade de performance)
    @JoinTable( // Cria uma tabela de junção para a relação ManyToMany
        name = "cliente_roles", // Nome da tabela de junção no DB
        joinColumns = @JoinColumn(name = "cliente_id"), // Coluna que referencia o Cliente
        inverseJoinColumns = @JoinColumn(name = "role_id") // Coluna que referencia a Role
    )
    private Set<Role> roles = new HashSet<>(); // Usa Set para garantir unicidade e performance em coleções grandes


    // Construtor manual, útil para criar um Cliente sem ID e sem roles inicialmente
    public Cliente(String nome, String cpf, String email, String senhaHash, ContaBancaria contaBancaria) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senhaHash = senhaHash;
        this.contaBancaria = contaBancaria;
        // As roles são inicializadas por 'new HashSet<>()'
    }

    // Métodos auxiliares para gerenciar a coleção de roles
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}
