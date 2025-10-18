package com.lunarstra.quantum.service.base;

import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.model.converter.UserConverter;
import com.lunarstra.quantum.model.request.AddUserRequest;
import com.lunarstra.quantum.model.request.UpdateUserRequest;
import com.lunarstra.quantum.model.response.UserResponse;
import com.lunarstra.quantum.repository.UserRepository;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
public class BaseUserService {
    @Resource
    protected UserRepository userRepository;

    /**
     * 保存用户
     *
     * @param addUserRequest 新增用户
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(AddUserRequest addUserRequest) {
        return userRepository.save(UserConverter.addRequestConvert2Entity(addUserRequest));
    }

    /**
     * 更新用户
     *
     * @param updateUserRequest 更新用户
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(UpdateUserRequest updateUserRequest) {
        return userRepository.updateById(UserConverter.updateRequestConvert2Entity(updateUserRequest));

    }

    /**
     * 查询所有用户
     *
     * @return 列表
     */
    public List<UserResponse> listResponse() {
        return userRepository.list().stream().map(UserConverter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询用户
     *
     * @param id 主键
     * @return 用户
     */
    public UserResponse getResponseById(Serializable id) {
        return UserConverter.entityConvert2Response(userRepository.getById(id));
    }

    /**
     * 根据id删除用户
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return userRepository.removeById(id);
    }

    /**
     * 分页查询用户
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<UserResponse> page(PageRequest pageRequest) {
        return userRepository.pageAs(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
            new QueryWrapper(), UserResponse.class);
    }

}