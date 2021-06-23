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

}
