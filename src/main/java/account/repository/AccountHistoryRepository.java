package account.repository;

import account.model.AccountHistoryResult;
import account.model.dto.AccountHistoryStatics;
import account.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query(value =
            "SELECT YEAR(transaction_date) as year,  account_no as accountNo, sum(price-fee) as totalAmount, account.name as name from account_history\n" +
                    "INNER JOIN account ON account_history.account_no = account.no\n" +
                    "WHERE account_history.cancel_status = 'N'\n" +
                    "GROUP BY year, account_no",
            nativeQuery = true)
    List<AccountHistoryResult> findTotalAmountGroupByYearAndAccountNo();
}
