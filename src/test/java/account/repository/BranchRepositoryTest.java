package account.repository;

import account.model.entity.Branch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    @Test
    void create() {
        Branch actual = branchRepository.save(new Branch("A", "판교점"));
        Branch expected = branchRepository.findById("A").get();
        assertThat(expected == actual).isTrue();
    }

    @Test
    void uploadTest() throws IOException {
        Resource resource = new ClassPathResource("관리점정보.csv");
        List<Branch> branches = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                .stream().skip(1).map(line -> {
                    String[] split = line.split(",");
                    return new Branch(split[0], split[1]);
                }).collect(Collectors.toList());
        branchRepository.saveAll(branches);
        List<Branch> actuals = branchRepository.findAll();
        assertThat(actuals.size()).isEqualTo(branches.size());
    }
}
