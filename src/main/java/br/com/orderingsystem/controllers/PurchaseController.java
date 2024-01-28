package br.com.orderingsystem.controllers;

import br.com.orderingsystem.domain.Product;
import br.com.orderingsystem.domain.User;
import br.com.orderingsystem.services.ProductService;
import br.com.orderingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PurchaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("/makePurchase")
    ResponseEntity<?> makePurchase(@RequestParam String cpf, @RequestParam Long productId) {

        Optional<User> optionalUser = userService.findUserByCpf(cpf);
        Optional<Product> optionalProduct = productService.findProductById(productId);

        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            User user = optionalUser.get();
            Product product = optionalProduct.get();

            if (user.getBalance() >= product.getPrice()) {
                user.getProducts().add(product);
                userService.incrementUserBalance(user.getId(), -product.getPrice());

                return ResponseEntity.ok("Purchase successful");
            } else {
                return ResponseEntity.badRequest().body("Insufficient balance to make the purchase");
            }
        } else {
            return ResponseEntity.badRequest().body("User or product not found");
        }
    }

}
