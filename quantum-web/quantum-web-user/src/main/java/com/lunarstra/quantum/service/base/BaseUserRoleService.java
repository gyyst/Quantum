package com.lunarstra.quantum.service.base;

import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.model.converter.UserRoleConverter;
import com.lunarstra.quantum.model.request.AddUserRoleRequest;
import com.lunarstra.quantum.model.request.UpdateUserRoleRequest;
import com.lunarstra.quantum.model.response.UserRoleResponse;
import com.lunarstra.quantum.repository.UserRoleRepository;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关系 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
public class BaseUserRoleService {
    @Resource
    protected UserRoleRepository userRoleRepository;

    /**
     * 保存用户角色关系
     *
     * @param addUserRoleRequest 新增用户角色关系
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(AddUserRoleRequest addUserRoleRequest) {
        return userRoleRepository.save(UserRoleConverter.addRequestConvert2Entity(addUserRoleRequest));
    }

    /**
     * 更新用户角色关系
     *
     * @param updateUserRoleRequest 更新用户角色关系
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(UpdateUserRoleRequest updateUserRoleRequest) {
        return userRoleRepository.updateById(UserRoleConverter.updateRequestConvert2Entity(updateUserRoleRequest));

    }

    /**
     * 查询所有用户角色关系
     *
     * @return 列表
     */
    public List<UserRoleResponse> listResponse() {
        return userRoleRepository.list().stream().map(UserRoleConverter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询用户角色关系
     *
     * @param id 主键
     * @return 用户角色关系
     */
    public UserRoleResponse getResponseById(Serializable id) {
        return UserRoleConverter.entityConvert2Response(userRoleRepository.getById(id));
    }

    /**
     * 根据id删除用户角色关系
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return userRoleRepository.removeById(id);
    }

    /**
     * 分页查询用户角色关系
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<UserRoleResponse> page(PageRequest pageRequest) {
        return userRoleRepository.pageAs(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
            new QueryWrapper(), UserRoleResponse.class);
    }

}