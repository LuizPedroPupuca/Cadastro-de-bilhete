package br.com.zup.edu.cadastrodebilhetes;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class BilheteController {


    private final BilheteRepository bilheteRepository;

    private final SorteioRepository sorteioRepository;

    public BilheteController(BilheteRepository bilheteRepository, SorteioRepository sorteioRepository) {
        this.bilheteRepository = bilheteRepository;
        this.sorteioRepository = sorteioRepository;
    }

    @PostMapping("/sorteio")
    public ResponseEntity<?> cadastraSorteio(@RequestBody @Valid SorteioRequest sorteioRequest, UriComponentsBuilder uriComponentsBuilder){

        Sorteio sorteio = sorteioRequest.toModel();
        sorteioRepository.save(sorteio);
        URI location = uriComponentsBuilder.path("bilhetes/{id}").buildAndExpand(sorteio.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PostMapping("/bilhetes")
    public ResponseEntity<?> cadastraBilhete(@RequestBody @Valid BilheteRequest bilheteRequest, UriComponentsBuilder uriComponentsBuilder){
        if(!sorteioRepository.existsById(bilheteRequest.getIdSorteio())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorteio não encontrado");
        }

        String hashCelular = TelefoneUtils.hash(bilheteRequest.getCelular());
        if(bilheteRepository.existsByHashCelularAndNumeroAndSorteio_id(hashCelular, bilheteRequest.getNumero(), bilheteRequest.getIdSorteio())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Dados já existentes");

        };

        Bilhete bilhete = bilheteRequest.toModel(sorteioRepository);
        bilheteRepository.save(bilhete);
        URI location = uriComponentsBuilder.path("bilhetes/{id}").buildAndExpand(bilhete.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @ExceptionHandler
    public ResponseEntity<?> errorUniqueConstraint(ConstraintViolationException e, WebRequest webRequest){
        Map<String, Object> body = Map.of(
                "status",422,
                "error", "UNPROCESSABLE_ENTITY",
                "timestamp", LocalDateTime.now(),
                "path", webRequest.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.unprocessableEntity().body(body);
    }



}
