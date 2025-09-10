package com.supermarket.food_test.services;

import com.supermarket.food_test.dtos.LoginDTO;
import com.supermarket.food_test.dtos.OrderDTO;
import com.supermarket.food_test.dtos.UserDTO;
import com.supermarket.food_test.models.Food;
import com.supermarket.food_test.models.Order;
import com.supermarket.food_test.models.User;
import com.supermarket.food_test.repositories.OrderRepository;
import com.supermarket.food_test.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  FoodService foodService;
    @Autowired
    private OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public User postUser(UserDTO userDTO){
        String password = passwordEncoder.encode(userDTO.password());
        var aux = new User();
        aux.setEmail(userDTO.email());
        aux.setPassword(password);
        aux.setFirstName(userDTO.firstName());
        aux.setLastName(userDTO.lastName());
        aux.setAddress(userDTO.address());
        aux.setUserRole(userDTO.userRole());
        //BeanUtils.copyProperties(userDTO,aux);
        //aux.setPassword(password);
        userRepository.save(aux);
        return aux;
    }

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean verifyPassword(User user,String password){
        return passwordEncoder.matches(password,user.getPassword());
    }

    public User putUser(UserDTO userDTO){
        Optional<User> user = findByEmail(userDTO.email());

        if(user.isPresent()){
            String password = passwordEncoder.encode(userDTO.password());
            var aux = user.get();
            aux.setFirstName(userDTO.firstName());
            aux.setLastName(userDTO.lastName());
            aux.setAddress(userDTO.address());
            aux.setPassword(password);
            userRepository.save(aux);
            return aux;
        }

        return null;
    }

    public boolean deleteUser(LoginDTO loginDTO){
        Optional<User> user = findByEmail(loginDTO.email());
        if(user.isPresent() && user.get().getUserRole().equals("USER")){
            userRepository.delete(user.get());
            return true;
        }

        return false;
    }


    public Order postOrder(OrderDTO orderDTO){
        boolean listNotStructured = orderDTO.items().stream().
                map(item-> foodService.findByName(item.name())).anyMatch(Optional::isEmpty);

        Optional<User> user = findByEmail(orderDTO.user().email());

        if(listNotStructured || user.isEmpty()){
            return null;
        }

        Set<Food> items = orderDTO.items().stream()
                .map(item -> foodService.findByName(item.name()).get())
                .collect(Collectors.toSet());

        Order order = new Order(items,user.get(),new Date());
        user.get().addOrder(order);
        orderRepository.save(order);
        userRepository.save(user.get());

        return order;
    }

}
