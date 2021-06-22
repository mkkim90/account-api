package account.model.entity;

import java.util.Arrays;

public enum CancelStatus {
    Y('Y'), N('N');

    private char value;

    private CancelStatus(char value) {
        this.value = value;
    }

    public static CancelStatus valueOf(char status) {
        return Arrays.stream(CancelStatus.values())
                .filter(cancelStatus -> cancelStatus.sameValue(status))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("취소 여부 값은 Y 또는 N 입니다."));
    }

    private boolean sameValue(char c) {
        return this.value == c;
    }
}
