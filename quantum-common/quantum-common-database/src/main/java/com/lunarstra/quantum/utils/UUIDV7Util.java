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
        String compactUuid = generateString();
        System.out.println(compactUuid);
        System.out.println(restoreFromString(compactUuid));
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
     * 将uuid中的 - 移除
     *
     * @param uuid
     * @return
     */
    private static String removeHyphens(UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    /**
     * 将紧凑格式的uuid字符串还原为标准UUID格式
     * 格式: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     *
     * @param compactUuid 紧凑格式的uuid字符串(32位,无连字符)
     * @return UUID实例
     */
    public static UUID restoreFromString(String compactUuid) {
        if (compactUuid == null || compactUuid.length() != 32) {
            throw new IllegalArgumentException("Invalid compact UUID string. Expected 32 characters, got: " + (
                compactUuid == null
                    ? "null"
                    : compactUuid.length()));
        }

        // 插入连字符: 8-4-4-4-12
        String formatted = String.format("%s-%s-%s-%s-%s", compactUuid.substring(0, 8), compactUuid.substring(8, 12),
            compactUuid.substring(12, 16), compactUuid.substring(16, 20), compactUuid.substring(20, 32));

        return UUID.fromString(formatted);
    }

    /**
     * 生成紧凑格式的UUIDv7字符串
     * 其中 - 被移除
     *
     * @return 紧凑格式的UUIDv7字符串
     */
    public static String generateString() {
        return removeHyphens(generateRawUUID());
    }

    /**
     * 生成raw格式的UUIDv7字符串
     * 其中 - 被移除
     *
     * @return 生成raw格式的UUIDv7字符串
     */
    public static String generateRawString() {
        return generateRawUUID().toString();
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

}