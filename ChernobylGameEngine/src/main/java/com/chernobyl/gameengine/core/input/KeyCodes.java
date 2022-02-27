package com.chernobyl.gameengine.core.input;

public class KeyCodes {
    // GLFW.java
    public static final int
     HB_KEY_SPACE           = KeyCode.Space.key(),
     HB_KEY_APOSTROPHE      = KeyCode.Apostrophe.key(), /* ' */
     HB_KEY_COMMA           = KeyCode.Comma.key(),      /* , */
     HB_KEY_MINUS           = KeyCode.Minus.key(),      /* - */
     HB_KEY_PERIOD          = KeyCode.Period.key(),     /* . */
     HB_KEY_SLASH           = KeyCode.Slash.key(),      /* / */
     HB_KEY_0               = KeyCode.D0.key(),
     HB_KEY_1               = KeyCode.D1.key(),
     HB_KEY_2               = KeyCode.D2.key(),
     HB_KEY_3               = KeyCode.D3.key(),
     HB_KEY_4               = KeyCode.D4.key(),
     HB_KEY_5               = KeyCode.D5.key(),
     HB_KEY_6               = KeyCode.D6.key(),
     HB_KEY_7               = KeyCode.D7.key(),
     HB_KEY_8               = KeyCode.D8.key(),
     HB_KEY_9               = KeyCode.D9.key(),
     HB_KEY_SEMICOLON       = KeyCode.Semicolon.key(),   /* ; */
     HB_KEY_EQUAL           = KeyCode.Equal.key(),       /* = */
     HB_KEY_A               = KeyCode.A.key(),
     HB_KEY_B               = KeyCode.B.key(),
     HB_KEY_C               = KeyCode.C.key(),
     HB_KEY_D               = KeyCode.D.key(),
     HB_KEY_E               = KeyCode.E.key(),
     HB_KEY_F               = KeyCode.F.key(),
     HB_KEY_G               = KeyCode.G.key(),
     HB_KEY_H               = KeyCode.H.key(),
     HB_KEY_I               = KeyCode.I.key(),
     HB_KEY_J               = KeyCode.J.key(),
     HB_KEY_K               = KeyCode.K.key(),
     HB_KEY_L               = KeyCode.L.key(),
     HB_KEY_M               = KeyCode.M.key(),
     HB_KEY_N               = KeyCode.N.key(),
     HB_KEY_O               = KeyCode.O.key(),
     HB_KEY_P               = KeyCode.P.key(),
     HB_KEY_Q               = KeyCode.Q.key(),
     HB_KEY_R               = KeyCode.R.key(),
     HB_KEY_S               = KeyCode.S.key(),
     HB_KEY_T               = KeyCode.T.key(),
     HB_KEY_U               = KeyCode.U.key(),
     HB_KEY_V               = KeyCode.V.key(),
     HB_KEY_W               = KeyCode.W.key(),
     HB_KEY_X               = KeyCode.X.key(),
     HB_KEY_Y               = KeyCode.Y.key(),
     HB_KEY_Z               = KeyCode.Z.key(),
     HB_KEY_LEFT_BRACKET    = KeyCode.LeftBracket.key(),  /* [ */
     HB_KEY_BACKSLASH       = KeyCode.Backslash.key(),    /* \ */
     HB_KEY_RIGHT_BRACKET   = KeyCode.RightBracket.key(), /* ] */
     HB_KEY_GRAVE_ACCENT    = KeyCode.GraveAccent .key(), /* ` */
     HB_KEY_WORLD_1         = KeyCode.World1.key(),       /* non-US #1 */
     HB_KEY_WORLD_2         = KeyCode.World2.key(),       /* non-US #2 */

