package com.chernobyl.gameengine.util;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.NativeResource;

import java.lang.annotation.Native;
import java.nio.ByteBuffer;

import static com.chernobyl.gameengine.Log.HB_CORE_ERROR;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class HBStruct extends org.lwjgl.system.Struct implements NativeResource {
    private static ByteBuffer buffer;
    static {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            buffer = stack.malloc(1);
        } catch (Exception e) {
            HB_CORE_ERROR("Could not allocate struct");
        }
    }

    public HBStruct() {
        super(memAddress(buffer), buffer);
    }

    @Override
    public int sizeof() {
        return Float.SIZE;
    }
}
