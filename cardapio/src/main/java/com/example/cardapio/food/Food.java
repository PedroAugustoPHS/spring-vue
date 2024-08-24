package com.example.cardapio.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "foods")
@Entity(name = "foods")
@Getter //Gera todos os Getters
@NoArgsConstructor //Cria um construtor sem argumentos
@AllArgsConstructor //Cria um construtor com todos os argumentos
@EqualsAndHashCode(of = "id") //Cria uma função que compara um valor com o "Id"desta classe
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String image;

    private Float price;

    public Food(FoodRequestDTO data){
        this.title = data.title();
        this.image = data.image();
        this.price = data.price();
    }
}
