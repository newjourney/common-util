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
        CommonLogger.info("2 : {}", CodecEnum.getByCode(TestEnum.class, 2));
    }

    public enum TestEnum implements CodecEnum {
        @Codec(1) ONE,
        @Codec(2) TWO

    }

}
