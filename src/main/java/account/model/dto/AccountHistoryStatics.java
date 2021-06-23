package account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class AccountHistoryStatics {
    private String year;
    private Long accountNo;
    private Long totalAmount;
    private String name;

    public AccountHistoryStatics(String year, Long accountNo, Long totalAmount, String name) {
        this.year = year;
        this.accountNo = accountNo;
        this.totalAmount = totalAmount;
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
