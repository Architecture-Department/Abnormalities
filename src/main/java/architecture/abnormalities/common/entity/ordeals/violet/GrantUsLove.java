package architecture.abnormalities.common.entity.ordeals.violet;

import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.init.AbnormalitiesSoundEvents;
import architecture.goldenboughs_lib.api.world.entity.IBehaviorTreeMob;
import architecture.goldenboughs_lib.api.world.entity.ISkillExpand;
import architecture.goldenboughs_lib.api.world.entity.ISpawnByEgg;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTFactory;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTNode;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTRoot;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.composite.ParallelNode;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.condition.ConditionBT;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.condition.TargetExistCondition;
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.leaf.LookAtTargetAction;
import architecture.goldenboughs_lib.init.LibAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * 英文编号:ordeals--violet noon
 * <p>
 * 中文编号:考验--紫罗兰色正午
 * <p>
 * 英文名:Grant Us Love
 * <p>
 * 中文名: 请给我们爱
 * <p>
 * 2025/12/22 尘昨喧
 *
 * <h2>TODO 待办事项:</h2>
 * <ul>
 *   <li>技能或大招剩余时间要持久化</li>
 *   <li>需要免疫中毒,漂浮</li>
 * </ul>
 */
public class GrantUsLove extends AbstractGolem implements IOrdealsVioletEntity, ISpawnByEgg, IBehaviorTreeMob<GrantUsLove>, Enemy, ISkillExpand, GeoEntity {
	public static final @NotNull ResourceLocation ULTIMATE_SKILL = Abnormalities.modRl("ultimate_skill");
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
//	private GrantUsLovePatch patch;
//
//	private final MobSkill ultimateSkill = new MobSkill(ULTIMATE_SKILL, 20 * 10) {
//		public static final float INTERRUPT_DAMAGE_PERCENTAGE = 0.2F; // 传送中断所需伤害百分比（0.2F即为20%的血量最大值）
//
//		@Override
//		public void cdEnd() {
//
//		}
//
//		@Override
//		public void launch() {
//			// TODO 生成传送门
//			var grantUsLovePatch = getPatch();
//			var entityEventListener = grantUsLovePatch.getEventListener();
//			var atomicReference = new AtomicReference<>(0.0F);
//			var delayTaskHolder = DelayTaskHolder.of(GrantUsLove.this);
//
//			delayTaskHolder.addTask(ULTIMATE_SKILL,
//				DelayTaskHolder.createTaskBilder()
//					.tickRun((tick, maxTick, iTask) -> tick)
//					.removedRun((tick) -> {
//						// TODO 然后触发新的技能
//						entityEventListener.removeListenersBelongTo(this);
//						grantUsLovePatch.playAnimationSynchronized(GrantUsLoveAnimations.EXTEND, 0.03f);
//					})
//					.resultRun(() -> {
//						LivingEntity target = getTarget();
//						if (target == null) {
//							return;
//						}
//						grantUsLovePatch.playAnimationSynchronized(GrantUsLoveAnimations.ULTIMATE_SKILL, 0.03f);
//					})
//					.removedTick(20 * 2) // 到时间后
//					.build());
//
//			entityEventListener.registerEvent(EpicFightEventHooks.Entity.TAKE_DAMAGE_POST, event -> {
//				atomicReference.updateAndGet(v -> v + event.getDamage());
//				if (atomicReference.get() >= INTERRUPT_DAMAGE_PERCENTAGE * getMaxHealth()) {
//					delayTaskHolder.removeTask(ULTIMATE_SKILL);
//				}
//			}, this, 10);
//		}
//	};

	public GrantUsLove(EntityType<? extends GrantUsLove> entityType, Level level) {
		super(entityType, level);
		lookControl = new LookControl(this) {
			@Override
			public void tick() {
//				if (getPatch().getEntityState().turningLocked()) {
//					return;
//				}

				this.getYRotD().ifPresent(p_287447_ -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, p_287447_, this.yMaxRotSpeed));
				this.getXRotD().ifPresent(p_352768_ -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), p_352768_, this.xMaxRotAngle)));
			}
		};
	}

