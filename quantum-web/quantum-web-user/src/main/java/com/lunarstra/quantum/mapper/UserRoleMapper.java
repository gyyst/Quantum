package com.lunarstra.quantum.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.lunarstra.quantum.model.entity.UserRole;

/**
 * 用户角色关系 映射层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
