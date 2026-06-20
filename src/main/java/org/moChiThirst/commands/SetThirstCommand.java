package org.moChiThirst.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.moChiThirst.MoChiThirst;

public class SetThirstCommand implements CommandExecutor {
    MoChiThirst moChiThirst;
    public SetThirstCommand(MoChiThirst moChiThirst) {this.moChiThirst = moChiThirst;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {



        return false;
    }
}
