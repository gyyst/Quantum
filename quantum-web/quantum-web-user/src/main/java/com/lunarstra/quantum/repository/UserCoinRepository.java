package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.mapper.UserCoinMapper;
import com.lunarstra.quantum.model.entity.UserCoin;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户硬币 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@Repository
public class UserCoinRepository extends ServiceImpl<UserCoinMapper, UserCoin> {

}