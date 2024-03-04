package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.BankAccount;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.repository.BankAccountRepository;
import com.bankoperations.bankoperations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

//    @Autowired(required = false)
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public User createUser(User request) {
        User user = new User();
        user.setUsername(request.getUsername());
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        user.setPassword(encodedPassword);
        user.setPassword(request.getPassword());
        user.setInitialDeposit(request.getInitialDeposit());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setFullName(request.getFullName());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(request.getInitialDeposit());

        user = userRepository.save(user);
        bankAccountRepository.save(bankAccount);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> searchUsers(String dateOfBirth, String phoneNumber, String fullName, String
            email, int page, int size, String[] sort) {
        // Здесь можно реализовать логику поиска пользователей с учетом переданных параметров
        // В данном примере просто возвращаем всех пользователей
        return userRepository.findAll();
    }

    public User updateContactInfo(Long userId, User request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (request.getPhoneNumber() != null) {
                user.setPhoneNumber(request.getPhoneNumber());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteContactInfo(Long userId, User request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getPhoneNumber().equals(request.getPhoneNumber())) {
                user.setPhoneNumber(null);
                userRepository.save(user);
                return true;
            }
            if (user.getEmail().equals(request.getEmail())) {
                user.setEmail(null);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

}
