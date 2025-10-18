package com.lunarstra.quantum.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.lunarstra.quantum.model.entity.UserCoin;

/**
 * 用户硬币 映射层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Mapper
public interface UserCoinMapper extends BaseMapper<UserCoin> {

}
