package account.model.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_no", nullable = false)
    private Account account;

    @Column(nullable = false)
    private Long transactionNo;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private long fee;

    @Column(columnDefinition = "ENUM('Y', 'N')")
    @Enumerated(EnumType.STRING)
    private CancelStatus cancelStatus = CancelStatus.N;

    @Builder
    private AccountHistory(LocalDate transactionDate, Account account, Long transactionNo, long price, long fee, CancelStatus cancelStatus) {
        this.transactionDate = transactionDate;
        this.account = account;
        this.transactionNo = transactionNo;
        this.price = price;
        this.fee = fee;
        this.cancelStatus = cancelStatus;
    }

    public static AccountHistory buildByUploadCsvFile(String[] accountHistories, Account account) {
        return new AccountHistory(accountHistories, account);
    }

    private AccountHistory(String[] accountHistories, Account account) {
        this.transactionDate = convertLocalDate(accountHistories[0]);
        this.account = account;
        this.transactionNo = convertLong(accountHistories[2]);
        this.price = convertLong(accountHistories[3]);
        this.fee = convertLong(accountHistories[4]);
        this.cancelStatus = convertCancelStatus(accountHistories[5]);
    }

    private LocalDate convertLocalDate(String accountHistory) {
        try {
            return LocalDate.parse(accountHistory, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("거래일자 데이터 포맷이 올바르지 않습니다.");
        }
    }

    private Long convertLong(String target) {
        try {
            return Long.parseLong(target);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 포맷이 올바르지 않습니다.");
        }
    }

    private CancelStatus convertCancelStatus(String target) {
        checkNotNull(target);
        validateConvertCancelStatus(target);
        return CancelStatus.valueOf(target.charAt(0));
    }

    private void validateConvertCancelStatus(String target) {
        if (target.length() != 1) {
            throw new IllegalArgumentException("취소 여부 길이는 한자리 수입니다.");
        }
    }

    private void checkNotNull(String target) {
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("필수 값 누락입니다.");
        }
    }
}