//	@Override
//	public GrantUsLovePatch getPatch() {
//		if (patch == null) {
//			patch = EpicFightCapabilities.getEntityPatch(this, GrantUsLovePatch.class);
//		}
//		return patch;
//	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
			.add(Attributes.KNOCKBACK_RESISTANCE, 1)
			.add(Attributes.MAX_HEALTH, 350)
			.add(Attributes.ATTACK_DAMAGE, 7)
			.add(Attributes.ATTACK_KNOCKBACK, 1)
			.add(Attributes.GRAVITY, 0.1)
			.add(LibAttributes.PHYSICS_VULNERABLE, 0.8)
			.add(LibAttributes.SPIRIT_VULNERABLE, 2.0)
			.add(LibAttributes.EROSION_VULNERABLE, 0.8)
			.add(LibAttributes.THE_SOUL_VULNERABLE, 1)
			/*.add(EpicFightAttributes.IMPACT, 8)
			.add(EpicFightAttributes.MAX_STRIKES, 8)*/;
	}

	@Override
	public void registerGoals() {
		super.registerGoals();
		IOrdealsVioletEntity.super.registerGoals();
		targetSelector.addGoal(3, createBehaviorTree());
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}


	//endregion
	//region 基本属性方法
	@Override
	public BTRoot<GrantUsLove> createBehaviorTree() {
		return new GrantUsLoveBT(this);
	}

	@Override
	public void tick() {
		super.tick();
//		ultimateSkill.tick();
		yBodyRot = yHeadRot;
		setYRot(yBodyRot);
	}

	@Override
	public void setDeltaMovement(Vec3 deltaMovement) {
		if (onGround()) {
			return;
		}
		if (deltaMovement.y > 0) {
			return;
		}
		super.setDeltaMovement(new Vec3(0, deltaMovement.y, 0));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	public void onSpawnByEgg() {
		// TODO 播放入场动画
	}

	@Override
	public boolean canTarget(Entity entity) {
		return IOrdealsVioletEntity.super.canTarget(entity);
	}

	@Override
	protected void actuallyHurt(DamageSource source, float damageAmount) {
		super.actuallyHurt(source, damageAmount);
	}

	//region 声音（音效集合）
	private void crashAtkSound() {
		playSound(AbnormalitiesSoundEvents.VIOLET_NOON_DOWN.value(), 2.0F, 1.0F);
	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPushedByFluid(@NotNull FluidType type) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected boolean isImmobile() {
		return false;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	public boolean isInWater() {
		return false;
	}

	//endregion

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		readSkillsData(compound);
	}

	@Override
	public void readSkillsData(CompoundTag compound) {
//		ultimateSkill.readData(compound);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		addSkillsData(compound);
	}

	@Override
	public void addSkillsData(CompoundTag compound) {
//		ultimateSkill.addData(compound);
	}

	@Override
	public int getHeadRotSpeed() {
		return 3;
	}

	@Override
	public int getMaxHeadYRot() {
		return 10;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}

	public static class GrantUsLoveBT extends BTRoot<GrantUsLove> {

		public GrantUsLoveBT(GrantUsLove mob) {
			super(mob);
		}

		@Override
		protected @NotNull BTNode createBehaviorTree() {
			return BTFactory.parallel(ParallelNode.Policy.REQUIRE_ALL, ParallelNode.Policy.REQUIRE_ALL)
				.addChild(BTFactory.infinite(BTFactory.selector()
					// 目标不存在
					.addWithCondition(ConditionBT.not(new TargetExistCondition(this.mob)), BTFactory.sequence())
					// 目标存在
					.addChild(BTFactory.infinite(BTFactory.sequence()
						.addChild(new LookAtTargetAction(mob))
						.addChild(BTFactory.selector())
					))))
				// 其他处理例如：技能冷却
				.addChild(BTFactory.infinite(BTFactory.success(() -> {
				})));
		}
	}
}
