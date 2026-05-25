package cn.sh1rocu.touhoulittlemaid.api.mixin;

public interface IEntityRenderStatePartialTick {
    default float tlm$partialTick() {
        throw new AssertionError("Implemented in Mixin");
    }

   default void tlm$setPartialTick(float partialTicks){
        throw new AssertionError("Implemented in Mixin");
   };
}
