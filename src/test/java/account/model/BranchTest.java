package account.model;

import account.model.entity.Branch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BranchTest {

    @Test
    void create() {
        Branch branch = new Branch("K", "강동지");
    }

    @Test
    void upload() throws IOException {
        Resource resource = new ClassPathResource("관리점정보.csv");
        List<Branch> branches = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return new Branch(split[0],split[1]);
                }).collect(Collectors.toList());
        assertThat(branches.size()).isEqualTo(5);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void checkNotNull(String input) {
        Assertions.assertThatThrownBy(() -> {
            new Branch(input, input);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
