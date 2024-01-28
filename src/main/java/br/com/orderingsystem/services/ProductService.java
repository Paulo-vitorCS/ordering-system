package br.com.orderingsystem.services;

import br.com.orderingsystem.domain.Product;

import java.util.Optional;

public interface ProductService {

    Optional<Product> findProductById(Long productId);
    Optional<Product> findProductByName(String productName);

}
