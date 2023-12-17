package br.com.acme.Assessment;

import br.com.acme.Assessment.Controller.JogadorController;
import br.com.acme.Assessment.Exception.ResourceNotFoundException;
import br.com.acme.Assessment.Model.Jogador;
import br.com.acme.Assessment.Service.JogadorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JogadoresServiceTests {
    @Autowired
    JogadorService jogadorService;
    private static final Logger log = LoggerFactory.getLogger(JogadorController.class);


    @Test
    @DisplayName("Deve retornar todos os jogadores")
    void RetornarTodosOsJogadores(){
      List<Jogador> jogadores = jogadorService.getAll();
      assertEquals(5, jogadores.size());
        log.info("Lista com 5 jogadores: " + jogadores);
    }
    @Test
    @DisplayName("Deve retornar um jogador pelo ID")
    void  RetornarUmJogadoresPeloId(){
        Jogador jogador = jogadorService.getById(1L);
        assertEquals(jogador.getNome(), "Fernando");
        assertEquals(jogador.getId(), 1L);
        assertEquals(jogador.getNumeroDaCamisa(), 10);
        assertEquals(jogador.getTime(), "Flamengo");

        assertThrows(ResourceNotFoundException.class,()->{
            jogadorService.getById(-2);
        });
        log.info("Jogador retornado com ID: " + jogadorService.getById(1L));
    }
    @Test
    @DisplayName("Deve remover um jogador pelo ID")
    void RemoverJogadorPeloId(){
        jogadorService.deleteById(1L);
        List<Jogador> jogadores = jogadorService.getAll();
        assertEquals(4, jogadores.size());
        log.info("Jogador removido com sucesso.");
    }
    @Test
    @DisplayName("Deve atualizar um valor no Map")
    void testUpdate(){
        Jogador gabriel = new Jogador(1L, "Gabriel", "Fluminense", 11);
        jogadorService.update(1L, gabriel);
        Jogador atualizado = jogadorService.getById(1L);
        assertEquals("Gabriel", atualizado.getNome());
        log.info("Jogador atualizado: " + jogadorService.getById(1L));
    }
    @Test
    @DisplayName("Deve Inserir um jogador")
    void testeInserir(){
        Jogador roberto = Jogador.builder().nome("Roberto").Time("Corinthias").numeroDaCamisa(12).build();
        Jogador fernando = Jogador.builder().nome("Fernando").Time("Bahia").numeroDaCamisa(4).build();
        Long id = jogadorService.getLastId();
        roberto.setId(++id);

        jogadorService.create(roberto);
        List<Jogador> all = jogadorService.getAll();
        assertEquals(5, all.size());
        Jogador retornado = jogadorService.getById(4);
        assertEquals("Roberto", retornado.getNome());
        assertEquals(4, retornado.getId());
        jogadorService.create(fernando);
        all = jogadorService.getAll();
        assertEquals(5, all.size());
        retornado = jogadorService.getById(5);
        assertEquals("Fernando", retornado.getNome());
        assertEquals(5, retornado.getId());
        log.info("Jogadores inseridos com sucesso.");
    }

}
