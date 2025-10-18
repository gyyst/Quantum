package com.lunarstra.quantum.service.base;

import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.model.converter.UserOpenKeyConverter;
import com.lunarstra.quantum.model.request.AddUserOpenKeyRequest;
import com.lunarstra.quantum.model.request.UpdateUserOpenKeyRequest;
import com.lunarstra.quantum.model.response.UserOpenKeyResponse;
import com.lunarstra.quantum.repository.UserOpenKeyRepository;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * 用户openKey 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
public class BaseUserOpenKeyService {
    @Resource
    protected UserOpenKeyRepository userOpenKeyRepository;

    /**
     * 保存用户openKey
     *
     * @param addUserOpenKeyRequest 新增用户openKey
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(AddUserOpenKeyRequest addUserOpenKeyRequest) {
        return userOpenKeyRepository.save(UserOpenKeyConverter.addRequestConvert2Entity(addUserOpenKeyRequest));
    }

    /**
     * 更新用户openKey
     *
     * @param updateUserOpenKeyRequest 更新用户openKey
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(UpdateUserOpenKeyRequest updateUserOpenKeyRequest) {
        return userOpenKeyRepository.updateById(
            UserOpenKeyConverter.updateRequestConvert2Entity(updateUserOpenKeyRequest));

    }

    /**
     * 查询所有用户openKey
     *
     * @return 列表
     */
    public List<UserOpenKeyResponse> listResponse() {
        return userOpenKeyRepository.list().stream().map(UserOpenKeyConverter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询用户openKey
     *
     * @param id 主键
     * @return 用户openKey
     */
    public UserOpenKeyResponse getResponseById(Serializable id) {
        return UserOpenKeyConverter.entityConvert2Response(userOpenKeyRepository.getById(id));
    }

    /**
     * 根据id删除用户openKey
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return userOpenKeyRepository.removeById(id);
    }

    /**
     * 分页查询用户openKey
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<UserOpenKeyResponse> page(PageRequest pageRequest) {
        return userOpenKeyRepository.pageAs(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
            new QueryWrapper(), UserOpenKeyResponse.class);
    }

}