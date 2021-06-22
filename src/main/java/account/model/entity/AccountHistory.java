package account.model;

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

    @Column(nullable = false)
    private Long accountNo;

    @Column(nullable = false)
    private Long transactionNo;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private long fee;

    @Column(nullable = false)
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

    public static AccountHistory buildByUploadCsvFile(String[] accountHistories) {
        return AccountHistory.builder()
                .transactionDate(convertLocalDate(accountHistories[0]))
                .accountNo(convertLong(accountHistories[1]))
                .transactionNo(convertLong(accountHistories[2]))
                .price(convertLong(accountHistories[3]))
                .fee(convertLong(accountHistories[4]))
                .cancelStatus(convertCancelStatus(accountHistories[5]))
                .build();
    }

    private static LocalDate convertLocalDate(String accountHistory) {
        try {
            return LocalDate.parse(accountHistory, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("거래일자 데이터 포맷이 올바르지 않습니다.");
        }
    }

    private static Long convertLong(String target) {
        try {
            return Long.parseLong(target);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 포맷이 올바르지 않습니다.");
        }
    }

    private static char convertCancelStatus(String target) {
        checkNotNull(target);
        validateConvertCancelStatus(target);
        return target.charAt(0);
    }

    private static void validateConvertCancelStatus(String target) {
        if (target.length() != 1) {
            throw new IllegalArgumentException("취소 여부 값은 Y 또는 N 입니다.");
        }
    }

    private static void checkNotNull(String target) {
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("필수 값 누락입니다.");
        }
    }
}
