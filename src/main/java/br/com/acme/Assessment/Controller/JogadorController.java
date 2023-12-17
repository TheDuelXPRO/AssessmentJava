package br.com.acme.Assessment.Controller;

import br.com.acme.Assessment.Exception.ResourceNotFoundException;
import br.com.acme.Assessment.Model.InformacoesPayload;
import br.com.acme.Assessment.Model.Jogador;
import br.com.acme.Assessment.Model.JogadoresPayload;
import br.com.acme.Assessment.Model.ResponsePayload;
import br.com.acme.Assessment.Service.JogadorApiService;
import br.com.acme.Assessment.Service.JogadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.util.List;


@RestController
@RequestMapping("/jogador")
public class JogadorController {
    private static final  Logger log = LoggerFactory.getLogger(JogadorController.class);
    @Autowired
    JogadorService jogadorService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false, defaultValue = "5") int size, @RequestParam(required = false, defaultValue = "1")int page){
        try {
            if(page < 1) throw new InvalidParameterException("Pagina invalida.");
            List<Jogador> jogadores = jogadorService.getAll();
            int totalSize = jogadorService.count();
            int qtdPaginas =(int) Math.ceil((double) totalSize / (double)size);
            new InformacoesPayload(totalSize, qtdPaginas);
            InformacoesPayload infoPayload = InformacoesPayload.builder().totalSize(totalSize).totalPages(qtdPaginas).build();
            int start = (page -1) * size;
            jogadores = jogadorService.getAll(start, start + size);
            jogadores = jogadores.subList(start, start + size);
            JogadoresPayload jogadoresPayload = new JogadoresPayload(jogadores, infoPayload);
            log.info(String.valueOf(HttpStatus.ACCEPTED));
            log.info("Jogadores encontrados com sucesso: " + jogadorService.getAll());
            return ResponseEntity.ok(jogadores);
        } catch (InvalidParameterException | IndexOutOfBoundsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePayload("Valor da pagina invalida."));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Jogador jogador = jogadorService.getById(id);
            log.info(String.valueOf(HttpStatus.FOUND));
            log.info("Jogador encontrado com sucesso: " + jogador);
            return ResponseEntity.ok(jogador);
        } catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }
    @PostMapping
    public ResponseEntity<ResponsePayload> create(@RequestBody Jogador jogador){
        jogadorService.create(jogador);
        log.info(String.valueOf(HttpStatus.CREATED));
        log.info("Jogador criado com sucesso: " + jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePayload("Jogador criado com sucesso."));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable Long id){
        try{
            jogadorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponsePayload("Jogador deletado com sucesso."));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable Long id, @RequestBody Jogador atualizado){
        try{
            jogadorService.update(id, atualizado);
            log.info(String.valueOf(HttpStatus.ACCEPTED));
            log.info("Jogador alterado com sucesso: " + jogadorService.getById(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponsePayload("Jogador alterado com sucesso."));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        }
    }

    private final JogadorApiService jogadorApiService;

    @Autowired
    public JogadorController(JogadorApiService jogadorApiService) {
        this.jogadorApiService = jogadorApiService;
    }

    @GetMapping("/jogadores-externos")
    public ResponseEntity<ResponsePayload> consultarJogadoresExterno(@PathVariable("jogadoresExterno") String jogadoresExterno) {
        try {
            HttpResponse<String> respostaApi = jogadorApiService.response();
            log.info("Consulta por jogadores externos encontrados:", jogadoresExterno);
            return ResponseEntity.ok(new ResponsePayload("Sucesso"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponsePayload(ex.getMessage()));
        }
    }
}
