package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.BankAccount;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class BalanceUpdateService {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 60000)
    public void updateBalances() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            BankAccount bankAccount = user.getBankAccount();
            double currentBalance = bankAccount.getBalance();
            double initialDeposit = user.getInitialDeposit();
            double newBalance = currentBalance * 1.05;
            if (newBalance > initialDeposit * 2.07) {
                newBalance = initialDeposit * 2.07;
            }
            bankAccount.setBalance(newBalance);
            userRepository.save(user);
        }
    }
}
