package com.supermarket.food_test.controllers;

import com.supermarket.food_test.dtos.FoodDTO;
import com.supermarket.food_test.models.Food;
import com.supermarket.food_test.services.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService service;

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Food food = service.findById(id);
        if(food != null){
            return ResponseEntity.status(HttpStatus.FOUND).body(food);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity postFood(@RequestBody @Valid FoodDTO foodDTO){
        Food food = service.postFood(foodDTO);
        if(food != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(food);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putFood(@RequestBody @Valid FoodDTO foodDTO, @PathVariable Long id){
        Food food = service.putFood(foodDTO,id);
        if(food != null){
            return ResponseEntity.status(HttpStatus.OK).body(food);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFood(@PathVariable Long id){
        if(service.deleteFood(id)){
            return ResponseEntity.status(HttpStatus.OK).body("deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}
