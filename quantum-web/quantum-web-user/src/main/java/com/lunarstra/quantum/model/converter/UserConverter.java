package com.lunarstra.quantum.model.converter;

import cn.dev33.satoken.stp.StpUtil;
import com.lunarstra.quantum.model.entity.User;
import com.lunarstra.quantum.model.request.AddUserRequest;
import com.lunarstra.quantum.model.request.UpdateUserRequest;
import com.lunarstra.quantum.model.request.UserRegisterRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.model.response.UserResponse;

/**
 * 用户 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */

public class UserConverter {

    /**
     * entity -> response
     *
     * @return
     */
    public static UserResponse entityConvert2Response(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setAccount(user.getAccount());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setName(user.getName());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setProfile(user.getProfile());
        userResponse.setState(user.getState());
        userResponse.setCreateTime(user.getCreateTime());
        userResponse.setUpdateTime(user.getUpdateTime());

        return userResponse;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static User userRegisterRequestConvert2Entity(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        User user = new User();

        user.setAccount(userRegisterRequest.getAccount());
        user.setPassword(userRegisterRequest.getPassword());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPhone(userRegisterRequest.getPhone());
        user.setName(userRegisterRequest.getName());
        user.setAvatar(userRegisterRequest.getAvatar());
        user.setProfile(userRegisterRequest.getProfile());
        user.setState(User.UserState.NORMAL);

        return user;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static User addRequestConvert2Entity(AddUserRequest addUserRequest) {
        if (addUserRequest == null) {
            return null;
        }
        User user = new User();

        user.setAccount(addUserRequest.getAccount());
        user.setPassword(addUserRequest.getPassword());
        user.setEmail(addUserRequest.getEmail());
        user.setPhone(addUserRequest.getPhone());
        user.setName(addUserRequest.getName());
        user.setAvatar(addUserRequest.getAvatar());
        user.setProfile(addUserRequest.getProfile());
        user.setState(addUserRequest.getState());

        return user;
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static User updateRequestConvert2Entity(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) {
            return null;
        }
        User user = new User();

        user.setId(updateUserRequest.getId());
        user.setAccount(updateUserRequest.getAccount());
        user.setPassword(updateUserRequest.getPassword());
        user.setEmail(updateUserRequest.getEmail());
        user.setPhone(updateUserRequest.getPhone());
        user.setName(updateUserRequest.getName());
        user.setAvatar(updateUserRequest.getAvatar());
        user.setProfile(updateUserRequest.getProfile());
        user.setState(updateUserRequest.getState());

        return user;
    }

    /**
     * User -> LoginUserResponse
     *
     * @param user
     * @return
     */
    public static LoginUserResponse convertUser2LoginUserInfo(User user) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setTokenInfo(StpUtil.getTokenInfo());
        loginUserResponse.setId(user.getId());
        loginUserResponse.setEmail(user.getEmail());
        loginUserResponse.setPhone(user.getPhone());
        loginUserResponse.setName(user.getName());
        loginUserResponse.setAvatar(user.getAvatar());
        loginUserResponse.setProfile(user.getAvatar());
        loginUserResponse.setStateString(user.getState().getDescription());
        return loginUserResponse;
    }
}