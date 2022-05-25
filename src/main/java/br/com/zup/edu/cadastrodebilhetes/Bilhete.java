package br.com.zup.edu.cadastrodebilhetes;

import javax.persistence.*;
import java.time.LocalDate;
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "Unique_celular_numero-da-sorte_sorteio"
                , columnNames = {"celular", "numero","sorteio_id"})
})
@Entity
public class Bilhete {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private String hashCelular;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Sorteio sorteio;

    @Column(nullable = false, length = 9999)
    private Integer numero;

    @Column(nullable = false)
    private LocalDate dataRegistro = LocalDate.now();

    public Bilhete(String nome, String celular, Sorteio sorteio, Integer numero) {
        this.nome = nome;
        this.celular = TelefoneUtils.anonymize(celular);
        this.hashCelular = TelefoneUtils.hash(celular);
        this.sorteio = sorteio;
        this.numero = numero;
    }

    @Deprecated
    public Bilhete() {
    }

    public Long getId() {
        return id;
    }
}
