package account.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
public class Account {
    @Id
    private Long no;

    private String name;

    private String branchCode;

    protected Account() {
    }

    @Builder
    private Account(Long no, String name, String branchCode) {
        this.no = no;
        this.name = name;
        this.branchCode = branchCode;
    }

    private void checkNotNull(Long no, String name, String branchCode) {
        if (no == null || name == null || name.isEmpty() || branchCode == null || branchCode.isEmpty()) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }
}
