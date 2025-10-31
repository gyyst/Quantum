package com.lunarstra.quantum.model.converter

import com.lunarstra.quantum.model.entity.UserRole
import com.lunarstra.quantum.model.request.AddUserRoleRequest
import com.lunarstra.quantum.model.request.UpdateUserRoleRequest
import com.lunarstra.quantum.model.response.UserRoleResponse

/**
 * 用户角色关系 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
object UserRoleConverter {
    /**
     * entity -> response
     *
     * @return
     */
    @JvmStatic
    fun entityConvert2Response(userRole: UserRole?): UserRoleResponse? {
        if (userRole == null) {
            return null
        }
        val userRoleResponse = UserRoleResponse().apply {

            userId = userRole.userId
            roleList = userRole.roleList
            createTime = userRole.createTime
            updateTime = userRole.updateTime
        }

        return userRoleResponse
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun addRequestConvert2Entity(addUserRoleRequest: AddUserRoleRequest?): UserRole? {
        if (addUserRoleRequest == null) {
            return null
        }
        val userRole = UserRole().apply {

            userId = addUserRoleRequest.userId
            roleList = addUserRoleRequest.roleList
        }

        return userRole
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun updateRequestConvert2Entity(updateUserRoleRequest: UpdateUserRoleRequest?): UserRole? {
        if (updateUserRoleRequest == null) {
            return null
        }
        val userRole = UserRole().apply {

            userId = updateUserRoleRequest.userId
            roleList = updateUserRoleRequest.roleList
        }

        return userRole
    }
}