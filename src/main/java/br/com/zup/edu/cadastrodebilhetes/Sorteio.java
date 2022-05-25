package br.com.zup.edu.cadastrodebilhetes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Sorteio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String decricao;


    private LocalDate dataSorteio;

    public Sorteio(String decricao, LocalDate dataSorteio) {
        this.decricao = decricao;
        this.dataSorteio = dataSorteio;
    }

    @Deprecated
    public Sorteio() {
    }

    public Long getId() {
        return id;
    }
}
