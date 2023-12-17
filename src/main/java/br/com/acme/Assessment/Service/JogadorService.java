package br.com.acme.Assessment.Service;

import br.com.acme.Assessment.Controller.JogadorController;
import br.com.acme.Assessment.Exception.ResourceNotFoundException;
import br.com.acme.Assessment.Model.Jogador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class JogadorService {
    private Map<Long, Jogador> jogadores = initJogadores();
    private static final Logger log = LoggerFactory.getLogger(JogadorController.class);
    private Long lastId = 3L;
    private Map<Long, Jogador> initJogadores(){
        Jogador Fernando = new Jogador(1L,"Fernando","Flamengo", 10);
        Jogador Rodrigo = new Jogador(2L,"Rodrigo","Sao Paulo", 4);
        Jogador Guilherme = new Jogador(3L,"Guilherme","Palmeiras", 1);
        Jogador Gustavo = new Jogador(4L,"Gustavo","Fluminense", 11);
        Jogador Neymar = new Jogador(5L,"Neymar","Bahia", 5);
        Map<Long, Jogador> jogadores = new HashMap<>();
        jogadores.put(1L, Fernando); jogadores.put(2L, Rodrigo); jogadores.put(3L, Guilherme); jogadores.put(4L, Gustavo); jogadores.put(5L, Neymar);
        return jogadores;
    }

    public List<Jogador> getAll() {
        return jogadores.values().stream().toList();
    }

    public List<Jogador> getAll(int start, int end) {
        List<Jogador> all = getAll();
        return all.subList(start, end);
    }

    public int count(){
        return jogadores.size();
    }


    public Jogador getById(long id) {
        Jogador jogador = jogadores.get(id);
        if(jogador == null) throw new ResourceNotFoundException("Jogador não foi encontrado");
        return jogador;
    }

    public void deleteById(long id) {
        if(jogadorNaoExiste(id)) throw new ResourceNotFoundException("Jogador não existe.");
        jogadores.remove(id);
    }

    public void update(long id, Jogador jogador) {
        if(jogadorNaoExiste(id)) throw new ResourceNotFoundException("Jogador não existe.");
        jogador.setId(id);
        jogadores.replace(id, jogador);
    }
    public Long getLastId(){
        return this.lastId;
    }

    public void incrementLastId() {
        this.lastId++;
    }

    public void create(Jogador jogador) {
        Long id = ++this.lastId;
        jogador.setId(id);
        jogadores.put(jogador.getId(), jogador);
    }
    private boolean jogadorNaoExiste(long id) {
        return !jogadores.containsKey(id);
    }
}
