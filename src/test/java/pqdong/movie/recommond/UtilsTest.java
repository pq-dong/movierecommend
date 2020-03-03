package pqdong.movie.recommond;

import org.junit.Test;
import pqdong.movie.recommend.utils.Md5EncryptionHelper;


/**
 * UtilsTest
 *
 * @author pqdong
 * @since 2020/03/03
 */
public class UtilsTest {

    @Test
    public void getMD5WithSalt() {
        System.out.println(Md5EncryptionHelper.getMD5WithSalt("123456"));
    }
}