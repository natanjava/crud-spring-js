package com.crud.crud.service;

import com.crud.crud.exception.RequestException;
import com.crud.crud.model.PessoaModel;
import com.crud.crud.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    public PessoaRepository repository;

    public List<PessoaModel> listar(){
        return repository.findAll();
    }

    public List<PessoaModel> listarPorOrdemAlfabetica(){
        return repository.listarPorOrdemAlfabetica();
    }

    public List<PessoaModel> listarPorIdade(){
        return repository.listarPorIdade();
    }

    public PessoaModel buscarPorID(Long codigo){
        return isPersonByCode(codigo);
    }

    public List<PessoaModel> buscrPortrecho(String trecho){
        return repository.buscarPorParte(trecho);
    }

    public String salvar(PessoaModel pessoa){
        for(PessoaModel person: repository.findAll()) {
            if(person.getEmail().equals(pessoa.getEmail()) && pessoa.getCodigo() == null) {
                throw new RequestException("Email already used, please choose another!");
            }
        }
        if (repository.contarPessoas() > 14) {
    		throw new RequestException("Maximal number of registers: 15!");
        }	
    	
        repository.save(pessoa);
        return "User saved successfully!";
    }

    public String excluirPorID(@RequestParam (name = "codigo") Long codigo){
        isPersonByCode(codigo);
        repository.deleteById(codigo);
        return "User removed successfully!";
    }

    //validações
    public PessoaModel isPersonByCode(Long codigo){
        Optional<PessoaModel> pessoa = repository.buscaPorID(codigo);
        if(pessoa.isEmpty()) throw new RequestException("User not found");
        else return pessoa.get();
    }
}
