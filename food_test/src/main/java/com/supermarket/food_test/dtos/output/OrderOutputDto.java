package com.supermarket.food_test.dtos.output;

import java.util.Date;
import java.util.List;

public record OrderOutputDto(Date date,Double total ,List<OrdersOutputPurchaseDto> purchases) {
}
