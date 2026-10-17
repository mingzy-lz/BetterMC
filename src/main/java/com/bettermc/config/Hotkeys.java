package com.bettermc.config;

import com.google.common.collect.ImmutableList;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import com.bettermc.Reference;

public class Hotkeys
{
    private static final String HOTKEY_KEY = Reference.MOD_ID + ".config.hotkey";

    /** 打开 BetterMC 的配置界面，默认 O + L */
    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "O,L", KeybindSettings.PRESS_ALLOWEXTRA).apply(HOTKEY_KEY);

    public static final ImmutableList<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_CONFIG_GUI
    );
}
