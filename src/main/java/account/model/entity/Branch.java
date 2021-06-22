package account.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Branch {

    @Id
    private String code;

    @Column(nullable = false)
    private String name;

    public Branch(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Branch() {
    }
}
