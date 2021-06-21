package account.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountHistoryTest {

    @Test
    void create() {
        AccountHistory accountHistory = AccountHistory.builder()
                .accountNo(11111117L)
                .transactionNo(1L)
                .cancelStatus('N')
                .price(1_000_000)
                .fee(0)
                .transactionDate(LocalDate.of(2018,9,24))
                .build();
    }

    @DisplayName("거래내역 업로드")
    @Test
    void upload() throws IOException {
        Resource resource = new ClassPathResource("거래내역.csv");
        List<AccountHistory> accountHistories = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return build(split);
                }).collect(Collectors.toList());
        assertThat(accountHistories.size()).isEqualTo(102);
    }

    private AccountHistory build(String[] split) {
        LocalDate transactionDate = LocalDate.parse(split[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long accountNo = Long.parseLong(split[1]);
        Long transactionNo = Long.parseLong(split[2]);
        long price = Long.parseLong(split[3]);
        long fee = Long.parseLong(split[4]);
        char cancelStatus = split[5].charAt(0);
        return AccountHistory.builder()
                .accountNo(accountNo)
                .transactionNo(transactionNo)
                .cancelStatus(cancelStatus)
                .price(price)
                .fee(fee)
                .transactionDate(transactionDate)
                .build();
    }

    @ParameterizedTest
    @CsvSource({"20180102,2018-01-02","20180703,2018-07-03","20180203,2018-02-03","20180305, 2018-03-05"})
    void convertLocalDate(String input, String expected) {
        LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyyMMdd"));
        assertThat(date).isEqualTo(expected);
    }
}
