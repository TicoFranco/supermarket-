package com.supermarket.food_test.controllers;

import com.supermarket.food_test.dtos.input.LoginDto;
import com.supermarket.food_test.dtos.input.OrderDto;
import com.supermarket.food_test.dtos.input.UserInputDto;
import com.supermarket.food_test.dtos.output.UserOutputDto;
import com.supermarket.food_test.models.Order;
import com.supermarket.food_test.models.User;
import com.supermarket.food_test.security.JWTUtil;
import com.supermarket.food_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserInputDto userInputDto){
        User user = userService.postUser(userInputDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDTO){
        Optional<User> user = userService.findByEmail(loginDTO.email());
        if(user.isPresent() && userService.verifyPassword(user.get(),loginDTO.password())){
            String token = JWTUtil.generateToken(user.get().getEmail());
            Map<String,String> response = new HashMap<>();
            response.put("Token",token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @CrossOrigin(origins = {"http://localhost:4200/"})
    @GetMapping("/users/{email}")
    public ResponseEntity findByEmail(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        if(user.isPresent()){
            UserOutputDto response = new UserOutputDto(
                    user.get().getEmail(),
                    user.get().getFirstName(),
                    user.get().getLastName(),
                    user.get().getOrders(),
                    user.get().getUserRole()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @GetMapping("/users")
    public ResponseEntity findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody OrderDto orderDTO){
        Order order = userService.postOrder(orderDTO);
        if(order != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error in data processing");
    }

    @GetMapping("/users/{email}/orders")
    public ResponseEntity getOrders(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userService.getOrdersByEmail(user.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders founded");
    }

    @PutMapping
    public ResponseEntity putUser(@RequestBody UserInputDto userInputDto){
        User user = userService.putUser(userInputDto);
        if(user != null){
            return  ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody LoginDto loginDTO){
        boolean result = userService.deleteUser(loginDTO);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

}
