package account.repository;

import account.model.entity.Account;
import account.model.entity.AccountHistory;
import account.model.entity.CancelStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountHistoryRepositoryTest {

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void uploadAccount() throws IOException{
        initAccount();
        initAccountHistory();
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

    private void initAccountHistory() throws IOException {
        Resource resource = new ClassPathResource("거래내역.csv");
        List<AccountHistory> accountHistories = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return AccountHistory.buildByUploadCsvFile(split, Account.builder().no(Long.parseLong(split[1])).build());
                }).collect(Collectors.toList());
        accountHistoryRepository.saveAll(accountHistories);
    }

    /**
     * 거래일자,계좌번호,거래번호,금액,수수료,취소여부
     * 20180102,11111111,1,1000000,0,N
     */
    @Test
    void create() {
        Account account = Account.builder()
                .no(99999999L)
                .branchCode("A")
                .name("김철")
                .build();

        Account savedAccount = accountRepository.save(account);
        AccountHistory accountHistory = AccountHistory.builder()
                .transactionDate(LocalDate.of(2018,01,02))
                .account(savedAccount)
                .transactionNo(1L)
                .price(1000000)
                .fee(0)
                .cancelStatus(CancelStatus.N)
                .build();
        AccountHistory actual = accountHistoryRepository.save(accountHistory);
        assertThat(accountHistory == actual).isTrue();
    }

    @Test
    void uploadTest() throws IOException {
        initAccountHistory();
    }

    @Test
    void findStaticsByYearAndAccountNo() {
        List<Object[]> list = accountHistoryRepository.findTotalAmountGroupByYearAndAccountNo();
        for (Object[] ob : list) {
            System.out.println(ob);
        }
    }
}
