package com.bettermc.config;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.data.json.JsonUtils;
import com.bettermc.BetterMC;
import com.bettermc.Reference;

public class Configs implements IConfigHandler
{
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";
    private static final String GENERIC_KEY = Reference.MOD_ID + ".config.generic";

    public static class Generic
    {
        /** 平坦挖掘：开启后脚下（低于脚部高度）的方块无法破坏 */
        public static final ConfigBooleanHotkeyed FLAT_MINING = new ConfigBooleanHotkeyed("flatMining", false, "LEFT_ALT,M", KeybindSettings.PRESS_ALLOWEXTRA).apply(GENERIC_KEY);

        /** 配置界面里显示的功能选项 */
        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                FLAT_MINING
        );

        /** 需要注册到按键分发系统的快捷键（带开关的配置项自带一个快捷键） */
        public static final ImmutableList<IHotkey> HOTKEYS = ImmutableList.of(
                FLAT_MINING
        );
    }

    @Override
    public void load()
    {
        Path configFile = FileUtils.getConfigDirectory().resolve(CONFIG_FILE_NAME);

        if (Files.exists(configFile) && Files.isReadable(configFile))
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readHotkeys(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
            }
        }
    }

    @Override
    public void save()
    {
        Path dir = FileUtils.getConfigDirectory();

        if (!Files.exists(dir))
        {
            FileUtils.createDirectoriesIfMissing(dir);
        }

        if (Files.isDirectory(dir))
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeHotkeys(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFile(root, dir.resolve(CONFIG_FILE_NAME));
        }
        else
        {
            BetterMC.LOGGER.error("save(): Config directory '{}' does not exist!", dir.toAbsolutePath());
        }
    }
}
