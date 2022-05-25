package br.com.zup.edu.cadastrodebilhetes;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class SorteioRequest {

    @NotBlank @Size(max = 150)
    private String descricao;

    @NotNull
    @Future
    private LocalDate data;

    public SorteioRequest(String descricao, LocalDate data) {
        this.descricao = descricao;
        this.data = data;
    }

    public SorteioRequest() {
    }

    public Sorteio toModel() {
        return new Sorteio(descricao, data);
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }
}
