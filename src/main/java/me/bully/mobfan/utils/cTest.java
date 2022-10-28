package me.bully.mobfan.utils;

import me.bully.mobfan.MobFan;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class cTest implements CommandExecutor {
    //command to test if the sign is a working fan or not, return the direction the fan is facing
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlockExact(5);
            if (block.getState() instanceof TileState) {
                TileState tileState = (TileState) block.getState();
                PersistentDataContainer container = tileState.getPersistentDataContainer();
                if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                    String face = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                    player.sendMessage(face);
                } else {
                    player.sendMessage("this is not a fan");
                }
            } else {
                player.sendMessage("This block got no tileState");
            }
        }


        return true;
    }
}
