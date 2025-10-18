package com.lunarstra.quantum.controller;

import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.constant.LimitType;
import com.lunarstra.quantum.model.request.AddUserRequest;
import com.lunarstra.quantum.model.request.UpdateUserRequest;
import com.lunarstra.quantum.model.response.UserResponse;
import com.lunarstra.quantum.service.UserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 控制层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@RestController
@Tag(name = "用户接口")
@RequestMapping("/")
@RedisLimit(redisKeyPrefix = "UserController", limitType = LimitType.METHOD)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户。
     *
     * @param addUserRequest 用户添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(summary = "保存用户", description = "保存用户")
    public BaseResponse<Boolean> save(
        @RequestBody @Validated @Parameter(description = "用户") AddUserRequest addUserRequest) {
        return BaseResponse.success(userService.save(addUserRequest));
    }

    /**
     * 根据主键删除用户。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除用户", description = "根据主键删除用户")
    public BaseResponse<Boolean> remove(@PathVariable @Parameter(description = "用户主键") Serializable id) {
        return BaseResponse.success(userService.removeById(id));
    }

    /**
     * 根据主键更新用户。
     *
     * @param updateUserRequest 用户修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新用户", description = "根据主键更新用户")
    public BaseResponse<Boolean> update(
        @RequestBody @Validated @Parameter(description = "用户主键") UpdateUserRequest updateUserRequest) {
        return BaseResponse.success(userService.updateById(updateUserRequest));
    }

    /**
     * 查询所有用户。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(summary = "查询所有用户", description = "查询所有用户")
    public BaseResponse<List<UserResponse>> list() {
        return BaseResponse.success(userService.listResponse());
    }

    /**
     * 根据用户主键获取详细信息。
     *
     * @param id 用户主键
     * @return 用户详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取用户", description = "根据主键获取用户")
    public BaseResponse<UserResponse> getInfo(@PathVariable Serializable id) {
        return BaseResponse.success(userService.getResponseById(id));
    }

    /**
     * 分页查询用户。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    @Operation(summary = "分页查询用户", description = "分页查询用户")
    public BaseResponse<Page<UserResponse>> page(
        @RequestBody @Validated @Parameter(description = "分页信息") PageRequest pageRequest) {
        return BaseResponse.success(userService.page(pageRequest));
    }

}