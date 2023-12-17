package br.com.acme.Assessment.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class JogadoresPayload {
    private List<Jogador> jogadores;
    private InformacoesPayload info;
}
