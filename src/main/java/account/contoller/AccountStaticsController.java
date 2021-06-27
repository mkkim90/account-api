package account.contoller;

import account.model.AccountResult;
import account.model.dto.AccountHistoryStatics;
import account.model.dto.AccountStatics;
import account.service.AccountHistoryStaticsService;
import account.service.AccountService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api(tags = "계좌 통계 API")
@RestController
@RequestMapping("api/account/statics")
public class AccountStaticsController {

    private final AccountHistoryStaticsService accountHistoryStaticsService;

    public AccountStaticsController(final AccountHistoryStaticsService accountHistoryStaticsService) {
        this.accountHistoryStaticsService = accountHistoryStaticsService;
    }

    @GetMapping("/annualMaximum")
    public List<AccountHistoryStatics> getAnnualMaximumAccountHistory() {
        return accountHistoryStaticsService.findAnnualMaxAccounts();
    }

    @GetMapping("/annualNoAccount")
    public List<AccountStatics> findNotAccountHistoryByYear() {
        return accountHistoryStaticsService.findNotAccountHistoryByYear(Arrays.asList("2018", "2019"));
    }
}
