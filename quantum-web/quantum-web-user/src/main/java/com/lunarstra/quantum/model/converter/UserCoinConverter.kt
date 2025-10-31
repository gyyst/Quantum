package com.lunarstra.quantum.model.converter;

import com.lunarstra.quantum.model.entity.UserCoin;
import com.lunarstra.quantum.model.request.AddUserCoinRequest;
import com.lunarstra.quantum.model.request.UpdateUserCoinRequest;
import com.lunarstra.quantum.model.response.UserCoinResponse;

/**
 * 用户硬币 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */

public class UserCoinConverter {

    /**
     * entity -> response
     *
     * @return
     */
    public static UserCoinResponse entityConvert2Response(UserCoin userCoin) {
        if (userCoin == null) {
            return null;
        }
        UserCoinResponse userCoinResponse = new UserCoinResponse();

        userCoinResponse.setUserId(userCoin.getUserId());
        userCoinResponse.setCoinNum(userCoin.getCoinNum());
        userCoinResponse.setCreateTime(userCoin.getCreateTime());
        userCoinResponse.setUpdateTime(userCoin.getUpdateTime());

        return userCoinResponse;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static UserCoin addRequestConvert2Entity(AddUserCoinRequest addUserCoinRequest) {
        if (addUserCoinRequest == null) {
            return null;
        }
        UserCoin userCoin = new UserCoin();

        userCoin.setUserId(addUserCoinRequest.getUserId());
        userCoin.setCoinNum(addUserCoinRequest.getCoinNum());

        return userCoin;
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static UserCoin updateRequestConvert2Entity(UpdateUserCoinRequest updateUserCoinRequest) {
        if (updateUserCoinRequest == null) {
            return null;
        }
        UserCoin userCoin = new UserCoin();

        userCoin.setUserId(updateUserCoinRequest.getUserId());
        userCoin.setCoinNum(updateUserCoinRequest.getCoinNum());

        return userCoin;
    }
}