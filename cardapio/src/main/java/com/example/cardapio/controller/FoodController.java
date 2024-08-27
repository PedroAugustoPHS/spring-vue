package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import com.example.cardapio.food.FoodResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("food")

public class FoodController {



    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void postFood(@RequestBody FoodRequestDTO data){
        repository.save(new Food(data));
        return;
    }

    @Operation(description = "Busca todos os itens da tabela de food")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista de foods retornada com sucesso")
    })
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAll(){
        return repository.findAll().stream().map(FoodResponseDTO::new).toList();
    }

    @Operation(description = "Busca um item na tabela food com base em seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food com o id solicitado retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Food com o id solicitado não foi encontrada")
    })
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public FoodResponseDTO getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(FoodResponseDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
    }

    @Operation(description = "Edita um item na tabela food com base em seu ID")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public FoodResponseDTO updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO data) {
        Food food = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
        food.setTitle(data.title());
        food.setImage(data.image());
        food.setPrice(data.price());

        repository.save(food);

        return new FoodResponseDTO(food);
    }
}