   /* Function keys */
     HB_KEY_ESCAPE          = KeyCode.Escape.key(),
     HB_KEY_ENTER           = KeyCode.Enter.key(),
     HB_KEY_TAB             = KeyCode.Tab.key(),
     HB_KEY_BACKSPACE       = KeyCode.Backspace.key(),
     HB_KEY_INSERT          = KeyCode.Insert.key(),
     HB_KEY_DELETE          = KeyCode.Delete.key(),
     HB_KEY_RIGHT           = KeyCode.Right.key(),
     HB_KEY_LEFT            = KeyCode.Left.key(),
     HB_KEY_DOWN            = KeyCode.Down.key(),
     HB_KEY_UP              = KeyCode.Up .key(),
     HB_KEY_PAGE_UP         = KeyCode.PageUp.key(),
     HB_KEY_PAGE_DOWN       = KeyCode.PageDown.key(),
     HB_KEY_HOME            = KeyCode.Home.key(),
     HB_KEY_END             = KeyCode.End.key(),
     HB_KEY_CAPS_LOCK       = KeyCode.CapsLock.key(),
     HB_KEY_SCROLL_LOCK     = KeyCode.ScrollLock.key(),
     HB_KEY_NUM_LOCK        = KeyCode.NumLock.key(),
     HB_KEY_PRINT_SCREEN    = KeyCode.PrintScreen.key(),
     HB_KEY_PAUSE           = KeyCode.Pause.key(),
     HB_KEY_F1              = KeyCode.F1.key(),
     HB_KEY_F2              = KeyCode.F2.key(),
     HB_KEY_F3              = KeyCode.F3.key(),
     HB_KEY_F4              = KeyCode.F4.key(),
     HB_KEY_F5              = KeyCode.F5.key(),
     HB_KEY_F6              = KeyCode.F6.key(),
     HB_KEY_F7              = KeyCode.F7.key(),
     HB_KEY_F8              = KeyCode.F8.key(),
     HB_KEY_F9              = KeyCode.F9.key(),
     HB_KEY_F10             = KeyCode.F10.key(),
     HB_KEY_F11             = KeyCode.F11.key(),
     HB_KEY_F12             = KeyCode.F12.key(),
     HB_KEY_F13             = KeyCode.F13.key(),
     HB_KEY_F14             = KeyCode.F14.key(),
     HB_KEY_F15             = KeyCode.F15.key(),
     HB_KEY_F16             = KeyCode.F16.key(),
     HB_KEY_F17             = KeyCode.F17.key(),
     HB_KEY_F18             = KeyCode.F18.key(),
     HB_KEY_F19             = KeyCode.F19.key(),
     HB_KEY_F20             = KeyCode.F20.key(),
     HB_KEY_F21             = KeyCode.F21.key(),
     HB_KEY_F22             = KeyCode.F22.key(),
     HB_KEY_F23             = KeyCode.F23.key(),
     HB_KEY_F24             = KeyCode.F24.key(),
     HB_KEY_F25             = KeyCode.F25.key(),

   /* Keypad */
     HB_KEY_KP_0            = KeyCode.KP0.key(),
     HB_KEY_KP_1            = KeyCode.KP1.key(),
     HB_KEY_KP_2            = KeyCode.KP2.key(),
     HB_KEY_KP_3            = KeyCode.KP3.key(),
     HB_KEY_KP_4            = KeyCode.KP4.key(),
     HB_KEY_KP_5            = KeyCode.KP5.key(),
     HB_KEY_KP_6            = KeyCode.KP6.key(),
     HB_KEY_KP_7            = KeyCode.KP7.key(),
     HB_KEY_KP_8            = KeyCode.KP8.key(),
     HB_KEY_KP_9            = KeyCode.KP9.key(),
     HB_KEY_KP_DECIMAL      = KeyCode.KPDecimal.key(),
     HB_KEY_KP_DIVIDE       = KeyCode.KPDivide .key(),
     HB_KEY_KP_MULTIPLY     = KeyCode.KPMultiply.key(),
     HB_KEY_KP_SUBTRACT     = KeyCode.KPSubtract.key(),
     HB_KEY_KP_ADD          = KeyCode.KPAdd.key(),
     HB_KEY_KP_ENTER        = KeyCode.KPEnter.key(),
     HB_KEY_KP_EQUAL        = KeyCode.KPEqual.key(),

     HB_KEY_LEFT_SHIFT      = KeyCode.LeftShift.key(),
     HB_KEY_LEFT_CONTROL    = KeyCode.LeftControl.key(),
     HB_KEY_LEFT_ALT        = KeyCode.LeftAlt .key(),
     HB_KEY_LEFT_SUPER      = KeyCode.LeftSuper.key(),
     HB_KEY_RIGHT_SHIFT     = KeyCode.RightShift.key(),
     HB_KEY_RIGHT_CONTROL   = KeyCode.RightControl.key(),
     HB_KEY_RIGHT_ALT       = KeyCode.RightAlt .key(),
     HB_KEY_RIGHT_SUPER     = KeyCode.RightSuper.key(),
     HB_KEY_MENU            = KeyCode.Menu .key();

}
