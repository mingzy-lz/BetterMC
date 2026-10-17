package com.bettermc;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.data.ModInfo;
import com.bettermc.config.Callbacks;
import com.bettermc.config.Configs;
import com.bettermc.event.InputHandler;
import com.bettermc.gui.GuiConfigs;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        // 配置文件的读写
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());

        // 配置界面：注册到 malilib 的配置界面列表（O+L 打开的就是这个界面）
        Registry.CONFIG_SCREEN.registerConfigScreenFactory(
                new ModInfo(Reference.MOD_ID, Reference.MOD_NAME, GuiConfigs::new)
        );

        // 把本模组的快捷键注册到 malilib 的按键分发系统里
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());

        Callbacks.init();
    }
}
