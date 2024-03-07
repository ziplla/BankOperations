package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.BankAccount;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.repository.UserRepository;
import org.apache.log4j.Logger;
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

    private final static Logger log = Logger.getLogger(BalanceUpdateService.class);

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
            log.info("Balance " + bankAccount.getId() + " increased by " + (newBalance - currentBalance));
            userRepository.save(user);
        }
    }
}
