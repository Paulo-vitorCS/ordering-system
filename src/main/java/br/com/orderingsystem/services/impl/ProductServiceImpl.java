package br.com.orderingsystem.services.impl;

import br.com.orderingsystem.domain.Product;
import br.com.orderingsystem.repositories.ProductRepository;
import br.com.orderingsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> findProductByName(String productName) {
        return productRepository.findByName(productName);
    }
}
