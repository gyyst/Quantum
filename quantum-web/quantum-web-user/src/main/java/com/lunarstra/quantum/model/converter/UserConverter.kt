package com.lunarstra.quantum.model.converter

import cn.dev33.satoken.stp.StpUtil
import com.lunarstra.quantum.model.bo.UserRegisterSendCodeBO
import com.lunarstra.quantum.model.entity.User
import com.lunarstra.quantum.model.request.AddUserRequest
import com.lunarstra.quantum.model.request.UpdateUserRequest
import com.lunarstra.quantum.model.request.UserRegisterRequest
import com.lunarstra.quantum.model.request.UserRegisterValidCodeSendRequest
import com.lunarstra.quantum.model.response.LoginUserResponse
import com.lunarstra.quantum.model.response.UserResponse
import com.lunarstra.quantum.utils.EncryptUtil

/**
 * 用户 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
object UserConverter {
    /**
     * entity -> response
     *
     * @return
     */
    @JvmStatic
    fun entityConvert2Response(user: User?): UserResponse? {
        if (user == null) {
            return null
        }
        val userResponse = UserResponse().apply {

            id = user.id
            account = user.account
            email = user.email
            phone = user.phone
            name = user.name
            avatar = user.avatar
            profile = user.profile
            state = user.state
            createTime = user.createTime
            updateTime = user.updateTime
        }

        return userResponse
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun userRegisterRequestConvert2Entity(userRegisterRequest: UserRegisterRequest?): User? {
        if (userRegisterRequest == null) {
            return null
        }
        val user = User().apply {

            account = userRegisterRequest.account
            password = EncryptUtil.encryptPassword(userRegisterRequest.password)
            email = userRegisterRequest.email
            phone = userRegisterRequest.phone
            name = userRegisterRequest.name
            avatar = userRegisterRequest.avatar
            profile = userRegisterRequest.profile
            state = User.UserState.NORMAL
        }

        return user
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun addRequestConvert2Entity(addUserRequest: AddUserRequest?): User? {
        if (addUserRequest == null) {
            return null
        }
        val user = User().apply {

            account = addUserRequest.account
            password = addUserRequest.password
            email = addUserRequest.email
            phone = addUserRequest.phone
            name = addUserRequest.name
            avatar = addUserRequest.avatar
            profile = addUserRequest.profile
            state = addUserRequest.state
        }

        return user
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun updateRequestConvert2Entity(updateUserRequest: UpdateUserRequest?): User? {
        if (updateUserRequest == null) {
            return null
        }
        val user = User().apply {

            id = updateUserRequest.id
            account = updateUserRequest.account
            password = updateUserRequest.password
            email = updateUserRequest.email
            phone = updateUserRequest.phone
            name = updateUserRequest.name
            avatar = updateUserRequest.avatar
            profile = updateUserRequest.profile
            state = updateUserRequest.state
        }

        return user
    }

    /**
     * User -> LoginUserResponse
     *
     * @param user
     * @return
     */
    @JvmStatic
    fun convertUser2LoginUserInfo(user: User): LoginUserResponse {
        val loginUserResponse = LoginUserResponse().apply {
            tokenInfo = StpUtil.getTokenInfo()
            id = user.id
            email = user.email
            phone = user.phone
            name = user.name
            avatar = user.avatar
            profile = user.avatar
            stateString = user.state.description
        }
        return loginUserResponse
    }

    @JvmStatic
    fun userRegisterRequestConvert2BO(
        userRegisterRequest: UserRegisterValidCodeSendRequest?, code: String?
    ): UserRegisterSendCodeBO? {
        if (userRegisterRequest == null) {
            return null
        }
        val userRegisterSendCodeBO = UserRegisterSendCodeBO().apply {
            registerEnum = userRegisterRequest.registerType
            address = userRegisterRequest.address
            this.code = code
        }

        return userRegisterSendCodeBO
    }
}