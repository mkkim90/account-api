package account.contoller;

import java.util.List;

import account.model.AccountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import account.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "계좌 API")
@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResult> getAccountInfo(String branchCode) {
        return accountService.getAccountByBranchCode(branchCode);
    }
}
