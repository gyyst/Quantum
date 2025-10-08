package com.lunarstra.quantum.service.base;

import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.model.converter.TestInfoConverter;
import com.lunarstra.quantum.model.request.AddTestInfoRequest;
import com.lunarstra.quantum.model.request.UpdateTestInfoRequest;
import com.lunarstra.quantum.model.response.TestInfoResponse;
import com.lunarstra.quantum.repository.TestInfoRepository;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 接口信息 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-08
 */
@Service
@Slf4j
public class BaseTestInfoService {
    @Resource
    protected TestInfoRepository testInfoRepository;

    /**
     * 保存接口信息
     *
     * @param addTestInfoRequest 新增接口信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(AddTestInfoRequest addTestInfoRequest) {
        return testInfoRepository.save(TestInfoConverter.addRequestConvert2Entity(addTestInfoRequest));
    }

    /**
     * 更新接口信息
     *
     * @param updateTestInfoRequest 更新接口信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(UpdateTestInfoRequest updateTestInfoRequest) {
        return testInfoRepository.updateById(TestInfoConverter.updateRequestConvert2Entity(updateTestInfoRequest));

    }

    /**
     * 查询所有接口信息
     *
     * @return 列表
     */
    public List<TestInfoResponse> listResponse() {
        return testInfoRepository.list().stream().map(TestInfoConverter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询接口信息
     *
     * @param id 主键
     * @return 接口信息
     */
    public TestInfoResponse getResponseById(Serializable id) {
        return TestInfoConverter.entityConvert2Response(testInfoRepository.getById(id));
    }

    /**
     * 根据id删除接口信息
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return testInfoRepository.removeById(id);
    }

    /**
     * 分页查询接口信息
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<TestInfoResponse> page(PageRequest pageRequest) {
        return testInfoRepository.pageAs(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
            new QueryWrapper(), TestInfoResponse.class);
    }

}