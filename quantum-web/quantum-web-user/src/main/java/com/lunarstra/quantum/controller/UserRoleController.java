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
import com.lunarstra.quantum.model.entity.UserRole;
import com.lunarstra.quantum.model.request.AddUserRoleRequest;
import com.lunarstra.quantum.model.request.UpdateUserRoleRequest;
import com.lunarstra.quantum.model.response.UserRoleResponse;
import com.lunarstra.quantum.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关系 控制层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@RestController
@Tag(name = "用户角色关系接口")
@RequestMapping("/userRole")
@RedisLimit(redisKeyPrefix = "UserRoleController", limitType = LimitType.METHOD)
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户角色关系。
     *
     * @param addUserRoleRequest 用户角色关系添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(summary = "保存用户角色关系", description="保存用户角色关系")
    public BaseResponse<Boolean> save(@RequestBody @Validated @Parameter(description="用户角色关系")AddUserRoleRequest addUserRoleRequest) {
        return BaseResponse.success(userRoleService.save(addUserRoleRequest));
    }

    /**
     * 根据主键删除用户角色关系。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除用户角色关系",description="根据主键删除用户角色关系")
    public BaseResponse<Boolean> remove(@PathVariable @Parameter(description="用户角色关系主键")Serializable id) {
        return BaseResponse.success(userRoleService.removeById(id));
    }

    /**
     * 根据主键更新用户角色关系。
     *
     * @param updateUserRoleRequest 用户角色关系修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新用户角色关系",description="根据主键更新用户角色关系")
    public BaseResponse<Boolean> update(@RequestBody @Validated @Parameter(description="用户角色关系主键")UpdateUserRoleRequest updateUserRoleRequest) {
        return BaseResponse.success(userRoleService.updateById(updateUserRoleRequest));
    }

    /**
     * 查询所有用户角色关系。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(summary = "查询所有用户角色关系",description="查询所有用户角色关系")
    public BaseResponse<List<UserRoleResponse>> list() {
        return BaseResponse.success(userRoleService.listResponse());
    }

    /**
     * 根据用户角色关系主键获取详细信息。
     *
     * @param id 用户角色关系主键
     * @return 用户角色关系详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取用户角色关系",description="根据主键获取用户角色关系")
    public BaseResponse<UserRoleResponse> getInfo(@PathVariable Serializable id) {
        return BaseResponse.success(userRoleService.getResponseById(id));
    }

    /**
     * 分页查询用户角色关系。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    @Operation(summary = "分页查询用户角色关系",description="分页查询用户角色关系")
    public BaseResponse<Page<UserRoleResponse>> page(@RequestBody @Validated @Parameter(description="分页信息")PageRequest pageRequest) {
        return BaseResponse.success(userRoleService.page(pageRequest));
    }

}