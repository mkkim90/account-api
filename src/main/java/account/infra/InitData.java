package account.infra;

import account.model.entity.Account;
import account.model.entity.AccountHistory;
import account.repository.AccountHistoryRepository;
import account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitData {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHistoryRepository accountHistoryRepository;

    @PostConstruct
    private void initData() throws IOException {
        initAccount();
        initAccountHistory();
    }

    private void initAccountHistory() throws IOException {
        Resource resource = new ClassPathResource("거래내역.csv");
        List<AccountHistory> accountHistories = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return AccountHistory.buildByUploadCsvFile(split);
                }).collect(Collectors.toList());
        accountHistoryRepository.saveAll(accountHistories);

    }

    private void initAccount() throws IOException {
        Resource resource = new ClassPathResource("계좌정보.csv");
        List<Account> accountList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return Account.builder().no(Long.parseLong(split[0])).name(split[1]).branchCode(split[2])
                            .build();
                }).collect(Collectors.toList());
        accountRepository.saveAll(accountList);
    }

}
