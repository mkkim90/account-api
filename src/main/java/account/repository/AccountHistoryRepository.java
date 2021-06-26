package account.repository;

import account.model.AccountHistoryResult;
import account.model.dto.AccountHistoryStatics;
import account.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query(value =
            "SELECT YEAR(transaction_date) as year,  account_no as accountNo, sum(price-fee) as totalAmount, account.name as name from account_history\n" +
                    "INNER JOIN account ON account_history.account_no = account.no\n" +
                    "WHERE account_history.cancel_status = 'N'\n" +
                    "GROUP BY year, account_no",
            nativeQuery = true)
    List<AccountHistoryResult> findTotalAmountGroupByYearAndAccountNo();

    @Query(value =
            "SELECT :year as year, no as accountNo, name\n" +
            "FROM account a\n" +
            "WHERE NOT EXISTS (\n" +
            "SELECT 1 FROM account_history b\n" +
            "WHERE b.cancel_status = 'N' and YEAR(b.transaction_date) = :year AND a.no = b.account_no GROUP BY account_no )",
            nativeQuery = true)
    List<AccountHistoryResult> findNotAccountHistoryByYear(@Param("year") String year);
}
