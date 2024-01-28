package br.com.orderingsystem.services.impl;

import br.com.orderingsystem.domain.User;
import br.com.orderingsystem.repositories.UserRepository;
import br.com.orderingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    @Override
    public void incrementUserBalance(Long userId, Double amount) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        });
    }

}
