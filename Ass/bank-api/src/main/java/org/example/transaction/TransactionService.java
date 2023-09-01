package org.example.transaction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository tranRepository) {
        this.transactionRepository = tranRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    public Transaction findByAccountId(Long id) {
        return transactionRepository.findByAccountId(id);
    }

    public Transaction findByBalanceId(Long id) {
        return transactionRepository.findByBalanceId(id);
    }

    public void deleteByAccountId(Long bal_id)
    {
        transactionRepository.deleteByAccountId(bal_id);
    }

    public Transaction create(Transaction transaction)
    {
        if (transaction.getId() != null && transactionRepository.existsById(transaction.getId()))
        {
            return null;
        }
        transaction.setId(System.currentTimeMillis());
        return transactionRepository.save(transaction);
    }
}
