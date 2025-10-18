package com.lunarstra.quantum.model.converter;

import com.lunarstra.quantum.model.entity.UserOpenKey;
import com.lunarstra.quantum.model.request.AddUserOpenKeyRequest;
import com.lunarstra.quantum.model.request.UpdateUserOpenKeyRequest;
import com.lunarstra.quantum.model.response.UserOpenKeyResponse;

/**
 * 用户openKey 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */

public class UserOpenKeyConverter {

    /**
     * entity -> response
     *
     * @return
     */
    public static UserOpenKeyResponse entityConvert2Response(UserOpenKey userOpenKey) {
        if (userOpenKey == null) {
            return null;
        }
        UserOpenKeyResponse userOpenKeyResponse = new UserOpenKeyResponse();

        userOpenKeyResponse.setUserId(userOpenKey.getUserId());
        userOpenKeyResponse.setAccessKey(userOpenKey.getAccessKey());
        userOpenKeyResponse.setSecretKey(userOpenKey.getSecretKey());
        userOpenKeyResponse.setCreateTime(userOpenKey.getCreateTime());
        userOpenKeyResponse.setUpdateTime(userOpenKey.getUpdateTime());

        return userOpenKeyResponse;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static UserOpenKey addRequestConvert2Entity(AddUserOpenKeyRequest addUserOpenKeyRequest) {
        if (addUserOpenKeyRequest == null) {
            return null;
        }
        UserOpenKey userOpenKey = new UserOpenKey();

        userOpenKey.setUserId(addUserOpenKeyRequest.getUserId());
        userOpenKey.setAccessKey(addUserOpenKeyRequest.getAccessKey());
        userOpenKey.setSecretKey(addUserOpenKeyRequest.getSecretKey());

        return userOpenKey;
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static UserOpenKey updateRequestConvert2Entity(UpdateUserOpenKeyRequest updateUserOpenKeyRequest) {
        if (updateUserOpenKeyRequest == null) {
            return null;
        }
        UserOpenKey userOpenKey = new UserOpenKey();

        userOpenKey.setUserId(updateUserOpenKeyRequest.getUserId());
        userOpenKey.setAccessKey(updateUserOpenKeyRequest.getAccessKey());
        userOpenKey.setSecretKey(updateUserOpenKeyRequest.getSecretKey());

        return userOpenKey;
    }
}