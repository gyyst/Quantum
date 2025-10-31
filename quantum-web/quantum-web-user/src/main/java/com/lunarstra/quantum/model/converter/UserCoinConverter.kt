package com.lunarstra.quantum.model.converter

import com.lunarstra.quantum.model.entity.UserCoin
import com.lunarstra.quantum.model.request.AddUserCoinRequest
import com.lunarstra.quantum.model.request.UpdateUserCoinRequest
import com.lunarstra.quantum.model.response.UserCoinResponse

/**
 * 用户硬币 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
object UserCoinConverter {
    /**
     * entity -> response
     *
     * @return
     */
    @JvmStatic
    fun entityConvert2Response(userCoin: UserCoin?): UserCoinResponse? {
        if (userCoin == null) {
            return null
        }
        val userCoinResponse = UserCoinResponse().apply {

            userId = userCoin.userId
            coinNum = userCoin.coinNum
            createTime = userCoin.createTime
            updateTime = userCoin.updateTime
        }

        return userCoinResponse
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun addRequestConvert2Entity(addUserCoinRequest: AddUserCoinRequest?): UserCoin? {
        if (addUserCoinRequest == null) {
            return null
        }
        val userCoin = UserCoin().apply {

            userId = addUserCoinRequest.userId
            coinNum = addUserCoinRequest.coinNum
        }

        return userCoin
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun updateRequestConvert2Entity(updateUserCoinRequest: UpdateUserCoinRequest?): UserCoin? {
        if (updateUserCoinRequest == null) {
            return null
        }
        val userCoin = UserCoin().apply {

            userId = updateUserCoinRequest.userId
            coinNum = updateUserCoinRequest.coinNum
        }

        return userCoin
    }
}