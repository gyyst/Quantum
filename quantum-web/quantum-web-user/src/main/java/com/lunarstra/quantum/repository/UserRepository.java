package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.exception.ThrowUtils;
import com.lunarstra.quantum.mapper.UserMapper;
import com.lunarstra.quantum.model.entity.User;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
}