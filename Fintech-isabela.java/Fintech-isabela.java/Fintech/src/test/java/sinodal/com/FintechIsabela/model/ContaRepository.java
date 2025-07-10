// src/main/java/com/sinodal/FinTechIsabela/repository/ContaRepository.java
package test.java.sinodal.com.FintechIsabela.model;

import com.sinodal.FinTechIsabela.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar Optional

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Este método buscará uma entidade 'Conta' cujo 'contaBancaria.id' corresponda ao ID fornecido
    Optional<Conta> findByContaBancariaId(Long contaBancariaId);
}