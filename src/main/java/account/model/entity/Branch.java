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
        checkNotNull(code, name);
        this.code = code;
        this.name = name;
    }

    protected Branch() {
    }

    private void checkNotNull(String code, String name) {
        if (code == null || code.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("필수값 누락입니다.");
        }
    }
}
