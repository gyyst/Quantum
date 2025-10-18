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
import com.lunarstra.quantum.model.entity.UserOpenKey;
import com.lunarstra.quantum.model.request.AddUserOpenKeyRequest;
import com.lunarstra.quantum.model.request.UpdateUserOpenKeyRequest;
import com.lunarstra.quantum.model.response.UserOpenKeyResponse;
import com.lunarstra.quantum.service.UserOpenKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 用户openKey 控制层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@RestController
@Tag(name = "用户openKey接口")
@RequestMapping("/userOpenKey")
@RedisLimit(redisKeyPrefix = "UserOpenKeyController", limitType = LimitType.METHOD)
public class UserOpenKeyController {

    @Autowired
    private UserOpenKeyService userOpenKeyService;

    /**
     * 添加用户openKey。
     *
     * @param addUserOpenKeyRequest 用户openKey添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(summary = "保存用户openKey", description="保存用户openKey")
    public BaseResponse<Boolean> save(@RequestBody @Validated @Parameter(description="用户openKey")AddUserOpenKeyRequest addUserOpenKeyRequest) {
        return BaseResponse.success(userOpenKeyService.save(addUserOpenKeyRequest));
    }

    /**
     * 根据主键删除用户openKey。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除用户openKey",description="根据主键删除用户openKey")
    public BaseResponse<Boolean> remove(@PathVariable @Parameter(description="用户openKey主键")Serializable id) {
        return BaseResponse.success(userOpenKeyService.removeById(id));
    }

    /**
     * 根据主键更新用户openKey。
     *
     * @param updateUserOpenKeyRequest 用户openKey修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新用户openKey",description="根据主键更新用户openKey")
    public BaseResponse<Boolean> update(@RequestBody @Validated @Parameter(description="用户openKey主键")UpdateUserOpenKeyRequest updateUserOpenKeyRequest) {
        return BaseResponse.success(userOpenKeyService.updateById(updateUserOpenKeyRequest));
    }

    /**
     * 查询所有用户openKey。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(summary = "查询所有用户openKey",description="查询所有用户openKey")
    public BaseResponse<List<UserOpenKeyResponse>> list() {
        return BaseResponse.success(userOpenKeyService.listResponse());
    }

    /**
     * 根据用户openKey主键获取详细信息。
     *
     * @param id 用户openKey主键
     * @return 用户openKey详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取用户openKey",description="根据主键获取用户openKey")
    public BaseResponse<UserOpenKeyResponse> getInfo(@PathVariable Serializable id) {
        return BaseResponse.success(userOpenKeyService.getResponseById(id));
    }

    /**
     * 分页查询用户openKey。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    @Operation(summary = "分页查询用户openKey",description="分页查询用户openKey")
    public BaseResponse<Page<UserOpenKeyResponse>> page(@RequestBody @Validated @Parameter(description="分页信息")PageRequest pageRequest) {
        return BaseResponse.success(userOpenKeyService.page(pageRequest));
    }

}