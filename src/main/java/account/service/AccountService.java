package account.service;

import java.util.List;

import account.model.AccountResult;
import account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountResult> getAccountByBranchCode(String branchCode){
        List<AccountResult> aa = accountRepository.getAccountByBranchCode(branchCode);
        return aa;
    }
}
