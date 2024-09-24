package auth;

import lombok.Getter;

@Getter
public class TokenDto {
    public Result result;
    public Token token;

    public class Result {
        private String code;
        private String transactionId;

        public String getCode() {
            return code;
        }

        public String getTransactionId() {
            return transactionId;
        }
    }
}
