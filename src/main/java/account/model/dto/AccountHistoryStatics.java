package account.model.dto;

import account.model.AccountHistoryResult;
import lombok.*;

@Data
public class AccountHistoryStatics {
    private String year;
    private Long accountNo;
    private Long totalAmount;
    private String name;

    @Builder
    private AccountHistoryStatics(String year, Long accountNo, Long totalAmount, String name) {
        this.year = year;
        this.accountNo = accountNo;
        this.totalAmount = totalAmount;
        this.name = name;
    }

    public static AccountHistoryStatics of(AccountHistoryResult accountHistoryResult) {
        return AccountHistoryStatics.builder()
                .year(accountHistoryResult.getYear())
                .accountNo(accountHistoryResult.getAccountNo())
                .totalAmount(accountHistoryResult.getTotalAmount())
                .name(accountHistoryResult.getName())
                .build();
    }
}
