package br.com.orderingsystem.services;


import br.com.orderingsystem.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long userId);
    Optional<User> findUserByCpf(String cpf);
    public void incrementUserBalance(Long userId, Double amount);

}
