package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.BankAccount;
import com.bankoperations.bankoperations.entity.Transfer;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.repository.BankAccountRepository;
import com.bankoperations.bankoperations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public double getUserBalance(Long userId) {
        BankAccount bankAccount = bankAccountRepository.findByUserId(userId);
        return (bankAccount != null) ? bankAccount.getBalance() : 0;
    }

    public boolean transferMoney(Transfer request) {
        User sender = request.getSender();
        User recipient = request.getRecipient();

        if (sender == null || recipient == null) {
            return false;
        }

        BankAccount senderAccount = sender.getBankAccount();
        BankAccount recipientAccount = recipient.getBankAccount();

        if (senderAccount == null || recipientAccount == null || senderAccount.getBalance() < request.getAmount()) {
            return false;
        }

        double amount = request.getAmount();
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(recipientAccount);

        return true;
    }
}

