package architecture.abnormalities.common.entity.ordeals.violet

import architecture.abnormalities.common.entity.ordeals.IOrdealsEntity
import architecture.abnormalities.init.AbnormalitiesSoundEvents
import architecture.abnormalities.init.entity.ProjectileEntityTypes
import architecture.goldenboughs_lib.api.world.entity.ISpawnByEgg
import architecture.goldenboughs_lib.init.LibAttributes
import architecture.goldenboughs_lib.init.LibDamageSources
import architecture.goldenboughs_lib.init.LibDamageTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.RandomStrollGoal
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

// TODO 优化渲染
// TODO 优化弹幕渲染，方向，和发射位置
class FruitOfUnderstanding(entityType: EntityType<out PathfinderMob>, level: Level) :
	PathfinderMob(entityType, level), IOrdealsVioletEntity, ISpawnByEgg, GeoEntity, Enemy {

	companion object {
		const val SELF_DESTRUCT_ATTACK_COUNT: Int = 3
		const val SELF_DESTRUCT_CHARGE_TIME: Float = 100f
		const val SELF_DESTRUCT_ANIM_DELAY: Int = 74
		const val INTERRUPT_DAMAGE_PERCENTAGE: Float = 0.2f
		const val NORMAL_ATK_DMG: Float = 3.0f
		const val NORMAL_ATK_RANGE: Float = 3.5f
		const val SELF_DESTRUCT_DMG: Float = 30.0f
		const val SELF_DESTRUCT_AOE_RADIUS: Float = 8.0f
		const val ATK_REDUCE_CHANCE: Float = 0.3f
		const val BULLET_ATTACK_RANGE_MIN: Float = 5.0f
		const val BULLET_ATTACK_RANGE_MAX: Float = 10.0f
		const val BULLET_COUNT_MIN: Int = 5
		const val BULLET_COUNT_MAX: Int = 7
		const val BULLET_SPREAD_ANGLE: Float = 120.0f
		const val BULLET_ATTACK_COOLDOWN: Int = 60
		const val BULLET_ATTACK_Windup: Int = 40

		@JvmField
		val SELF_DESTRUCT_CHARGING: EntityDataAccessor<Boolean> =
			SynchedEntityData.defineId(FruitOfUnderstanding::class.java, EntityDataSerializers.BOOLEAN)

		@JvmField
		val CHARGE_DAMAGE_TAKEN: EntityDataAccessor<Float> =
			SynchedEntityData.defineId(FruitOfUnderstanding::class.java, EntityDataSerializers.FLOAT)

		fun createAttributes(): AttributeSupplier.Builder {
			return createMobAttributes()
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
				.add(Attributes.MAX_HEALTH, 190.0)
				.add(Attributes.ATTACK_DAMAGE, 3.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0)
				.add(Attributes.GRAVITY, 0.08)
				.add(LibAttributes.PHYSICS_VULNERABLE, 1.0)
				.add(LibAttributes.SPIRIT_VULNERABLE, 1.5)
				.add(LibAttributes.EROSION_VULNERABLE, 1.0)
				.add(LibAttributes.THE_SOUL_VULNERABLE, 1.0)
		}
	}

	private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

	private var selfDestructCounter: Int = SELF_DESTRUCT_ATTACK_COUNT
	private var chargeTime: Int = 0
	private var isCharging: Boolean = false
	private var hasPlayedExplodeAnim: Boolean = false
	private var chargeDamageTaken: Float = 0.0f

	private var walkSoundCooldown: Int = 0
	private var isBulletAttacking: Boolean = false
	private var bulletAttackCooldown: Int = 100
	private var bulletAttackWindup: Int = BULLET_ATTACK_Windup

	init {
		entityData.set(SELF_DESTRUCT_CHARGING, false)
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0f)
	}

	override fun removeWhenFarAway(distanceToClosestPlayer: Double): Boolean {
		return false
	}

	override fun defineSynchedData(builder: SynchedEntityData.Builder) {
		super.defineSynchedData(builder)
		builder.define(SELF_DESTRUCT_CHARGING, false)
		builder.define(CHARGE_DAMAGE_TAKEN, 0.0f)
	}

	override fun onSyncedDataUpdated(key: EntityDataAccessor<*>) {
		super.onSyncedDataUpdated(key)
		if (SELF_DESTRUCT_CHARGING == key) {
			this.isCharging = entityData.get(SELF_DESTRUCT_CHARGING)
		}
	}

	override fun onSpawnByEgg() {
	}

	override fun registerGoals() {
		super<IOrdealsVioletEntity>.registerGoals()
		this.goalSelector.addGoal(0, FloatGoal(this))
		this.goalSelector.addGoal(3, FruitOfUnderstandingMeleeAttackGoal())
		this.goalSelector.addGoal(4, object : RandomStrollGoal(this, 0.6) {
			override fun canUse(): Boolean {
				return !isCharging && !isBulletAttacking && super.canUse()
			}
		})
		this.goalSelector.addGoal(6, object : RandomLookAroundGoal(this) {
			override fun canUse(): Boolean {
				return !isCharging && !isBulletAttacking && super.canUse()
			}
		})
	}

	private fun canPerformNormalAttack(): Boolean {
		val target = this.target
		return !this.isCharging &&
			!this.isBulletAttacking &&
			target != null &&
			target.isAlive &&
			!isCamp(target)
	}

	private fun tryReduceSelfDestructCounter() {
		if (this.isCharging || this.selfDestructCounter <= 0) {
			return
		}
		if (!(this.random.nextFloat() < ATK_REDUCE_CHANCE)) {
			return
		}

		this.selfDestructCounter--
		if (this.selfDestructCounter <= 0) {
			startSelfDestructCharge()
		}
	}

	private fun startSelfDestructCharge() {
		if (this.isCharging) return

		this.isCharging = true
		this.chargeTime = 0
		this.hasPlayedExplodeAnim = false
		this.chargeDamageTaken = 0.0f
		entityData.set(SELF_DESTRUCT_CHARGING, true)
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0f)

		this.target = null
	}

	private fun atkEffect() {
		onAttack()
	}

	protected open fun onAttack() {
	}

	override fun tick() {
		super.tick()

		if (this.level().isClientSide || this.isDeadOrDying) {
			return
		}

		if (this.bulletAttackCooldown > 0) {
			this.bulletAttackCooldown--
		}

		if (this.isBulletAttacking) {
			if (this.bulletAttackWindup > 0) {
				this.bulletAttackWindup--

				if (this.bulletAttackWindup == 0) {
					executeBulletAttack()
					this.isBulletAttacking = false
					this.bulletAttackCooldown = BULLET_ATTACK_COOLDOWN
				}
			}
			return
		}

		val target = this.target
		if (target != null && target.isAlive && !isCamp(target) && !this.isCharging) {
			tryStartBulletAttack()
		}

		if (!this.isCharging) {
			tryPlayWalkSound()
		}

		if (this.isCharging) {
			this.chargeTime++

			if (!this.hasPlayedExplodeAnim && this.chargeTime >= SELF_DESTRUCT_ANIM_DELAY) {
				this.triggerAnim("controller", "self_destruct_explode")
				this.hasPlayedExplodeAnim = true
			}

			val interruptThreshold = this.maxHealth * INTERRUPT_DAMAGE_PERCENTAGE
			if (this.chargeDamageTaken >= interruptThreshold) {
				interruptSelfDestruct()
				return
			}

			if (this.chargeTime >= SELF_DESTRUCT_CHARGE_TIME) {
				selfDestructAttack()
				this.isCharging = false
				this.chargeTime = 0
				this.hasPlayedExplodeAnim = false
				this.chargeDamageTaken = 0.0f
				entityData.set(SELF_DESTRUCT_CHARGING, false)
				entityData.set(CHARGE_DAMAGE_TAKEN, 0.0f)
			}

			entityData.set(CHARGE_DAMAGE_TAKEN, this.chargeDamageTaken)
		}
	}

	private fun selfDestructAttack() {
		if (this.level().isClientSide) return
		selfDestructSound()

		for (livingEntity in this.level().getEntitiesOfClass(
			LivingEntity::class.java,
			this.boundingBox.inflate(SELF_DESTRUCT_AOE_RADIUS.toDouble())
		) { entity -> entity != this && entity.isAlive && !isCamp(entity) }) {

			livingEntity.hurt(LibDamageSources.erosionDamage(this), SELF_DESTRUCT_DMG)
		}

		this.discard()

		spawnExplosionParticles()
	}

	private fun spawnExplosionParticles() { //TODO: 添加自爆粒子效果
	}

	private fun selfDestructSound() {
		playSound(AbnormalitiesSoundEvents.VIOLET_DAWN_SUICIDE.value(), 2.0f, 1.0f)
	}

	private fun tryPlayWalkSound() {
		if (walkSoundCooldown > 0) {
			walkSoundCooldown--
			return
		}

		if (this.onGround() && this.deltaMovement.horizontalDistanceSqr() > 0.01) {
			playSound(AbnormalitiesSoundEvents.VIOLET_DAWN_WALK.value(), 0.5f, 1.0f)
			walkSoundCooldown = 60
		}
	}

	private fun tryStartBulletAttack() {
		if (this.isCharging || this.isBulletAttacking || this.bulletAttackCooldown > 0) {
			return
		}

		val target = this.target
		if (target == null || !target.isAlive || isCamp(target)) {
			return
		}

		val distance = this.distanceTo(target)

		var shouldUseBulletAttack = false

		if (distance >= BULLET_ATTACK_RANGE_MIN && distance <= BULLET_ATTACK_RANGE_MAX) {
			shouldUseBulletAttack = true
		} else if (distance < BULLET_ATTACK_RANGE_MIN) {
			shouldUseBulletAttack = this.random.nextFloat() < 0.3f
		}

		if (shouldUseBulletAttack) {
			startBulletAttack()
		}
	}

	private fun startBulletAttack() {
		this.isBulletAttacking = true
		this.bulletAttackWindup = BULLET_ATTACK_Windup

		triggerAnim("controller", "bullet_attack")
	}

	private fun executeBulletAttack() {
		val target = this.target
		if (target == null || !target.isAlive) {
			return
		}

		val bulletCount = BULLET_COUNT_MIN + this.random.nextInt(BULLET_COUNT_MAX - BULLET_COUNT_MIN + 1)

		val lookVec = this.getViewVector(1.0f)
		val startPos = this.position().add(0.0, 1.5, 0.0)

		for (i in 0 until bulletCount) {
			val horizontalAngle = (this.random.nextFloat() - 0.5f) * BULLET_SPREAD_ANGLE
			val verticalAngle = (this.random.nextFloat() - 0.3f) * 35.0f + 40.0f

			val yaw = Math.toRadians(horizontalAngle.toDouble())
			val pitch = Math.toRadians(verticalAngle.toDouble())

			val cosYaw = cos(yaw)
			val sinYaw = sin(yaw)
			val cosPitch = cos(pitch)
			val sinPitch = sin(pitch)

			val bulletVec = Vec3(
				(lookVec.x * cosYaw - lookVec.z * sinYaw) * cosPitch,
				lookVec.y * cosPitch + sinPitch,
				(lookVec.x * sinYaw + lookVec.z * cosYaw) * cosPitch
			)

			val normalized = bulletVec.normalize().scale(FruitBullet.BULLET_SPEED.toDouble())

			val fruitBullet = FruitBullet(this.level(), this, startPos.x, startPos.y, startPos.z)
			fruitBullet.deltaMovement = normalized

			this.level().addFreshEntity(fruitBullet)
		}
	}

	private fun interruptSelfDestruct() {
		this.isCharging = false
		this.chargeTime = 0
		this.hasPlayedExplodeAnim = false
		this.chargeDamageTaken = 0.0f
		this.selfDestructCounter = SELF_DESTRUCT_ATTACK_COUNT
		entityData.set(SELF_DESTRUCT_CHARGING, false)
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0f)
		this.triggerAnim("controller", "self_destruct_interrupted")
	}

	override fun actuallyHurt(source: DamageSource, damageAmount: Float) {
		super.actuallyHurt(source, damageAmount)
		if (this.level().isClientSide ||
			source.entity !is LivingEntity
		) {
			return
		}

		if (!this.isCharging) {
			return
		}

		this.chargeDamageTaken += damageAmount

		val interruptThreshold = this.maxHealth * INTERRUPT_DAMAGE_PERCENTAGE
		if (this.chargeDamageTaken >= interruptThreshold) {
			interruptSelfDestruct()
		}
	}

	override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
		val mainController = AnimationController(this, "controller", 5) { state ->
			if (this.isCharging && !this.hasPlayedExplodeAnim) {
				return@AnimationController state.setAndContinue(RawAnimation.begin().thenLoop("self_destruct_charge"))
			}

			val movementSpeed = this.deltaMovement.horizontalDistanceSqr()
			if (movementSpeed > 0.0001) {
				state.setAndContinue(RawAnimation.begin().thenLoop("walk"))
			} else {
				state.setAndContinue(RawAnimation.begin().thenLoop("idle"))
			}
		}

		mainController.triggerableAnim("normal_attack", RawAnimation.begin().thenPlay("normal_attack"))
		mainController.triggerableAnim("bullet_attack", RawAnimation.begin().thenPlay("bullet_attack"))
		mainController.triggerableAnim("self_destruct_explode", RawAnimation.begin().thenPlay("self_destruct_explode"))
		mainController.triggerableAnim(
			"self_destruct_interrupted",
			RawAnimation.begin().thenPlay("self_destruct_interrupted")
		)

		controllers.add(mainController)
	}

	override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
		return cache
	}

	class FruitBullet : ThrowableProjectile, GeoEntity {

		companion object {
			const val BULLET_DAMAGE: Float = 8.0f
			const val BULLET_SPEED: Float = 0.6f
			const val GRAVITY: Float = 0.0005f
		}

		private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

		constructor(entityType: EntityType<out ThrowableProjectile>, level: Level) : super(entityType, level)

		constructor(level: Level, shooter: LivingEntity, x: Double, y: Double, z: Double) :
			super(ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(), shooter, level) {
			this.setPos(x, y, z)
		}

		override fun defineSynchedData(builder: SynchedEntityData.Builder) {
		}

		override fun tick() {
			super.tick()

			val movement = this.deltaMovement

			if (movement.length() > 0) {
				this.setRot(
					(atan2(movement.z, movement.x) * 180.0 / Math.PI).toFloat() - 90.0f,
					(atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI).toFloat()
				)
			}

			val newMovement = movement.add(0.0, -GRAVITY.toDouble(), 0.0)
			this.deltaMovement = newMovement.scale(0.98)
		}

		override fun onHit(result: HitResult) {
			super.onHit(result)

			if (!this.level().isClientSide) {
				this.discard()
			}
		}

		override fun onHitEntity(result: EntityHitResult) {
			super.onHitEntity(result)

			val target = result.entity
			val shooter = this.owner

			if (shooter is IOrdealsEntity && target != null && !shooter.isCamp(target)) {
				target.hurt(LibDamageSources.createDamage(LibDamageTypes.EROSION, this, shooter), BULLET_DAMAGE)
			}

			discardBullet()
		}

		fun discardBullet() {
			if (!this.level().isClientSide) {
				this.discard()
			} else {
				// TODO 添加子弹消失效果，例如粒子，音效
			}
		}

		override fun onHitBlock(result: BlockHitResult) {
			super.onHitBlock(result)
			discardBullet()
		}

		override fun addAdditionalSaveData(compound: CompoundTag) {
			super.addAdditionalSaveData(compound)
		}

		override fun readAdditionalSaveData(compound: CompoundTag) {
			super.readAdditionalSaveData(compound)
		}

		override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
			val mainController = AnimationController(this, "controller", 0) { state ->
				state.setAndContinue(RawAnimation.begin().thenLoop("bullet_idle"))
			}

			controllers.add(mainController)
		}

		override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
			return cache
		}
	}

	inner class FruitOfUnderstandingMeleeAttackGoal : MeleeAttackGoal(this@FruitOfUnderstanding, 0.4, true) {
		private var attackCooldown: Int = 0
		private var isWindingUp: Boolean = false

		override fun tick() {
			super.tick()

			if (!isWindingUp) {
				if (attackCooldown > 0) {
					attackCooldown--
				}
				return
			}

			attackCooldown--

			if (attackCooldown <= 0) {
				executeAttack()
				isWindingUp = false
				attackCooldown = adjustedTickDelay(attackInterval)
			}
		}

		private fun executeAttack() {
			val target = this@FruitOfUnderstanding.target

			if (target == null || !target.isAlive ||
				!(this@FruitOfUnderstanding.distanceToSqr(target) <= NORMAL_ATK_RANGE * NORMAL_ATK_RANGE) ||
				this@FruitOfUnderstanding.isCamp(target)
			) {
				return
			}

			val damageSource = LibDamageSources.erosionDamage(this@FruitOfUnderstanding)
			if (!target.hurt(damageSource, NORMAL_ATK_DMG)) {
				return
			}

			atkEffect()

			tryReduceSelfDestructCounter()
		}

		override fun checkAndPerformAttack(target: LivingEntity) {
			if (attackCooldown > 0 ||
				isWindingUp ||
				!canPerformNormalAttack() ||
				!this.canPerformAttack(target)
			) {
				return
			}
			startAttackWindup()
		}

		override fun canPerformAttack(entity: LivingEntity): Boolean {
			return this.isTimeToAttack &&
				this@FruitOfUnderstanding.distanceToSqr(entity) <= NORMAL_ATK_RANGE * NORMAL_ATK_RANGE
		}

		private fun startAttackWindup() {
			this@FruitOfUnderstanding.triggerAnim("controller", "normal_attack")

			isWindingUp = true
			attackCooldown = 15
		}

		override fun canUse(): Boolean {
			return canPerformNormalAttack() && super.canUse()
		}

		override fun stop() {
			super.stop()
			isWindingUp = false
			attackCooldown = 0
		}
	}
}
