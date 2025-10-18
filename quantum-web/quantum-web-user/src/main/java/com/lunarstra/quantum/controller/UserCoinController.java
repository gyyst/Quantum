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
import com.lunarstra.quantum.model.entity.UserCoin;
import com.lunarstra.quantum.model.request.AddUserCoinRequest;
import com.lunarstra.quantum.model.request.UpdateUserCoinRequest;
import com.lunarstra.quantum.model.response.UserCoinResponse;
import com.lunarstra.quantum.service.UserCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 用户硬币 控制层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@RestController
@Tag(name = "用户硬币接口")
@RequestMapping("/userCoin")
@RedisLimit(redisKeyPrefix = "UserCoinController", limitType = LimitType.METHOD)
public class UserCoinController {

    @Autowired
    private UserCoinService userCoinService;

    /**
     * 添加用户硬币。
     *
     * @param addUserCoinRequest 用户硬币添加请求类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    @Operation(summary = "保存用户硬币", description="保存用户硬币")
    public BaseResponse<Boolean> save(@RequestBody @Validated @Parameter(description="用户硬币")AddUserCoinRequest addUserCoinRequest) {
        return BaseResponse.success(userCoinService.save(addUserCoinRequest));
    }

    /**
     * 根据主键删除用户硬币。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除用户硬币",description="根据主键删除用户硬币")
    public BaseResponse<Boolean> remove(@PathVariable @Parameter(description="用户硬币主键")Serializable id) {
        return BaseResponse.success(userCoinService.removeById(id));
    }

    /**
     * 根据主键更新用户硬币。
     *
     * @param updateUserCoinRequest 用户硬币修改请求类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新用户硬币",description="根据主键更新用户硬币")
    public BaseResponse<Boolean> update(@RequestBody @Validated @Parameter(description="用户硬币主键")UpdateUserCoinRequest updateUserCoinRequest) {
        return BaseResponse.success(userCoinService.updateById(updateUserCoinRequest));
    }

    /**
     * 查询所有用户硬币。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    @Operation(summary = "查询所有用户硬币",description="查询所有用户硬币")
    public BaseResponse<List<UserCoinResponse>> list() {
        return BaseResponse.success(userCoinService.listResponse());
    }

    /**
     * 根据用户硬币主键获取详细信息。
     *
     * @param id 用户硬币主键
     * @return 用户硬币详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取用户硬币",description="根据主键获取用户硬币")
    public BaseResponse<UserCoinResponse> getInfo(@PathVariable Serializable id) {
        return BaseResponse.success(userCoinService.getResponseById(id));
    }

    /**
     * 分页查询用户硬币。
     *
     * @param pageRequest 分页请求类
     * @return 分页对象
     */
    @PostMapping("page")
    @Operation(summary = "分页查询用户硬币",description="分页查询用户硬币")
    public BaseResponse<Page<UserCoinResponse>> page(@RequestBody @Validated @Parameter(description="分页信息")PageRequest pageRequest) {
        return BaseResponse.success(userCoinService.page(pageRequest));
    }

}