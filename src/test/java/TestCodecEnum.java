import com.klaus.enums.Codec;
import com.klaus.enums.CodecEnum;
import com.klaus.log.CommonLogger;
import org.junit.Test;

/**
 * Created by KlausZ on 2019/12/25.
 */
public class TestCodecEnum {

    @Test
    public void test() {
        CommonLogger.info("ONE : {}", TestEnum.ONE.code());
        CommonLogger.info("101 : {}", CodecEnum.getByCode(TestEnum.class, 101));
    }

    public enum TestEnum implements CodecEnum {
        @Codec(100) ONE,
        @Codec(101) TWO

    }

}
