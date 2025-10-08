package com.lunarstra.quantum.model.converter;

import com.lunarstra.quantum.model.entity.TestInfo;
import com.lunarstra.quantum.model.request.AddTestInfoRequest;
import com.lunarstra.quantum.model.request.UpdateTestInfoRequest;
import com.lunarstra.quantum.model.response.TestInfoResponse;

/**
 * 接口信息 转换类。
 *
 * @author lunarstra
 * @since 2025-10-08
 */

public class TestInfoConverter {

    /**
     * entity -> response
     *
     * @return
     */
    public static TestInfoResponse entityConvert2Response(TestInfo testInfo) {
        if (testInfo == null) {
            return null;
        }
        TestInfoResponse testInfoResponse = new TestInfoResponse();

        testInfoResponse.setId(testInfo.getId());
        testInfoResponse.setName(testInfo.getName());
        testInfoResponse.setCreateTime(testInfo.getCreateTime());
        testInfoResponse.setUpdateTime(testInfo.getUpdateTime());

        return testInfoResponse;
    }

    /**
     * addRequest -> entity
     *
     * @return
     */
    public static TestInfo addRequestConvert2Entity(AddTestInfoRequest addTestInfoRequest) {
        if (addTestInfoRequest == null) {
            return null;
        }
        TestInfo testInfo = new TestInfo();

        testInfo.setName(addTestInfoRequest.getName());

        return testInfo;
    }

    /**
     * updateRequest -> entity
     *
     * @return
     */
    public static TestInfo updateRequestConvert2Entity(UpdateTestInfoRequest updateTestInfoRequest) {
        if (updateTestInfoRequest == null) {
            return null;
        }
        TestInfo testInfo = new TestInfo();

        testInfo.setId(updateTestInfoRequest.getId());
        testInfo.setName(updateTestInfoRequest.getName());

        return testInfo;
    }
}