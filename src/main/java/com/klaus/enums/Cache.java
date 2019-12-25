package com.klaus.enums;

import com.klaus.log.CommonLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache of CodecEnums
 * Created by KlausZ on 2019/12/24.
 */
class Cache {

    private static Map<Class<? extends CodecEnum>, Map<Object, Integer>> CACHE_BY_ENUM = new ConcurrentHashMap<>();
    private static Map<Class<? extends CodecEnum>, Map<Integer, Object>> CACHE_BY_CODE = new ConcurrentHashMap<>();

    private static void decode(Class<? extends CodecEnum> clazz) {
        try {
            if (CACHE_BY_ENUM.containsKey(clazz) && CACHE_BY_CODE.containsKey(clazz)) return;
            Method method = clazz.getMethod("values");
            method.setAccessible(true);
            Enum<?>[] values = (Enum[]) method.invoke(null);
            Map<Object, Integer> mapByEnum = CACHE_BY_ENUM.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
            Map<Integer, Object> mapByCode = CACHE_BY_CODE.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
            for (Enum<?> type : values) {
                Field field = clazz.getField(type.name());
                Codec annotation = field.getAnnotation(Codec.class);
                int val = annotation.value();
                mapByEnum.put(type, val);
                mapByCode.put(val, type);
            }
            CommonLogger.info("decode enum {} : \n mapByEnum: {}\n mapByCode: {}", clazz, mapByEnum, mapByCode);
        } catch (Exception e) {
            throw new IllegalArgumentException(clazz + " decode enum error", e);
        }
    }

    static int getCodeByEnum(Class<? extends CodecEnum> clazz, Object e) {
        decode(clazz);
        return CACHE_BY_ENUM.get(clazz).get(e);
    }

    @SuppressWarnings("unchecked")
    static <T extends CodecEnum> T getEnumByCode(Class<? extends CodecEnum> clazz, int code) {
        decode(clazz);
        return (T) CACHE_BY_CODE.get(clazz).get(code);
    }

}
