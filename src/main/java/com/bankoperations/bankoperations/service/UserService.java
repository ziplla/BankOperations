package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.BankAccount;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.exception.*;
import com.bankoperations.bankoperations.model.UpdateEmailRequest;
import com.bankoperations.bankoperations.model.UpdatePhoneRequest;
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

    public User createUser(User request) throws InvalidUserException {

        try {
            validateUser(request);
        } catch (InvalidUserException e) {
            throw new InvalidUserException(e.getMessage());
        }

        User user = getUser(request);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(request.getInitialDeposit());

        user = userRepository.save(user);
        bankAccountRepository.save(bankAccount);

        return user;
        //TODO: выводить вместе со счетом
    }

    private static User getUser(User request) {
        User user = new User();

        user.setUsername(request.getUsername());
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        user.setPassword(encodedPassword);
        user.setPassword(request.getPassword());
        user.setInitialDeposit(request.getInitialDeposit());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());

        user.setDateOfBirth(request.getDateOfBirth());
        user.setFullName(request.getFullName());
        return user;
    }

    public void validateUser(User user) throws InvalidUserException {
        try {
            validateInitialDeposit(user);
        } catch (InvalidInitialDepositException e) {
            throw new InvalidUserException(e.getMessage());
        }

        try {
            validateUsername(user);
        } catch (InvalidUsernameException e) {
            throw new InvalidUserException(e.getMessage());
        }

        try {
            validateEmail(user);
        } catch (InvalidEmailException e) {
            throw new InvalidUserException(e.getMessage());
        }

        try {
            validatePhoneNumber(user);
        } catch (InvalidPhoneNumberException e) {
            throw new InvalidUserException(e.getMessage());
        }
    }

    public static void validateInitialDeposit(User user) throws InvalidInitialDepositException {
        Double balance = user.getInitialDeposit();
        if (balance <= 0) {
            throw new InvalidInitialDepositException("Initial deposit can't be 0 or less");
        }
    }

    public void validateUsername(User user) throws InvalidUsernameException {
        User checkUsername = userRepository.findByUsername(user.getUsername());
        if (checkUsername != null) {
            throw new InvalidUsernameException("This username is already in use");
        }
    }

    public void validatePhoneNumber(User user) throws InvalidPhoneNumberException {
        User checkPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (checkPhoneNumber != null) {
            throw new InvalidPhoneNumberException("This phone number is already in use");
        }
    }

    public void validateEmail(User user) throws InvalidEmailException {
        User checkEmail = userRepository.findByEmail(user.getEmail());
        if (checkEmail != null) {
            throw new InvalidEmailException("This email is already in use");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> searchUsers(String dateOfBirth, String phoneNumber, String fullName, String
            email, int page, int size, String[] sort) {
        //TODO: Добавить поиск
        return userRepository.findAll();
    }

    public User updateEmail(Long userId, UpdateEmailRequest request) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (request.getNewEmail() != null) {
                user.setEmail(request.getNewEmail());
            }
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public User updatePhoneNumber(Long userId, UpdatePhoneRequest request) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (request.getNewPhoneNumber() != null) {
                user.setPhoneNumber(request.getNewPhoneNumber());
            }
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
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
