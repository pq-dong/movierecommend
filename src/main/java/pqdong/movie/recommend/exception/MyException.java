package pqdong.movie.recommend.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * MyException
 *
 * @author pqdong
 * @since 2020/03/04
 */
@Slf4j
public class MyException extends RuntimeException {

    @Getter
    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        log.warn("exception! {}", resultEnum.getMsg());
    }

    public MyException(Integer code,  String msg) {
        super(msg);
        this.code = code;
        log.warn("exception! {}", msg);
    }

}