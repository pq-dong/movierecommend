package pqdong.movie.recommend.domain.util;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ResponseMessage
 *
 * @author pqdong
 * @since 2020/03/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {

    private static final int SUCCESS_CODE = 0;

    private static final int FAILED_CODE = 1;

    private static final int PERMISSION_CODE = 2;

    private static final int ILLEGAL_CODE = 3;

    private static final int NEED_LOGIN_CODE = 10;

    private static final int ENCRYPTED_CODE = 1000;

    private int code;

    private String msg;

    private String description;

    private T data;

    public ResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMessage(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public static <T> ResponseMessage<T> successMessage(T data) {
        return new ResponseMessage<>(SUCCESS_CODE, "success", null, data);
    }

    public static ResponseMessage failedMessage(String message) {
        return new ResponseMessage<>(FAILED_CODE, "failed", message, null);
    }

    public static <T> ResponseMessage<T> failedMessage(String message, T data) {
        return new ResponseMessage<>(1, "failed", message, data);
    }

    public static <T> ResponseMessage<T> permissionMessage(T data) {
        return new ResponseMessage<>(PERMISSION_CODE, "permission denied", null, data);
    }

    public static ResponseMessage illegalMessage(String message) {
        return new ResponseMessage<>(ILLEGAL_CODE, "illegal", message, null);
    }

    public static ResponseMessage needLoginMessage() {
        return new ResponseMessage(NEED_LOGIN_CODE, "need login");
    }

    public static ResponseMessage errorMessage(int code, String msg) {
        return new ResponseMessage<>(code, "error", msg, null);
    }

    public boolean success() {
        return this.code == SUCCESS_CODE;
    }


}