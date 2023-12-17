package br.com.acme.Assessment.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class Jogador {
    private Long id;
    private String nome;
    private String Time;
    private int numeroDaCamisa;
}
