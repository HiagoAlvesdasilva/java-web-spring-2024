package com.study.userdpt.controllers;

import com.study.userdpt.entities.User;
import com.study.userdpt.exceptions.ResourceNotFoundException;
import com.study.userdpt.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users", description = "User operations")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Operation(summary = "Listar todos os usuários")
@GetMapping
public ResponseEntity<List<User>> findAll(){
    List<User> result = repository.findAll();
    return new ResponseEntity<>(result, HttpStatus.OK);
}

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity <User> findById(@PathVariable Long id){
        User result = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID: "+ id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Criar um novo usuário")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User result = repository.save(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um usuário")
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID: "+ id));
        user.setId(id);
        User result = repository.save(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Apagar um usuário")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID: "+ id));
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
