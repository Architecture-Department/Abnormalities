package architecture.abnormalities.common.entity.ordeals

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.GoalSelector
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.player.Player

interface IOrdealsEntity {
	fun registerGoals() {
		targetSelector.addGoal(1, NearestAttackableTargetGoal<>(mob, Player::class.java, true) { canTarget(it) })
		targetSelector.addGoal(2, NearestAttackableTargetGoal<>(mob, Mob::class.java, true) { canTarget(it) })
	}

	/**
	 * 目标选择
	 */
	fun canTarget(entity: Entity): Boolean {
		return isValidTarget(entity)
	}

	/**
	 * 判断是否是可以攻击目标
	 * TODO 还需要处理以便支持斗蛐蛐
	 */
	fun isValidTarget(entity: Entity): Boolean {
		if (entity === this) return false

		if (!entity.isAlive || !entity.isAttackable) {
			return false
		}

		if (entity is Player player) {
			return !player.isCreative && !player.isSpectator
		}

		return !isCamp(entity)
	}

	/**
	 * 判断是否是同阵营
	 * TODO 还需要处理以便支持斗蛐蛐
	 */
	fun isCamp(entity: Entity): Boolean {
		return mob.type == entity.type
	}

	val targetSelector: GoalSelector
		get() = mob.targetSelector

	val mob: Mob
		get() = this as Mob

	val goalSelector: GoalSelector
		get() = mob.goalSelector
}
