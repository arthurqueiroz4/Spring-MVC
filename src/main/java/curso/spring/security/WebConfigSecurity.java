package curso.spring.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsImpl implementacaoUserDetailsService;

    @Override // Configura as solicitações de acesso por Http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // Desativa as configurações padrão de memória.
                .authorizeRequests() // Pertimi restringir acessos
                .antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a pagina inicial
                .antMatchers(HttpMethod.GET, "/cadastropessoa").permitAll()
                .antMatchers("/materialize/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()// permite qualquer usuário
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/cadastropessoa", true)
                .failureUrl("/login?error=true")
                .and()
                .logout().logoutSuccessUrl("/login")
                // Mapeia URL de Logout e invalida usuário autenticado
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }


    @Override // Cria autenticação do usuário com banco de dados ou em memória
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(implementacaoUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override

    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/materialize/**");

    }

}
