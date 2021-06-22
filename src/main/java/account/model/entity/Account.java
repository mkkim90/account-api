package account.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
public class Account {
    @Id
    private Long no;

    private String Name;

    @Enumerated(EnumType.STRING)
    private BranchCode branchCode;

    protected Account() {
    }

    @Builder
    private Account(Long no, String name, BranchCode branchCode) {
        this.no = no;
        Name = name;
        this.branchCode = branchCode;
    }

    private void checkNotNull(Long no, String name, BranchCode branchCode) {
        if (no == null || name == null || name.isEmpty() || branchCode == null) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }
}
