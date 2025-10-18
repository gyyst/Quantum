package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.mapper.UserRoleMapper;
import com.lunarstra.quantum.model.entity.UserRole;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关系 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@Repository
public class UserRoleRepository extends ServiceImpl<UserRoleMapper, UserRole> {

}