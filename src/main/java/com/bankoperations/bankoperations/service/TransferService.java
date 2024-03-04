package com.bankoperations.bankoperations.service;

import com.bankoperations.bankoperations.entity.Transfer;
import com.bankoperations.bankoperations.entity.User;
import com.bankoperations.bankoperations.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public void logTransaction(User sender, User recipient, double amount) {
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setAmount(amount);
        transfer.setTimestamp(new Date());
        transferRepository.save(transfer);
    }
}
