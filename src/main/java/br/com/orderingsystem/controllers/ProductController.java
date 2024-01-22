package br.com.orderingsystem.controllers;

import br.com.orderingsystem.assemblers.ProductModelAssembler;
import br.com.orderingsystem.assemblers.UserModelAssembler;
import br.com.orderingsystem.domain.Product;
import br.com.orderingsystem.domain.User;
import br.com.orderingsystem.exceptions.ProductNotFoundException;
import br.com.orderingsystem.exceptions.UserNotFoundException;
import br.com.orderingsystem.repositories.ProductRepository;
import br.com.orderingsystem.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductModelAssembler productModelAssembler;

    public ProductController(ProductRepository productRepository, ProductModelAssembler productModelAssembler) {
        this.productRepository = productRepository;
        this.productModelAssembler = productModelAssembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {

        List<EntityModel<Product>> products = productRepository.findAll().stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productModelAssembler.toModel(product);

    }

    @PostMapping("/products")
    ResponseEntity<?> newProduct(@RequestBody Product newProduct) {

        EntityModel<Product> entityModel = productModelAssembler.toModel(productRepository.save(newProduct));


        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/products/{id}")
    ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        Product updatedProduct = productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });

        EntityModel<Product> entityModel = productModelAssembler.toModel(updatedProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        productRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
