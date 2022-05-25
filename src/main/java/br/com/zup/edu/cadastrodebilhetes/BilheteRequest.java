package br.com.zup.edu.cadastrodebilhetes;

import javax.validation.constraints.*;

public class BilheteRequest {

    @NotBlank
    private String nome;

    @NotBlank  @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String celular;

    @NotNull @Min(1) @Max(9999)
    private Integer numero;

    @NotNull
    private Long idSorteio;


    public BilheteRequest(String nome, String celular, Integer numero, Long idSorteio) {
        this.nome = nome;
        this.celular = celular;
        this.numero = numero;
        this.idSorteio = idSorteio;
    }

    public BilheteRequest() {
    }

    public Bilhete toModel(SorteioRepository sorteioRepository){
        Sorteio sorteio = sorteioRepository.findById(idSorteio).get();
        return new Bilhete(nome, celular, sorteio, numero);
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public Integer getNumero() {
        return numero;
    }

    public Long getIdSorteio() {
        return idSorteio;
    }
}
