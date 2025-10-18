package com.lunarstra.quantum.service.base;

import com.lunarstra.quantum.common.PageRequest;
import com.lunarstra.quantum.model.converter.UserCoinConverter;
import com.lunarstra.quantum.model.request.AddUserCoinRequest;
import com.lunarstra.quantum.model.request.UpdateUserCoinRequest;
import com.lunarstra.quantum.model.response.UserCoinResponse;
import com.lunarstra.quantum.repository.UserCoinRepository;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * 用户硬币 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
public class BaseUserCoinService {
    @Resource
    protected UserCoinRepository userCoinRepository;

    /**
     * 保存用户硬币
     *
     * @param addUserCoinRequest 新增用户硬币
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    public Boolean save(AddUserCoinRequest addUserCoinRequest) {
        return userCoinRepository.save(UserCoinConverter.addRequestConvert2Entity(addUserCoinRequest));
    }

    /**
     * 更新用户硬币
     *
     * @param updateUserCoinRequest 更新用户硬币
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    public Boolean updateById(UpdateUserCoinRequest updateUserCoinRequest) {
        return userCoinRepository.updateById(UserCoinConverter.updateRequestConvert2Entity(updateUserCoinRequest));

    }

    /**
     * 查询所有用户硬币
     *
     * @return 列表
     */
    public List<UserCoinResponse> listResponse() {
        return userCoinRepository.list().stream().map(UserCoinConverter::entityConvert2Response).toList();
    }

    /**
     * 根据id查询用户硬币
     *
     * @param id 主键
     * @return 用户硬币
     */
    public UserCoinResponse getResponseById(Serializable id) {
        return UserCoinConverter.entityConvert2Response(userCoinRepository.getById(id));
    }

    /**
     * 根据id删除用户硬币
     *
     * @return 列表
     */
    public Boolean removeById(Serializable id) {
        return userCoinRepository.removeById(id);
    }

    /**
     * 分页查询用户硬币
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public Page<UserCoinResponse> page(PageRequest pageRequest) {
        return userCoinRepository.pageAs(new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize()),
            new QueryWrapper(), UserCoinResponse.class);
    }

}