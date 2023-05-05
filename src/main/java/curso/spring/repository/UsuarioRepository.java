package curso.spring.repository;

import curso.spring.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository  extends CrudRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.login = :login")
    Optional<Usuario> findByUsuarioByLogin(String login);
}
