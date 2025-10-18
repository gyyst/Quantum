package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.mapper.UserMapper;
import com.lunarstra.quantum.model.entity.User;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
    /**
     * 根据用户获取account
     *
     * @param account
     * @return
     */
    public User getUserByAccount(String account) {
        return null;
    }
}