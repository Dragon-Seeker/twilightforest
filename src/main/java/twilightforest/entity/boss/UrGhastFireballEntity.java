package twilightforest.entity.boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.entity.projectile.ITFProjectile;

public class UrGhastFireballEntity extends LargeFireball implements ITFProjectile {

	private int power;
	public UrGhastFireballEntity(Level world, UrGhastEntity entityTFTowerBoss, double x, double y, double z, int power) {
		super(world, entityTFTowerBoss, x, y, z, power);
		this.power = power;
	}

	// [VanillaCopy] super, edits noted
	@Override
	protected void onHit(HitResult result) {
		// TF - don't collide with other fireballs
		if (result instanceof EntityHitResult) {
			if (!this.level.isClientSide && !(((EntityHitResult) result).getEntity() instanceof AbstractHurtingProjectile)) {
				if (((EntityHitResult) result).getEntity() != null) {
					// TF - up damage by 10
					((EntityHitResult) result).getEntity().hurt(DamageSource.fireball(this, this.getOwner()), 16.0F);
					this.doEnchantDamageEffects((LivingEntity)this.getOwner(), ((EntityHitResult) result).getEntity());
				}

				boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
				this.level.explode(null, this.getX(), this.getY(), this.getZ(), this.power, flag, flag ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
				this.discard();
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float scale, float dist) {
		Vec3 vec3d = (new Vec3(x, y, z))
				.normalize()
				.add(this.random.nextGaussian() * 0.0075F * dist, this.random.nextGaussian() * 0.0075F * dist, this.random.nextGaussian() * 0.0075F * dist)
				.scale(scale);
		this.setDeltaMovement(vec3d);
		float f = Mth.sqrt((float) distanceToSqr(vec3d));
		this.yRot = (float) (Mth.atan2(vec3d.x, z) * (180F / (float) Math.PI));
		this.xRot = (float) (Mth.atan2(vec3d.y, f) * (180F / (float) Math.PI));
		this.yRotO = this.getYRot();
		this.xRotO = this.getXRot();
	}
}
