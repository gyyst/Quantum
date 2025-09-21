package com.lunarstra.quantum.constant;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/3 20:37
 */
public interface RedisConstant {
    /**
     * USER_REGISTER_EMAIL:<userEmail>
     */
    String USER_REGISTER_EMAIL = "user:register:email:";
    int USER_REGISTER_EMAIL_TTL_S = 300;
    /**
     * USER_SIGN:<userId>
     */
    String USER_SIGN = "user:sign:";
    int USER_SIGN_TTL_DAY = 33;
    /**
     * API_INVOKE:<apiId>:<userId>:<nonce>
     */
    String API_INVOKE_NONCE = "api:invoke:nonce";
    int API_INVOKE_NONCE_MIN = 15;
    /**
     * BANK_DETAIL:<bankId>
     */
    String BANK_DETAIL = "bank_detail:";
    int BANK_DETAIL_MIN = 60;
    /**
     * TEXT_QUESTION_DETAIL:<textQuestionId>
     */
    String TEXT_QUESTION_DETAIL = "text_question_detail:";
    int TEXT_QUESTION_DETAIL_MIN = 60;

    /**
     * 专栏审核任务队列
     */
    String QUEUE_COLUMN_DISTRIBUTE_MANAGER = "queue:column:distribute:manager";

    /**
     * 数据字典缓存表
     */
    String MAP_DATA_DICTIONARY = "map:data:dictionary";

}