package curso.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Número não pode ser vazio")
    @NotNull(message = "Número não pode ser nulo")
    private String numero;
    @NotEmpty(message = "Tipo não pode ser vazio")
    @NotNull(message = "Tipo não pode ser nulo")
    private String tipo;
    @JoinColumn(name = "pessoa_id")
    @ManyToOne
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public Telefone setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public Telefone setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Telefone setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }
}
