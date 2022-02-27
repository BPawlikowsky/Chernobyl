package com.chernobyl.gameengine.core.window;

import com.chernobyl.platform.linux.LinuxWindow;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.chernobyl.gameengine.core.Asserts.HB_CORE_ASSERT;

public abstract class Window {
    @Getter
    protected long nativeWindow;
    @Getter
    protected String title;
    @Getter @Setter
    protected int width, height;
    protected static Window m_Window = null;
    public abstract void OnUpdate();
    public abstract void Shutdown();

    // Window attributes
    public abstract void SetEventCallback(IEventCallback callback);
    public abstract void SetVSync(boolean enabled);
    public abstract boolean IsVSync();
    @Getter
    private static final WindowPlatform s_platform = WindowPlatform.Linux;

    public static Window Create()
    {
        switch (s_platform){
            case Linux -> { return LinuxWindow.get(); }
            case None -> { return null; }
        }

        HB_CORE_ASSERT(false, "Unknown platform!");
        return null;
    }

    public static Window Create(WindowProps props)
    {
        switch (s_platform){
            case Linux -> { return LinuxWindow.get(props); }
            case None -> { return null; }
        }

        HB_CORE_ASSERT(false, "Unknown platform!");
        return null;
    }

    public enum WindowPlatform {
        Windows, Linux, MacOS, None
    }
}
