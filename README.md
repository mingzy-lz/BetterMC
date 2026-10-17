# BetterMC · 更好的MC

> 一个基于 **Fabric + MaLiLib** 的 Minecraft 客户端实用模组，目标是给日常生存/建筑加一些"顺手"的小功能，而不是塞一堆花哨的东西。

- **英文名**：BetterMC
- **中文名**：更好的MC
- **游戏版本**：Minecraft **26.3**
- **加载器**：Fabric（Loader ≥ 0.19.5）
- **前置模组**：[MaLiLib](https://modrinth.com/mod/malilib) ≥ 0.30.2
- **运行环境**：仅客户端（Client-side），服务器不需要安装

---

## 功能

### 1. 平坦挖掘（Flat Mining）

铺地板、清场地、挖平整区域时最烦的就是——一镐子下去，把自己脚下的方块挖了，然后掉进坑里。

开启**平坦挖掘**后：

- ✅ 凡是**低于你脚部高度**的方块，全部无法破坏（不管在脚下哪一层、离你多远）
- ✅ 与你脚同层及更高的方块（侧面、头顶）**照常挖掘**
- ✅ 创造模式瞬间破坏同样会被拦截
- ✅ 持续按住左键也不会出现"假挖掘"的挥手动画

只有一开关，没有其它参数：

| 配置项 | 说明 | 默认值 |
| --- | --- | --- |
| 平坦挖掘 | 总开关，可绑定快捷键（默认 `Left Alt` → `M`） | 关 |

> 也就是说：开着它，你只能"横着挖"和"往上挖"，挖不到任何比你脚更低的东西。
> 需要往下挖的时候，按一下快捷键关掉即可。

### 2. 配置界面（O + L）

按 **`O` 然后 `L`** 即可打开"更好的MC"配置界面。

- 界面基于 MaLiLib 的配置系统，开关和快捷键都能直接在界面里改
- **"打开配置界面"这个快捷键本身也在界面里，可以随时改成你习惯的键位**
- 改动会自动保存到配置文件（关闭界面时写盘）
- 每个配置项右侧都有 `Reset` 按钮，可以单独恢复默认值

---

## 安装

1. 安装 **Fabric Loader**（0.19.5 或更高）到 Minecraft **26.3**
2. 把以下两个 `.jar` 放进 `.minecraft/mods/`：
   - `malilib-fabric-26.3-0.30.2.jar`（前置，必须）
   - `bettermc-fabric-26.3-0.1.0.jar`（本模组）
3. 启动游戏即可，**不需要**在服务端安装

> 需要 Java 25（Minecraft 26.x 起的要求）。

---

## 快捷键

| 功能 | 默认键位 | 说明 |
| --- | --- | --- |
| 打开配置界面 | `O` → `L` | 依次按下，不是同时按；可在界面内重新绑定 |
| 平坦挖掘开关 | `Left Alt` → `M` | 切换时会显示动作条提示 |

两个快捷键都可以在配置界面里重新绑定，也支持 MaLiLib 的按键设置（长按/松开触发、允许其它键同时按下等）。

---

## 配置文件

路径：`.minecraft/config/bettermc.json`（首次进游戏后自动生成）

结构大致如下，具体字段由 MaLiLib 负责序列化，**不建议手动编辑**，改配置请走 `O + L` 界面：

```jsonc
{
  "Generic": {
    "flatMining": { "enabled": false, "hotkey": { ... } }
  },
  "Hotkeys": {
    "openConfigGui": { ... }
  }
}
```

删除该文件即可恢复全部默认设置。

---

## 从源码构建

```bash
git clone <本仓库>
cd BetterMC

# 需要 JDK 25
./gradlew build
```

产物：`build/libs/bettermc-fabric-26.3-0.1.0.jar`

开发调试：

```bash
./gradlew runClient
```

> **注意**：Minecraft 26.x 已改用 Mojang 官方映射（Mojang Mappings），Yarn 映射停留在 1.21.11。
> 因此本工程 `build.gradle` 中**不声明** `mappings`，依赖统一用 `implementation` 而不是 `modImplementation`。

---

## 项目结构

```
BetterMC/
├── build.gradle                 # Loom 1.18.x + Java 25
├── gradle.properties            # 版本号集中管理
├── libs/
│   └── malilib-fabric-26.3-0.30.2.jar   # 前置（本地依赖）
└── src/main/
    ├── java/com/bettermc/
    │   ├── BetterMC.java                 # ModInitializer 入口
    │   ├── InitHandler.java              # 注册配置 / 配置界面 / 快捷键
    │   ├── Reference.java                # mod id、名称、版本常量
    │   ├── config/
    │   │   ├── Configs.java              # 配置项定义 + 读写
    │   │   ├── Hotkeys.java              # 独立快捷键定义
    │   │   └── Callbacks.java            # 快捷键回调（打开界面）
    │   ├── event/InputHandler.java       # 把快捷键交给 MaLiLib 分发
    │   ├── gui/GuiConfigs.java           # 配置界面
    │   ├── util/FlatMiningHandler.java   # 平坦挖掘判定逻辑
    │   └── mixin/MixinMultiPlayerGameMode.java  # 破坏方块拦截
    └── resources/
        ├── fabric.mod.json
        ├── bettermc.mixins.json
        └── assets/bettermc/
            ├── icon.png
            └── lang/{en_us,zh_cn}.json
```


