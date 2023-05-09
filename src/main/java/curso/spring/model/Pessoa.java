package curso.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import java.util.Date;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    @NotNull(message = "Sobrenome não pode ser nulo")
    @NotEmpty(message = "Sobrenome não pode ser vazio")
    private String sobrenome;
    @Min(value = 18, message = "Idade inválida")
    @NotNull(message = "Idade não pode ser nulo")
    private int idade;
    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Telefone> telefones;
    @NotNull(message ="Campo CEP não pode ser nulo")
    @NotEmpty(message = "Campo CEP não pode ser vazio")
    private String cep;
    @NotNull(message = "Campo rua não pode ser nulo")
    @NotEmpty(message = "Campo rua não pode ser vazio")
    private String rua;
    @NotNull(message = "Campo bairro não pode ser vazio")
    @NotEmpty(message = "Campo bairro não pode ser vazio")
    private String bairro;
    @NotNull(message = "Campo cidade não pode ser vazio")
    @NotEmpty(message = "Campo cidade não pode ser vazio")
    private String cidade;
    @NotNull(message = "Campo uf não pode ser vazio")
    @NotEmpty(message = "Campo uf não pode ser vazio")
    private String uf;
    @NotNull(message = "Campo ibge não pode ser vazio")
    @NotEmpty(message = "Campo ibge não pode ser vazio")
    private String ibge;
    @NotEmpty(message = "Campo sexo não selecionado")
    private String sexo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    @Lob
    private byte[] file;
    private String nomeFileCurriculo;
    private String tipoFileCurriculo;

    public String getNomeFileCurriculo() {
        return nomeFileCurriculo;
    }

    public Pessoa setNomeFileCurriculo(String nomeFileCurriculo) {
        this.nomeFileCurriculo = nomeFileCurriculo;
        return this;
    }

    public String getTipoFileCurriculo() {
        return tipoFileCurriculo;
    }

    public Pessoa setTipoFileCurriculo(String tipoFileCurriculo) {
        this.tipoFileCurriculo = tipoFileCurriculo;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public Pessoa setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public Pessoa setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
        return this;
    }

    public String getCep() {
        return cep;
    }

    public Pessoa setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public String getRua() {
        return rua;
    }

    public String getSexo() {
        return sexo;
    }

    public Pessoa setSexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public Pessoa setRua(String rua) {
        this.rua = rua;
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public Pessoa setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public Pessoa setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }



    public String getUf() {
        return uf;
    }

    public Pessoa setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public String getIbge() {
        return ibge;
    }

    public Pessoa setIbge(String ibge) {
        this.ibge = ibge;
        return this;
    }



    public List<Telefone> getTelefones() {
        return telefones;
    }

    public Pessoa setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
