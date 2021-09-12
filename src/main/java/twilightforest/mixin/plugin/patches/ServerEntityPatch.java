package twilightforest.mixin.plugin.patches;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ServerEntityPatch implements Patch {
    @Override
    public void applyMethod(MethodNode node) {
        node.instructions.insert(
                Patch.findFirstInstruction(node, Opcodes.GETFIELD),
                Patch.listOf(
                        new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "twilightforest/ASMHooks",
                                "updateMultiparts",
                                "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/entity/Entity;",
                                false
                        )
                )
        );
    }

    @Override
    public void applyClass(ClassNode classNode) {}

    @Override
    public String getMixinClass() {
        return FabricLoader.getInstance().getMappingResolver().mapClassName("named", "net.minecraft.server.level.ServerEntity");
    }

    @Override
    public String getMethodName() {
        return FabricLoader.getInstance().getMappingResolver().mapMethodName("named", "net.minecraft.class_3231", "sendDirtyEntityData", getMethodDesc());
    }

    @Override
    public String getMethodDesc() {
        return "()V";
    }
}
