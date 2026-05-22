package com.github.tartaricacid.touhoulittlemaid.api;

/**
 * This annotation lets touhou little maid mod detect mod extension.
 * All {@link com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid} must have this annotation and a constructor with no arguments.
 */

/**
 * Note: This is used for (Neo)Forge.
 * Fabric use entry point to replace it,
 * so you need implement the {@link com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid} interface
 * on a class and add that as an entry point of type "little_maid_extension" in your fabric.mod.json
 */
@Deprecated
public @interface LittleMaidExtension {
}
