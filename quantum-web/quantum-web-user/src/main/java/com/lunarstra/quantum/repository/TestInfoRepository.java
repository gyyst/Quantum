package com.lunarstra.quantum.repository;

import com.lunarstra.quantum.mapper.TestInfoMapper;
import com.lunarstra.quantum.model.entity.TestInfo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 接口信息 数据访问层。
 *
 * @author lunarstra
 * @since 2025-10-08
 */
@Slf4j
@Repository
public class TestInfoRepository extends ServiceImpl<TestInfoMapper, TestInfo> {

}