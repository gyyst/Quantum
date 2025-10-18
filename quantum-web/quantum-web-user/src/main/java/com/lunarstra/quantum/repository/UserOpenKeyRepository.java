package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.mapper.UserOpenKeyMapper;
import com.lunarstra.quantum.model.entity.UserOpenKey;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户openKey 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@Repository
public class UserOpenKeyRepository extends ServiceImpl<UserOpenKeyMapper, UserOpenKey> {

}