package com.bettermc.gui;

import java.util.ArrayList;
import java.util.List;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import com.bettermc.Reference;
import com.bettermc.config.Configs;
import com.bettermc.config.Hotkeys;

public class GuiConfigs extends GuiConfigsBase
{
    public GuiConfigs()
    {
        super(10, 50, Reference.MOD_ID, null, "bettermc.gui.title.configs", Reference.MOD_VERSION);
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs()
    {
        List<IConfigBase> configs = new ArrayList<>();

        // 功能选项
        configs.addAll(Configs.Generic.OPTIONS);
        // 快捷键（含"打开配置界面"）
        configs.addAll(Hotkeys.HOTKEY_LIST);

        return ConfigOptionWrapper.createFor(configs);
    }
}
