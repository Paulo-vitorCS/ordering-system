package br.com.orderingsystem.repositories;

import br.com.orderingsystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
