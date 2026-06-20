package org.moChiThirst.commands;

import org.bukkit.command.CommandSender;
import org.moChiThirst.managers.ConfigManager;
import org.moChiThirst.utils.Color;

import java.util.List;

public class ReloadCommand implements SubCommand {

    @Override
    public String getName() { return "reload"; }

    @Override
    public String getPermission() { return "MoChiThirst.reload"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String message = ConfigManager.get("messages.yml").getString("reload");
        long start = System.currentTimeMillis();

        ConfigManager.reloadAll();

        long time = System.currentTimeMillis() - start;
        sender.sendMessage(Color.translate(message.replace("{time}", String.valueOf(time))));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}