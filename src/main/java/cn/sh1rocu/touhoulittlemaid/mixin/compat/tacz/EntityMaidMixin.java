package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import cn.sh1rocu.tacz.api.mixin.ItemHandlerCapability;
import cn.sh1rocu.tacz.util.forge.LazyOptional;
import cn.sh1rocu.tacz.util.itemhandler.IItemHandler;
import cn.sh1rocu.touhoulittlemaid.util.compat.tacz.WrappedTaCZItemHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(EntityMaid.class)
public abstract class EntityMaidMixin extends LivingEntity implements ItemHandlerCapability {
    @Unique
    @Nullable
    private LazyOptional<IItemHandler> tacz$maidItemHandler;

    protected EntityMaidMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public LazyOptional<IItemHandler> tacz$getItemHandler(@Nullable Direction facing) {
        if (this.tacz$maidItemHandler == null) {
            this.tacz$maidItemHandler = LazyOptional.of(() -> new WrappedTaCZItemHandler(((EntityMaid) (Object) this).getAllInv()));
        }

        if (isAlive()) {
            return this.tacz$maidItemHandler.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public void tacz$invalidateItemHandler() {
        if (this.tacz$maidItemHandler == null) return;
        this.tacz$maidItemHandler.invalidate();
    }

    @Override
    public void tacz$reviveItemHandler() {
        this.tacz$maidItemHandler = LazyOptional.of(() -> new WrappedTaCZItemHandler(((EntityMaid) (Object) this).getAllInv()));
    }
}
