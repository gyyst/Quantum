package com.lunarstra.quantum.model.converter

import com.lunarstra.quantum.model.entity.UserOpenKey
import com.lunarstra.quantum.model.request.AddUserOpenKeyRequest
import com.lunarstra.quantum.model.request.UpdateUserOpenKeyRequest
import com.lunarstra.quantum.model.response.UserOpenKeyResponse

/**
 * 用户openKey 转换类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
object UserOpenKeyConverter {
    /**
     * entity -> response
     *
     * @return
     */
    @JvmStatic
    fun entityConvert2Response(userOpenKey: UserOpenKey?): UserOpenKeyResponse? {
        if (userOpenKey == null) {
            return null
        }
        val userOpenKeyResponse = UserOpenKeyResponse().apply {

            userId = userOpenKey.userId
            accessKey = userOpenKey.accessKey
            secretKey = userOpenKey.secretKey
            createTime = userOpenKey.createTime
            updateTime = userOpenKey.updateTime
        }

        return userOpenKeyResponse
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun addRequestConvert2Entity(addUserOpenKeyRequest: AddUserOpenKeyRequest?): UserOpenKey? {
        if (addUserOpenKeyRequest == null) {
            return null
        }
        val userOpenKey = UserOpenKey().apply {
            userId = addUserOpenKeyRequest.userId
            accessKey = addUserOpenKeyRequest.accessKey
            secretKey = addUserOpenKeyRequest.secretKey
        }



        return userOpenKey
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    @JvmStatic
    fun updateRequestConvert2Entity(updateUserOpenKeyRequest: UpdateUserOpenKeyRequest?): UserOpenKey? {
        if (updateUserOpenKeyRequest == null) {
            return null
        }
        val userOpenKey = UserOpenKey().apply {
            userId = updateUserOpenKeyRequest.userId
            accessKey = updateUserOpenKeyRequest.accessKey
            secretKey = updateUserOpenKeyRequest.secretKey

        }

        return userOpenKey
    }
}