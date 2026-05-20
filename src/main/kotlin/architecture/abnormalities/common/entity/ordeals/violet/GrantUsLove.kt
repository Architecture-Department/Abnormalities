package architecture.abnormalities.common.entity.ordeals.violet

import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.init.AbnormalitiesSoundEvents
import architecture.goldenboughs_lib.api.world.entity.IBehaviorTreeMob
import architecture.goldenboughs_lib.api.world.entity.ISkillExpand
import architecture.goldenboughs_lib.api.world.entity.ISpawnByEgg
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTFactory
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTNode
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.BTRoot
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.composite.ParallelNode
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.condition.ConditionBT
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.condition.TargetExistCondition
import architecture.goldenboughs_lib.api.world.entity.ai.behavior.leaf.LookAtTargetAction
import architecture.goldenboughs_lib.init.LibAttributes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.LookControl
import net.minecraft.world.entity.animal.AbstractGolem
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.fluids.FluidType
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.util.GeckoLibUtil

/**
 * 英文编号:ordeals--violet noon
 *
 * 中文编号:考验--紫罗兰色正午
 *
 * 英文名:Grant Us Love
 *
 * 中文名: 请给我们爱
 *
 * 2025/12/22 尘昨喧
 *
 * ## TODO 待办事项:
 * - 技能或大招剩余时间要持久化
 * - 需要免疫中毒,漂浮
 */
class GrantUsLove(entityType: EntityType<out GrantUsLove>, level: Level) :
	AbstractGolem(entityType, level), IOrdealsVioletEntity, ISpawnByEgg, IBehaviorTreeMob<GrantUsLove>, Enemy,
	ISkillExpand, GeoEntity {

	companion object {
		@JvmField
		val ULTIMATE_SKILL: ResourceLocation = Abnormalities.modRl("ultimate_skill")

		fun createAttributes(): AttributeSupplier.Builder {
			return createMobAttributes()
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
				.add(Attributes.MAX_HEALTH, 350.0)
				.add(Attributes.ATTACK_DAMAGE, 7.0)
				.add(Attributes.ATTACK_KNOCKBACK, 1.0)
				.add(Attributes.GRAVITY, 0.1)
				.add(LibAttributes.PHYSICS_VULNERABLE, 0.8)
				.add(LibAttributes.SPIRIT_VULNERABLE, 2.0)
				.add(LibAttributes.EROSION_VULNERABLE, 0.8)
				.add(LibAttributes.THE_SOUL_VULNERABLE, 1.0)
		}
	}

	private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

	init {
		lookControl = object : LookControl(this) {
			override fun tick() {
				this.yRotD.ifPresent { yRotD ->
					this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, yRotD, this.yMaxRotSpeed)
				}
				this.xRotD.ifPresent { xRotD -> this.mob.xRot = this.rotateTowards(this.mob.xRot, xRotD, this.xMaxRotAngle) }
			}
		}
	}

	override fun registerGoals() {
		super.registerGoals()
		(this as IOrdealsVioletEntity).registerGoals()
		targetSelector.addGoal(3, createBehaviorTree())
	}

	override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
		return cache
	}

	override fun createBehaviorTree(): BTRoot<GrantUsLove> {
		return GrantUsLoveBT(this)
	}

	override fun tick() {
		super.tick()
		yBodyRot = yHeadRot
		yRot = yBodyRot
	}

	override fun setDeltaMovement(deltaMovement: Vec3) {
		if (onGround()) {
			return
		}
		if (deltaMovement.y > 0) {
			return
		}
		super.setDeltaMovement(Vec3(0.0, deltaMovement.y, 0.0))
	}

	override fun defineSynchedData(builder: SynchedEntityData.Builder) {
		super.defineSynchedData(builder)
	}

	override fun onSyncedDataUpdated(key: EntityDataAccessor<*>) {
		super.onSyncedDataUpdated(key)
	}

	override fun onSpawnByEgg() {
		// TODO 播放入场动画
	}

	override fun canTarget(entity: Entity): Boolean {
		return (this as IOrdealsVioletEntity).canTarget(entity)
	}

	override fun actuallyHurt(source: DamageSource, damageAmount: Float) {
		super.actuallyHurt(source, damageAmount)
	}

	private fun crashAtkSound() {
		playSound(AbnormalitiesSoundEvents.VIOLET_NOON_DOWN.value(), 2.0f, 1.0f)
	}

	override fun getMovementEmission(): MovementEmission {
		return MovementEmission.NONE
	}

	override fun isPushable(): Boolean {
		return false
	}

	override fun isPushedByFluid(type: FluidType): Boolean {
		return false
	}

	override fun canBeCollidedWith(): Boolean {
		return true
	}

	override fun isImmobile(): Boolean {
		return false
	}

	override fun causeFallDamage(fallDistance: Float, multiplier: Float, source: DamageSource): Boolean {
		return false
	}

	override fun isInWater(): Boolean {
		return false
	}

	override fun readAdditionalSaveData(compound: CompoundTag) {
		super.readAdditionalSaveData(compound)
		readSkillsData(compound)
	}

	override fun readSkillsData(compound: CompoundTag) {
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		super.addAdditionalSaveData(compound)
		addSkillsData(compound)
	}

	override fun addSkillsData(compound: CompoundTag) {
	}

	override fun getHeadRotSpeed(): Int {
		return 3
	}

	override fun getMaxHeadYRot(): Int {
		return 10
	}

	override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
		controllers.add(AnimationController(this, "controller") { PlayState.CONTINUE })
	}

	override fun getTick(`object`: Any?): Double {
		return 0.0
	}

	class GrantUsLoveBT(mob: GrantUsLove) : BTRoot<GrantUsLove>(mob) {

		override fun createBehaviorTree(): BTNode {
			return BTFactory.parallel(ParallelNode.Policy.REQUIRE_ALL, ParallelNode.Policy.REQUIRE_ALL)
				.addChild(
					BTFactory.infinite(
						BTFactory.selector()
							// 目标不存在
							.addWithCondition(ConditionBT.not(TargetExistCondition(this.mob)), BTFactory.sequence())
							// 目标存在
							.addChild(
								BTFactory.infinite(
									BTFactory.sequence()
										.addChild(LookAtTargetAction(mob))
										.addChild(BTFactory.selector())
								)
							)
					)
				)
				// 其他处理例如：技能冷却
				.addChild(
					BTFactory.infinite(
						BTFactory.success { }
					)
				)
		}
	}
}
