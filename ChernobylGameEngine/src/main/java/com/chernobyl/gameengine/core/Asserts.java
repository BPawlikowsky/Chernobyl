package com.chernobyl.gameengine.core;

import static com.chernobyl.gameengine.core.Log.HB_CORE_ERROR;
import static com.chernobyl.gameengine.core.Log.HB_ERROR;

public class Asserts {
    public static void HB_ASSERT(boolean result, String errorMessage) {
        if(!result) HB_ERROR(errorMessage);
        assert result : errorMessage;
    }

    public static void HB_CORE_ASSERT(boolean result, String errorMessage) {
        if(!result) HB_CORE_ERROR(errorMessage);
        assert result : errorMessage;
    }
}
