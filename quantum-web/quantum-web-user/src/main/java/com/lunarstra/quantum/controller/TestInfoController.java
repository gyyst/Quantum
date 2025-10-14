package com.lunarstra.quantum.controller;

import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.constant.LimitType;
import com.mybatisflex.core.paginate.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.lunarstra.quantum.model.entity.TestInfo;
import com.lunarstra.quantum.model.request.AddTestInfoRequest;
import com.lunarstra.quantum.model.request.UpdateTestInfoRequest;
import com.lunarstra.quantum.model.response.TestInfoResponse;
import com.lunarstra.quantum.service.TestInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 接口信息 控制层。
 *
 * @author lunarstra
 * @since 2025-10-12
 */
@Slf4j
@RestController
@Tag(name = "接口信息接口")
@RequestMapping("/testInfo")
@RedisLimit(redisKeyPrefix = "TestInfoController", limitType = LimitType.METHOD)
public class TestInfoController {

    @Autowired
    private TestInfoService testInfoService;

    /**
     * 添加接口信息。
     *
     * @param addTestInfoRequest 接口信息添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(summary = "保存接口信息", description="保存接口信息")
    public BaseResponse<Boolean> save(@RequestBody @Validated @Parameter(description="接口信息")AddTestInfoRequest addTestInfoRequest) {
        return BaseResponse.success(testInfoService.save(addTestInfoRequest));
    }

    /**
     * 根据主键删除接口信息。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除接口信息",description="根据主键删除接口信息")
    public BaseResponse<Boolean> remove(@PathVariable @Parameter(description="接口信息主键")Serializable id) {
        return BaseResponse.success(testInfoService.removeById(id));
    }

    /**
     * 根据主键更新接口信息。
     *
     * @param updateTestInfoRequest 接口信息修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新接口信息",description="根据主键更新接口信息")
    public BaseResponse<Boolean> update(@RequestBody @Validated @Parameter(description="接口信息主键")UpdateTestInfoRequest updateTestInfoRequest) {
        return BaseResponse.success(testInfoService.updateById(updateTestInfoRequest));
    }

    /**
     * 查询所有接口信息。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(summary = "查询所有接口信息",description="查询所有接口信息")
    public BaseResponse<List<TestInfoResponse>> list() {
        return BaseResponse.success(testInfoService.listResponse());
    }

    /**
     * 根据接口信息主键获取详细信息。
     *
     * @param id 接口信息主键
     * @return 接口信息详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取接口信息",description="根据主键获取接口信息")
    public BaseResponse<TestInfoResponse> getInfo(@PathVariable Serializable id) {
        return BaseResponse.success(testInfoService.getResponseById(id));
    }

    /**
     * 分页查询接口信息。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    @Operation(summary = "分页查询接口信息",description="分页查询接口信息")
    public BaseResponse<Page<TestInfoResponse>> page(@RequestBody @Validated @Parameter(description="分页信息")PageRequest pageRequest) {
        return BaseResponse.success(testInfoService.page(pageRequest));
    }

}