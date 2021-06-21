package account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import account.model.AccountResult;
import account.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(value = "SELECT account_no as accountNo, account_name as accountName FROM account WHERE branch_code = :branchCode", nativeQuery = true)
    List<AccountResult> getAccountByBranchCode(@Param("branchCode") String branchCode);

}
