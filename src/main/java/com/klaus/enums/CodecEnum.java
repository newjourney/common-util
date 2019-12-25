package com.klaus.enums;

/**
 * 仅供枚举类实现此接口。
 * <p>
 *     用以方便地获取枚举对象对应的类型码（通过Codec定义）；或者通过类型码获取对应的枚举对象
 * </p>
 * Created by KlausZ on 2019/8/30.
 */
public interface CodecEnum {

    default int code() {
        return Cache.getCodeByEnum(this.getClass(), this);
    }

    static <T extends CodecEnum> T getByCode(Class<T> clazz, int code) {
        return Cache.getEnumByCode(clazz, code);
    }
}
