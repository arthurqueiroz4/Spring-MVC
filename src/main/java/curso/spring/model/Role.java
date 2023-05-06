package curso.spring.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomeRole;
    @Override
    public String getAuthority() {
        return this.nomeRole;
    }
    public String getNomeRole() {
        return nomeRole;
    }

    public Role setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
        return this;
    }
    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

}
