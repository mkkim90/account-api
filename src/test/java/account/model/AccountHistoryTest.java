package account.model;

import account.model.entity.AccountHistory;
import account.model.entity.CancelStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountHistoryTest {

    @Test
    void create() {
        AccountHistory accountHistory = AccountHistory.builder()
                .accountNo(11111117L)
                .transactionNo(1L)
                .cancelStatus(CancelStatus.valueOf('N'))
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
                    return AccountHistory.buildByUploadCsvFile(split);
                }).collect(Collectors.toList());
        assertThat(accountHistories.size()).isEqualTo(102);
    }

    @DisplayName("거래내역 업로드 등록 예외 - 날짜포맷 불일치")
    @ParameterizedTest
    @ValueSource(strings={"2018102,11111111,1,1000000,0,N","mkk,11111111,1,1000000,0,N"})
    void validUploadTransactionDateException(String input) {
        Assertions.assertThatThrownBy(() -> {
            AccountHistory.buildByUploadCsvFile(input.split(","));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("거래내역 업로드 등록 예외 - 숫자 포맷 불일치")
    @ParameterizedTest
    @ValueSource(strings={"20181002,11111-111,1,1000000,0,N","20200622,11111111,u,1000000,0,N", "20200622,11111111,1,100uui0000,0,N"})
    void validUploadLongParseException(String input) {
        Assertions.assertThatThrownBy(() -> {
            AccountHistory.buildByUploadCsvFile(input.split(","));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("거래내역 업로드 등록 예외 - 취소 여부 Y,N이 아닌 경우 포맷 불일치")
    @ParameterizedTest
    @ValueSource(strings={"20181002,11111111,1,1000000,0,X","20200622,11111111,2,1000000,0,I"})
    void validUploadCancelStatusException(String input) {
        Assertions.assertThatThrownBy(() -> {
            AccountHistory.buildByUploadCsvFile(input.split(","));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
