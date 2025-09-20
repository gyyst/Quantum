package com.lunarstra.quantum.utils;

/**
 * @author yi'xiao'yi
 * @title CheckIdCardUtils
 * @description 信息校验工具
 * @date 2022/3/30 11:16
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckAllUsersUtils {
    /**
     * 身份证号码中的出生日期的格式
     */
    private final static String BIRTH_DATE_FORMAT = "yyyyMMdd";

    /**
     * 手机号正则表达式
     */
    private static final String REGEX_PHONE
        = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    private static final String REGEX_EMAIL
        = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    /**
     * 身份证的最小出生日期,1900年1月1日
     */
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);

    private final static int NEW_CARD_NUMBER_LENGTH = 18;

    private final static int OLD_CARD_NUMBER_LENGTH = 15;

    /**
     * 18位身份证中最后一位校验码
     */
    private final static char[] VERIFY_CODE = {
        '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
    };

    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private final static int[] VERIFY_CODE_WEIGHT = {
        7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    /**
     * 手机号长度
     */
    private static final int PHONE_LENGTH = 11;

    /**
     * 检验表格文本长度身份证手机号等方法
     */

    /**
     * 如果是15位身份证号码，则自动转换为18位
     *
     * @param cardNumber
     * @return
     */
    public static boolean checkIdCard(String cardNumber) {
        if (null != cardNumber) {
            cardNumber = cardNumber.trim();
            if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()) {
                cardNumber = contertToNewCardNumber(cardNumber);
            }
            return validate(cardNumber);
        }
        return false;
    }

    public static boolean validate(String cardNumber) {
        boolean result = true;
        // 身份证号不能为空
        result = result && (null != cardNumber);
        // 身份证号长度是18(新证)
        result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length();
        // 身份证号的前17位必须是阿拉伯数字
        for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            result = result && ch >= '0' && ch <= '9';
        }
        // 身份证号的第18位校验正确
        result = result && (calculateVerifyCode(cardNumber) == cardNumber.charAt(NEW_CARD_NUMBER_LENGTH - 1));
        // 出生日期不能晚于当前时间，并且不能早于1900年
        try {
            Date birthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT).parse(getBirthDayPart(cardNumber));
            result = result && null != birthDate;
            result = result && birthDate.before(new Date());
            result = result && birthDate.after(MINIMAL_BIRTH_DATE);
            /**
             * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],
             * 日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
             * 月份和日期相符合
             */
            String birthdayPart = getBirthDayPart(cardNumber);
            String realBirthdayPart = new SimpleDateFormat(BIRTH_DATE_FORMAT).format(birthDate);
            result = result && (birthdayPart.equals(realBirthdayPart));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static String getBirthDayPart(String cardNumber) {
        return cardNumber.substring(6, 14);
    }

    /**
     * 校验码（第十八位数）：
     * <p>
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2;
     * 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10
     * 校验码: 1 0 X 9 8 7 6 5 4 3 2
     *
     * @param cardNumber
     * @return cardNumber
     */
    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += (ch - '0') * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 把15位身份证号码转换到18位身份证号码<br>
     * 15位身份证号码与18位身份证号码的区别为：<br>
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     *
     * @param oldCardNumber
     * @return oldCardNumber
     */
    private static String contertToNewCardNumber(String oldCardNumber) {
        StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
        buf.append(oldCardNumber, 0, 6);
        buf.append("19");
        buf.append(oldCardNumber.substring(6));
        buf.append(calculateVerifyCode(buf));
        return buf.toString();
    }

    public static String hasDigit(String content) {
        String reg = "\\d{17}[\\d|x|X]|\\d{15}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 校验手机号格式是否正确
     *
     * @param phone
     * @return
     * @author tqf
     * @Description 手机号格式校验
     * @Version 1.0
     * @since 2022-08-09 23:28
     */
    public static Boolean regexPhone(String phone) {
        Boolean b;
        if (phone.length() != PHONE_LENGTH) {
            b = false;
        } else {
            //                编译正则表达式
            Pattern p = Pattern.compile(REGEX_PHONE);
            //                对手机号进行匹配
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            b = isMatch;
        }
        return b;
    }

    /**
     * @param value
     * @return
     */
    /**
     * 查询字段限制长度length
     */
    public static boolean txtLength(int fieldTypeId, String value, int fieldValueLength) {
        return value.length() <= fieldValueLength;
    }

    public static boolean checkDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            /*判断格式（精确到秒）*/
            sdf.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean verifyEmail(String email) {
        boolean b = false;
        Pattern p = Pattern.compile(REGEX_EMAIL);
        Matcher m = p.matcher(email);
        boolean isMatch = m.matches();
        b = isMatch;
        return b;
    }

    /**
     * 校验用户真实姓名
     *
     * @param realName 真实姓名
     * @return
     */
    public static boolean checkRealName(String realName) {
        String regx = "^[\\u4E00-\\u9FA5]{2,10}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(realName);
        return matcher.find();
    }

}
