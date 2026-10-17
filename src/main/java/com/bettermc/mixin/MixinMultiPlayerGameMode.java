package com.bettermc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import com.bettermc.util.FlatMiningHandler;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode
{
    /**
     * 开始挖掘时拦截（生存/创造模式都会走这里，创造模式会直接破坏方块）
     */
    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void bettermc_onStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir)
    {
        if (FlatMiningHandler.isProtected(pos))
        {
            cir.setReturnValue(false);
        }
    }

    /**
     * 持续挖掘时拦截（按住左键挖掘的每一 tick）
     * 返回 true 可以顺带压掉手臂挥动动画
     */
    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void bettermc_onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir)
    {
        if (FlatMiningHandler.isProtected(pos))
        {
            cir.setReturnValue(true);
        }
    }

    /**
     * 兜底：真正执行"破坏方块 + 发包"的方法也拦一道。
     * 上面两个入口覆盖了原版的所有挖掘流程，这里再挡一次，
     * 防止其它模组直接调用 destroyBlock() 绕过前面的拦截。
     */
    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void bettermc_onDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (FlatMiningHandler.isProtected(pos))
        {
            cir.setReturnValue(false);
        }
    }
}
