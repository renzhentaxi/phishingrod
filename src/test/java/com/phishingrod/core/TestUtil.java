package com.phishingrod.core;

import java.util.HashMap;
import java.util.Map;

public class TestUtil
{
    public final static String TEST_NAME = "testName";
    public final static String TEST_CHANGED_NAME = "changedTestName";
    public static final String TEST_HTML = "<body>TEST_HTML</body>";

    public final static String TEST_EMAIL_ADDRESS = "test@test.com";
    public final static String TEST_CHANGED_EMAIL_ADDRESS = "changedTest@test.com";

    public final static String TEST_PARAM_NAME = "paramName";
    public final static String TEST_PARAM_VALUE = "paramValue";
    public final static String TEST_NEW_PARAM_NAME = "newParamName";
    public final static String TEST_NEW_PARAM_VALUE = "newParamValue";

    public static Map<String, String> TEST_PARAMETER_MAP()
    {
        return convertToMap(TEST_PARAM_NAME, TEST_PARAM_VALUE);
    }

    public static Map<String, String> convertToMap(String... paramData)
    {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < paramData.length; i += 2)
        {
            map.put(paramData[i], paramData[i + 1]);
        }
        return map;
    }
}
