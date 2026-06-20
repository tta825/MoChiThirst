package org.moChiThirst;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

/**
 * %thirst_current% -> độ khát hiện tại  | vd: 10
 * %thirst_max%     -> độ khát tối đa    | vd: 20
 * %thirst_percent% -> phần trăm độ khát | vd: 50
 */

public class Placeholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() { return "thirst"; }

    @Override
    public @NotNull String getAuthor() { return "MoChiMC"; }

    @Override
    public @NotNull String getVersion() { return "1.0.0"; }

}
