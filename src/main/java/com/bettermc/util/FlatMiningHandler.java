package com.bettermc.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;

import com.bettermc.config.Configs;

/**
 * 平坦挖掘 / Flat Mining
 *
 * 开启后，凡是位于玩家脚底以下的方块都无法被破坏，
 * 与脚同层及更高的方块（侧面、头顶）仍然可以正常挖掘。
 */
public class FlatMiningHandler
{
    /**
     * @return true 表示该方块在脚下方，应当阻止破坏
     */
    public static boolean isProtected(BlockPos pos)
    {
        if (!Configs.Generic.FLAT_MINING.getBooleanValue())
        {
            return false;
        }

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null || player.isSpectator())
        {
            return false;
        }

        // 用脚底的精确高度来判断，而不是 blockPosition()。
        // 站在半砖 / 地毯 / 耕地 / 雪层这类非整格方块上时，
        // blockPosition() 会把"踩着的那一格"算成脚所在的高度，
        // 于是脚正下方那块就漏网了 —— 这就是之前"有时候还能挖"的原因。
        final double feetY = player.getY();

        // 方块底面低于脚底，说明这个方块整体在脚下方
        return pos.getY() < feetY;
    }
}
