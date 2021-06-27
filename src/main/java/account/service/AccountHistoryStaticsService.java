package account.service;

import account.model.AccountHistoryResult;
import account.model.dto.AccountHistoryStatics;
import account.model.dto.AccountStatics;
import account.repository.AccountHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Transactional
public class AccountHistoryStaticsService {

    private final AccountHistoryRepository accountHistoryRepository;

    public AccountHistoryStaticsService(final AccountHistoryRepository accountHistoryRepository) {
        this.accountHistoryRepository = accountHistoryRepository;
    }

    public List<AccountHistoryStatics> findAnnualMaxAccounts() {
        List<AccountHistoryResult> list = accountHistoryRepository.findTotalAmountGroupByYearAndAccountNo();
        return convertMaxAccountHistoryStatic(list);
    }

    private List<AccountHistoryStatics> convertMaxAccountHistoryStatic(List<? extends AccountHistoryResult> list) {
        return list.parallelStream()
                .collect(groupingBy(AccountHistoryResult::getYear, TreeMap::new,
                        maxBy(Comparator.comparingLong(AccountHistoryResult::getTotalAmount))))
                .values()
                .stream()
                .map(accountHistoryResult -> AccountHistoryStatics.of(accountHistoryResult.orElseThrow(EntityNotFoundException::new)))
                .collect(toList());
    }

    public List<AccountStatics> findNotAccountHistoryByYear(List<String> years) {
        return getAccountHistoryResults(years)
                .parallelStream()
                .map(accountHistoryResult -> AccountStatics.of(accountHistoryResult))
                .collect(Collectors.toList());
    }

    private List<AccountHistoryResult> getAccountHistoryResults(List<String> years) {
        List<AccountHistoryResult> accountHistoryResults = new ArrayList<>();
        for (String year : years) {
            accountHistoryResults.addAll(accountHistoryRepository.findNotAccountHistoryByYear(year));
        }
        return accountHistoryResults;
    }

}
