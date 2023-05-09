package curso.spring.repository;

import curso.spring.model.Pessoa;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("select p from Pessoa p where p.nome like %?1%")
    List<Pessoa> findPessoaByName(String nome);
    @Query("select p from Pessoa p where p.sexo = :sexo")
    List<Pessoa> findPessoaBySexo(String sexo);
    @Query("select p from Pessoa p where p.sexo = ?1 and p.nome like %?2%")
    List<Pessoa> findPessoaBySexoAndByName(String sexo, String nome);

    default Page<Pessoa> findPessoaByNamePage(String nome, Pageable pageable){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
        Page<Pessoa> pessoas = findAll(example, pageable);

        return pessoas;
    }
    default Page<Pessoa> findPessoaByNomeAndSexoPage(String nome, String sexo,Pageable pageable){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setSexo(sexo);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());


        Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
        Page<Pessoa> pessoas = findAll(example, pageable);

        return pessoas;
    }
    default Page<Pessoa> findPessoaBySexoPage(String sexo ,Pageable pageable){
        Pessoa pessoa = new Pessoa();
        pessoa.setSexo(sexo);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());


        Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
        Page<Pessoa> pessoas = findAll(example, pageable);

        return pessoas;
    }
}
