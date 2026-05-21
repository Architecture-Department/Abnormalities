# Abnormalities

异想体模组。基于 Project Moon 世界观的敌对实体系统，包含异想体（Abnormalities）和考验（Ordeals）实体。

## 项目结构

```
src/main/
├── java/architecture/abnormalities/
│   └── mixin/                      — 预留 Mixin 包（当前无实现）
│
└── kotlin/architecture/abnormalities/
    ├── core/
    │   ├── Abnormalities.kt        — @Mod("abnormalities") 入口
    │   ├── AbnormalitiesClient.kt  — 客户端入口
    │   └── registry/
    │       ├── EntityAttributeRegistry.kt
    │       └── client/EntityRenderersRegistry.kt
    ├── common/
    │   ├── entity/
    │   │   ├── abnormalities/
    │   │   │   └── TrainingRabbits.kt          — 训练兔兔 (TETH)
    │   │   └── ordeals/
    │   │       ├── IOrdealsEntity.kt            — 考验派系基础接口
    │   │       ├── violet/
    │   │       │   ├── GrantUsLove.kt           — 请给我们爱!!! (HE, 紫色正午)
    │   │       │   ├── FruitOfUnderstanding.kt  — 理解的果实 (TETH, 紫色黎明)
    │   │       │   └── IOrdealsVioletEntity.kt
    │   │       └── amber/green/crimson/         — 预留派系接口
    │   └── item/
    │       └── ModEggItem.kt        — 自定义刷怪蛋
    ├── init/
    │   ├── AbnormalitiesEntityTypes.kt
    │   ├── OrdealsEntityTypes.kt
    │   ├── ProjectileEntityTypes.kt
    │   ├── AbnormalitiesSpawnEggItems.kt
    │   ├── AbnormalitiesSoundEvents.kt  — 7 音效事件
    │   ├── AbnormalitiesCreativeModeTabs.kt
    │   ├── AbnormalitiesEntityDataSerializers.kt — 自定义数据序列化器
    │   └── tag/AbnormalitiesEntityTags.kt
    ├── client/renderer/entity/
    │   ├── GrantUsLoveRenderer.kt    — 带 AutoGlowingRenderLayer 脉动发光
    │   └── FruitOfUnderstandingRenderer.kt — 含内部投射物渲染器
    ├── datagen/                      — 数据生成（物品模型/音效/标签/i18n）
    └── events/                       — 事件处理（预留）
```

## 实体系统

### 异想体 (Abnormalities)

| 实体              | 等级   | 说明                       |
|-----------------|------|--------------------------|
| TrainingRabbits | TETH | 训练兔兔，GeckoLib 模型，灵魂/侵蚀弱点 |

### 考验 (Ordeals)

四大派系接口层次：`IOrdealsEntity` → `IOrdealsVioletEntity` / `IOrdealsAmberEntity` / `IOrdealsGreenEntity` /
`IOrdealsCrimsonEntity`

| 实体                   | 等级   | 类型   | 说明                                       |
|----------------------|------|------|------------------------------------------|
| GrantUsLove          | HE   | 紫色正午 | 巨型 2×5，350HP，7 伤害，触手动画，行为树 AI            |
| FruitOfUnderstanding | TETH | 紫色黎明 | 190HP，弹幕攻击(5-7发/120°)，自爆机制(3次充能/100tick) |

### 实体标签

- `abnormalities` — 异想体
- `ordeals` — 考验（含子标签 violet/amber/green/crimson）
- 派系互识别 + `CampHurtByTargetGoal` 团战仇恨

## 特性

- **行为树 AI**：GrantUsLove 使用 GoldenBoughsLib 的 `IBehaviorTreeMob` + `ISkillExpand` 技能系统
- **自定义伤害抗性**：物理 0.8 / 精神 2.0 / 侵蚀 0.8 / 灵魂 1.0
- **自爆打断机制**：FruitOfUnderstanding 承受 20% HP 伤害可打断自爆充能
- **GeckoLib 动画**：GrantUsLove 47 动画文件 + 关节掩码（6 触手 × 3 段）
- **AutoGlowingRenderLayer**：实体脉动发光渲染层

## 上游依赖

- **GoldenBoughsLib** — 行为树、属性、模型、数据生成基础
- **ResonatorCombatFramework** — 战斗框架
