package br.com.orderingsystem.repositories;

import br.com.orderingsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



}
