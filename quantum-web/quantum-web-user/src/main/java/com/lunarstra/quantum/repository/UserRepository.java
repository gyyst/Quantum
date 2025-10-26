package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.constant.EmailTemplate;
import com.lunarstra.quantum.constant.RedisConstant;
import com.lunarstra.quantum.exception.ThrowUtils;
import com.lunarstra.quantum.mapper.UserMapper;
import com.lunarstra.quantum.model.entity.User;
import com.lunarstra.quantum.model.request.UserRegisterValidCodeSendRequest;
import com.lunarstra.quantum.utils.RedisCache;
import com.lunarstra.quantum.utils.SendMailUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.lunarstra.quantum.model.entity.table.UserTableDef.USER;

/**
 * 用户 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
    @Resource
    private SendMailUtils sendMailUtils;

    @Resource
    private RedisCache redisCache;

    /**
     * 根据用户获取account
     *
     * @param account
     * @return
     */
    public User getUserByAccount(String account) {
        QueryWrapper queryWrapper = new QueryWrapper().from(USER).where(USER.ACCOUNT.eq(account));
        User user = this.getOne(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return user;
    }

    public boolean checkAccountExist(String account) {
        QueryWrapper queryWrapper = new QueryWrapper().from(USER).where(USER.ACCOUNT.eq(account));
        return this.exists(queryWrapper);
    }

    /**
     * 发送验证码，根据request选择不同渠道发送
     *
     * @param request
     * @return
     */
    public boolean validCodeSend(UserRegisterValidCodeSendRequest request) {
        int validCode = new Random().nextInt(1000000);
        log.info("生成验证码:{}", validCode);
        String target;
        switch (request.getRegisterType()) {
            case email -> {
                target = request.getEmail();
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", null);
                map.put("verificationUrl", null);
                sendMailUtils.sendTemplateMail(target, EmailTemplate.REGISTER_VERIFICATION, map);
            }
            default -> throw new IllegalStateException("Unexpected value: " + request.getRegisterType());
        }
        redisCache.setCacheObject(RedisCache.buildKey(RedisConstant.USER_REGISTER_EMAIL, target), validCode,
            RedisConstant.USER_REGISTER_EMAIL_TTL_S, TimeUnit.SECONDS);
        return true;
    }
}