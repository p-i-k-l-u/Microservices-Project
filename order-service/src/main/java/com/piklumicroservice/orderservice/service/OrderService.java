package com.piklumicroservice.orderservice.service;


import com.piklumicroservice.orderservice.dto.InventoryResponse;
import com.piklumicroservice.orderservice.dto.OrderRequest;
import com.piklumicroservice.orderservice.model.Order;
import com.piklumicroservice.orderservice.model.OrderLineItems;
import com.piklumicroservice.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional

public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

//    public OrderService(OrderRepository orderRepository, WebClient webClientBuilder) {
//        this.orderRepository = orderRepository;
//        this.webClientBuilder = (WebClient.Builder) webClientBuilder;
//    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemDtos = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemDtos);


        List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItems -> orderLineItems.getSkuCode())
                .toList();


// call inventory service, place the order when order
// in the stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build() )
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allProductsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock, Please try again");

        }


    }

    private OrderLineItems mapToDto(com.piklumicroservice.orderservice.dto.OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItems.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItems;
    }

}