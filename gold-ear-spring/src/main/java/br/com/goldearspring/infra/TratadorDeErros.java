package br.com.goldearspring.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class TratadorDeErros {

    private record DadosErroValidacao(String campo, String mensagem){

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity tratar404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarInvalidade(MethodArgumentNotValidException MEx){
        List<FieldError> errors = MEx.getFieldErrors();
        List<DadosErroValidacao> data = new ArrayList<>();
        for(FieldError fe: errors){
            data.add(new DadosErroValidacao(fe.getField(), fe.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
