package com.example.cardapio.food;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Table(name = "foods")
@Entity(name = "foods")
@Getter //Gera todos os Getters
@Setter //Gera todos os Setters
@NoArgsConstructor //Cria um construtor sem argumentos
@AllArgsConstructor //Cria um construtor com todos os argumentos
@EqualsAndHashCode(of = "id") //Cria uma função que compara um valor com o "Id"desta classe
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título não pode estar em branco")
    private String title;

    @NotBlank(message = "A imagem não pode estar em branco")
    private String image;

    @NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "O preço deve ser um valor positivo")
    private Float price;

    public Food(FoodRequestDTO data){
        this.title = data.title();
        this.image = data.image();
        this.price = data.price();
    }
}
