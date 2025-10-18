package com.lunarstra.quantum.model.converter;

import com.lunarstra.quantum.model.entity.UserRole;
import com.lunarstra.quantum.model.request.AddUserRoleRequest;
import com.lunarstra.quantum.model.request.UpdateUserRoleRequest;
import com.lunarstra.quantum.model.response.UserRoleResponse;

/**
 * 用户角色关系 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */

public class UserRoleConverter {

    /**
     * entity -> response
     *
     * @return
     */
    public static UserRoleResponse entityConvert2Response(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleResponse userRoleResponse = new UserRoleResponse();

        userRoleResponse.setUserId(userRole.getUserId());
        userRoleResponse.setRoleList(userRole.getRoleList());
        userRoleResponse.setCreateTime(userRole.getCreateTime());
        userRoleResponse.setUpdateTime(userRole.getUpdateTime());

        return userRoleResponse;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static UserRole addRequestConvert2Entity(AddUserRoleRequest addUserRoleRequest) {
        if (addUserRoleRequest == null) {
            return null;
        }
        UserRole userRole = new UserRole();

        userRole.setUserId(addUserRoleRequest.getUserId());
        userRole.setRoleList(addUserRoleRequest.getRoleList());

        return userRole;
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static UserRole updateRequestConvert2Entity(UpdateUserRoleRequest updateUserRoleRequest) {
        if (updateUserRoleRequest == null) {
            return null;
        }
        UserRole userRole = new UserRole();

        userRole.setUserId(updateUserRoleRequest.getUserId());
        userRole.setRoleList(updateUserRoleRequest.getRoleList());

        return userRole;
    }
}