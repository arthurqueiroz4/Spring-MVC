package curso.spring.repository;

import curso.spring.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "select t from Telefone t where t.pessoa.id = :pessoaid")
    public List<Telefone> getTelefones(Long pessoaid);
}
