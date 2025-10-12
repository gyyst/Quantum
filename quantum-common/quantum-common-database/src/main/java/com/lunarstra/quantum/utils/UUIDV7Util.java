package com.lunarstra.quantum.utils;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/12 23:25
 */

public class UUIDV7Util {

    public static void main(String[] args) {
        UUID generate = generateRawUUID();
        System.out.println(generate);
        System.out.println(extractTimestamp(generate));
        System.out.println(generateString());
    }

    /**
     * 生成UUIDv7
     *
     * @return UUIDv7实例
     */
    public static UUID generateRawUUID() {
        return generateUUID();
    }

    /**
     * 生成UUIDv7
     *
     * @return UUIDv7实例
     */
    private static UUID generateUUID() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    /**
     * 将uuid中的 - 转变为 U
     *
     * @param uuid
     * @return
     */
    private static String convert_2U(UUID uuid) {
        return uuid.toString().replace("-", "U");
    }

    /**
     * 将uuid中的 _ 转变为 U
     *
     * @param uuid
     * @return
     */
    private static UUID convertU2_(String uuid) {
        return UUID.fromString(uuid.replace("U", "-"));
    }

    /**
     * 生成UUIDv7
     * 其中 - 被替换为 U
     *
     * @return UUIDv7实例
     */
    public static String generateString() {
        return convert_2U(generateRawUUID());
    }

    /**
     * 从UUIDv7提取时间戳
     *
     * @param uuid UUIDv7实例
     * @return 时间戳(毫秒)
     */
    public static long extractTimestamp(UUID uuid) {
        return (uuid.getMostSignificantBits() >>> 16) & 0xFFFFFFFFFFFFL;
    }

    /**
     * 检查是否为有效的UUIDv7
     *
     * @param uuid 要检查的UUID
     * @return true如果是有效的UUIDv7
     */
    public static boolean isUUIDv7(UUID uuid) {
        return (uuid.version() == 7) && (uuid.variant() == 2);
    }

    /**
     * 生成可排序的UUIDv7字符串
     *
     * @return 按时间排序的UUIDv7字符串
     */
    public static String generateSortable() {
        return generateRawUUID().toString();
    }
}
