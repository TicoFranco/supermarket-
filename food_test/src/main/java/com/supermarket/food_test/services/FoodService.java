package com.supermarket.food_test.services;

import com.supermarket.food_test.dtos.input.FoodDto;
import com.supermarket.food_test.models.Food;
import com.supermarket.food_test.repositories.FoodRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository repository;

    public List<Food> findAll(){
        return repository.findAll();
    }

    public Food findById(Long id){
        Optional<Food> food = repository.findById(id);
        return food.orElse(null);
    }

    public Food postFood(FoodDto foodDTO){
        if(foodDTO.name().isBlank() || foodDTO.price() == null || foodDTO.price() == 0 || foodDTO.type() == null || foodDTO.url().isBlank()){
            return null;
        }else {
            var aux = new Food();
            BeanUtils.copyProperties(foodDTO, aux);
            repository.save(aux);
            return aux;
        }
    }

    public Food putFood(FoodDto foodDTO, Long id) {
        if (foodDTO.name().isBlank() || foodDTO.price() == null || foodDTO.price() == 0 || foodDTO.type() == null || foodDTO.url().isBlank()) {
            return null;
        }else{
            Optional<Food> food = repository.findById(id);
            if(food.isPresent()){
                var aux = food.get();
                BeanUtils.copyProperties(foodDTO,aux);
                repository.save(aux);
                return aux;
            }else{
                return null;
            }
        }
    }

    public boolean deleteFood(Long id){
        Optional<Food> food = repository.findById(id);
        if(food.isPresent()){
            repository.delete(food.get());
            return true;
        }else{
            return false;
        }
    }

    public Optional<Food> findByName(String name){
        String aux = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        return repository.findByName(aux);
    }
}
