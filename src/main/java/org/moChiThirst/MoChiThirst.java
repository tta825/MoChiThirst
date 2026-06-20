package org.moChiThirst;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.moChiThirst.commands.ReloadCommand;
import org.moChiThirst.commands.SetThirstCommand;
import org.moChiThirst.managers.CommandManager;
import org.moChiThirst.managers.ConfigManager;

public final class MoChiThirst extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.setup(this);
        CommandManager commandManager = new CommandManager(this, "thirst");
        commandManager.register(new  ReloadCommand());
    }
}
