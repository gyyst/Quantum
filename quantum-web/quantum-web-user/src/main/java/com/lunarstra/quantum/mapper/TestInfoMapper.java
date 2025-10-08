package com.lunarstra.quantum.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.lunarstra.quantum.model.entity.TestInfo;

/**
 * 接口信息 映射层。
 *
 * @author lunarstra
 * @since 2025-10-08
 */
@Mapper
public interface TestInfoMapper extends BaseMapper<TestInfo> {

}
