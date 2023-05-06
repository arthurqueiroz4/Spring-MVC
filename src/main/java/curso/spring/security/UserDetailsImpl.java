package curso.spring.security;

import curso.spring.model.Usuario;
import curso.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuarioByLogin(s)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado."));

        return new User(usuario.getLogin(),
                usuario.getPassword(),
                true,true,true,true,
                usuario.getAuthorities());
    }
}
