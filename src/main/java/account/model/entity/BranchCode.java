package account.model.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BranchCode {
    A("A", "판교점"),
    B("B", "분당점"),
    C("C", "강남점"),
    D("D", "잠실점"),
    E("E", "을지로점");

    private String code;
    private String name;

    BranchCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BranchCode of(String code) {
        return Arrays.stream(BranchCode.values())
                .filter(branchCode -> branchCode.sameCode(code))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("해당 지점을 찾을 수 없습니다."));
    }

    private boolean sameCode(String code) {
        return this.code.equals(code);
    }

}
