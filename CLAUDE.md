# Abnormalities

Mod ID: `abnormalities`

异想体模组。基于 Project Moon 世界观的敌对实体系统，包含异想体（Abnormalities）和考验（Ordeals）。

## 包结构

- `core/` — `Abnormalities.kt`(@Mod), `AbnormalitiesClient.kt`, 注册表
- `init/` — 注册初始化（EntityTypes, SpawnEggItems, SoundEvents, CreativeModeTabs, EntityDataSerializers, 标签）
  - `init/entity/` — 实体类型注册
- `common/entity/` — 实体类
  - `abnormalities/` — `TrainingRabbits` (TETH)
  - `ordeals/` — 考验接口 + violet 派系实现（GrantUsLove HE, FruitOfUnderstanding TETH），预留 amber/green/crimson
- `common/item/` — `ModEggItem` 自定义刷怪蛋
- `client/renderer/entity/` — 实体渲染器（GrantUsLove, FruitOfUnderstanding）
- `events/` — 事件监听器 + 注册中心
- `event/` — 自定义事件
- `datagen/` — 数据生成
- `util/` — 工具类

## 现有实体

| 实体                   | 等级   | 说明                          |
|----------------------|------|-----------------------------|
| TrainingRabbits      | TETH | 训练兔兔, GeckoLib 模型, 灵魂/侵蚀弱点  |
| GrantUsLove          | HE   | 紫色正午, 巨型 2×5, 触手动画, 行为树 AI  |
| FruitOfUnderstanding | TETH | 紫色黎明, 弹幕攻击(5-7发/120°), 自爆机制 |

## 依赖

- **GoldenBoughsLib** — 行为树、属性、模型、数据生成基础
- **ResonatorCombatFramework** — 战斗框架

由 ImaginaryCraft 模块 jarJar 聚合。
