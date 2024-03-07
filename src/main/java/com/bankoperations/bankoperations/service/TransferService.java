package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.Transfer;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.exception.InsufficientBalanceException;
import com.bankoperations.bankoperations.exception.InvalidTransferException;
import com.bankoperations.bankoperations.exception.UserNotFoundException;
import com.bankoperations.bankoperations.repository.TransferRepository;
import com.bankoperations.bankoperations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Transfer transferMoney(Long senderId, Long recipientId, double amount) throws InsufficientBalanceException,
            UserNotFoundException, InvalidTransferException {
        User sender = userRepository.findById(senderId).orElse(null);
        User recipient = userRepository.findById(recipientId).orElse(null);

        if (senderId.equals(recipientId)) {
            throw new InvalidTransferException("You cannot transfer money to your account");
        }

        if (sender == null) {
            throw new UserNotFoundException("Sender not found");
        }

        if (recipient == null) {
            throw new UserNotFoundException("Recipient not found");
        }

        if (sender.getBankAccount().getBalance() < amount && sender.getBankAccount().getBalance() - amount < 0) {
            throw new InsufficientBalanceException("There are not enough funds in the account to complete the transfer");
        }

        sender.getBankAccount().setBalance(sender.getBankAccount().getBalance() - amount);
        userRepository.save(sender);

        recipient.getBankAccount().setBalance(recipient.getBankAccount().getBalance() + amount);
        userRepository.save(recipient);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setAmount(amount);
        transfer.setTimestamp(new Date());
        transferRepository.save(transfer);

        return transfer;
    }
}
