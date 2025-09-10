package com.supermarket.food_test.controllers;

import com.supermarket.food_test.dtos.LoginDTO;
import com.supermarket.food_test.dtos.OrderDTO;
import com.supermarket.food_test.dtos.UserDTO;
import com.supermarket.food_test.models.Order;
import com.supermarket.food_test.models.User;
import com.supermarket.food_test.security.JWTUtil;
import com.supermarket.food_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO){
        User user = userService.postUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        Optional<User> user = userService.findByEmail(loginDTO.email());
        if(user.isPresent() && userService.verifyPassword(user.get(),loginDTO.password())){
            String token = JWTUtil.generateToken(user.get().getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Token: "+token);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @GetMapping("/users/{email}")
    public ResponseEntity findByEmail(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND).body(user.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @GetMapping("/users")
    public ResponseEntity findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody OrderDTO orderDTO){
        Order order = userService.postOrder(orderDTO);
        if(order != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error in data processing");
    }

    @PutMapping
    public ResponseEntity putUser(@RequestBody UserDTO userDTO){
        User user = userService.putUser(userDTO);
        if(user != null){
            return  ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody LoginDTO loginDTO){
        boolean result = userService.deleteUser(loginDTO);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

}
