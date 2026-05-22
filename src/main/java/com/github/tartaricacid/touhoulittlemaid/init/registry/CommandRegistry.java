package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.command.RootCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public final class CommandRegistry {
    public static void onServerStaring(CommandDispatcher<CommandSourceStack> dispatcher) {
        RootCommand.register(dispatcher);
    }
}
