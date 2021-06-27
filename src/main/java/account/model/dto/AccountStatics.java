package account.model.dto;

import account.model.AccountHistoryResult;
import lombok.Builder;
import lombok.Data;

@Data
public class AccountStatics {
    private String year;
    private Long accountNo;
    private String name;

    @Builder
    private AccountStatics(String year, Long accountNo, String name) {
        this.year = year;
        this.accountNo = accountNo;
        this.name = name;
    }

    public static AccountStatics of(AccountHistoryResult accountHistoryResult) {
        return AccountStatics.builder()
                .accountNo(accountHistoryResult.getAccountNo())
                .name(accountHistoryResult.getName())
                .year(accountHistoryResult.getYear())
                .build();
    }
}
