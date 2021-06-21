package account.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountHistory {
    private LocalDate transactionDate;
    private Long accountNo;
    private Long transactionNo;
    private long price;
    private long fee;
    private char cancelStatus;

    @Builder
    private AccountHistory(LocalDate transactionDate, Long accountNo, Long transactionNo, long price, long fee, char cancelStatus) {
        this.transactionDate = transactionDate;
        this.accountNo = accountNo;
        this.transactionNo = transactionNo;
        this.price = price;
        this.fee = fee;
        this.cancelStatus = cancelStatus;
    }
}
