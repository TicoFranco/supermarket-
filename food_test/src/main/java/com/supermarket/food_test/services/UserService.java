package com.supermarket.food_test.services;

import com.supermarket.food_test.dtos.input.LoginDto;
import com.supermarket.food_test.dtos.input.OrderDto;
import com.supermarket.food_test.dtos.input.OrderItemDto;
import com.supermarket.food_test.dtos.input.UserInputDto;
import com.supermarket.food_test.dtos.output.OrderOutputDto;
import com.supermarket.food_test.dtos.output.OrdersOutputPurchaseDto;
import com.supermarket.food_test.models.Food;
import com.supermarket.food_test.models.Order;
import com.supermarket.food_test.models.Purchase;
import com.supermarket.food_test.models.User;
import com.supermarket.food_test.repositories.OrderRepository;
import com.supermarket.food_test.repositories.PurchaseRepository;
import com.supermarket.food_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  FoodService foodService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public User postUser(UserInputDto userInputDto){
        String password = passwordEncoder.encode(userInputDto.password());
        var aux = new User();
        aux.setEmail(userInputDto.email());
        aux.setPassword(password);
        aux.setFirstName(userInputDto.firstName());
        aux.setLastName(userInputDto.lastName());
        aux.setAddress(userInputDto.address());
        aux.setUserRole(userInputDto.userRole());
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

    public User putUser(UserInputDto userInputDto){
        Optional<User> user = findByEmail(userInputDto.email());

        if(user.isPresent()){
            String password = passwordEncoder.encode(userInputDto.password());
            var aux = user.get();
            aux.setFirstName(userInputDto.firstName());
            aux.setLastName(userInputDto.lastName());
            aux.setAddress(userInputDto.address());
            aux.setPassword(password);
            userRepository.save(aux);
            return aux;
        }

        return null;
    }

    public boolean deleteUser(LoginDto loginDTO){
        Optional<User> user = findByEmail(loginDTO.email());
        if(user.isPresent() && user.get().getUserRole().equals("USER")){
            userRepository.delete(user.get());
            return true;
        }

        return false;
    }


    public Order postOrder(OrderDto orderDTO){
        boolean listNotStructured = orderDTO.items().stream().
                map(item-> foodService.findById(item.id())).anyMatch(Objects::isNull);

        Optional<User> user = findByEmail(orderDTO.email());

        if(listNotStructured || user.isEmpty() || orderDTO.items().isEmpty()){
            return null;
        }

        Order order = new Order(user.get(),new Date());
        Set<Purchase> items = new HashSet<>();

        for (OrderItemDto itemDTO : orderDTO.items()) {
            Food food = foodService.findById(itemDTO.id());
            if(items.stream().noneMatch(item -> item.getFoodName().equals(food.getName()))){
                Purchase purchase = new Purchase(order,food,itemDTO.count());
                items.add(purchase);
            }
        }

        order.setItems(items);
        user.get().addOrder(order);
        orderRepository.save(order);
        userRepository.save(user.get());

        return order;
    }

    public List<OrderOutputDto> getOrdersByEmail(User user){
        List<Order> orders = orderRepository.ordersByUser(user.getId());
        if(orders.isEmpty()){
            return null;
        }
        List<OrderOutputDto> output = new ArrayList<>();
        orders.forEach(order -> {
            List<Purchase> purchases = purchaseRepository.purchasesByOrder(order.getId());
            List<OrdersOutputPurchaseDto> purchasesOutput = new ArrayList<>();
            purchases.forEach(purchase ->
                purchasesOutput.add(new OrdersOutputPurchaseDto(purchase.getFoodName(),
                        purchase.getQuantity(),
                        purchase.getValue(),
                        purchase.getFoodUrl()))
            );
            OrderOutputDto orderOutput = new OrderOutputDto(order.getDate(),order.getTotal(),purchasesOutput);
            output.add(orderOutput);
        });

        return output;
    }

}
