package com.bettermc.config;

import fi.dy.masa.malilib.gui.GuiBase;
import com.bettermc.gui.GuiConfigs;

public class Callbacks
{
    public static void init()
    {
        // O + L -> 打开配置界面
        Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback((action, key) ->
        {
            GuiBase.openGui(new GuiConfigs());
            return true;
        });
    }
}
