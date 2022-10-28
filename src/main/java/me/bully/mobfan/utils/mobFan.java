package me.bully.mobfan.utils;

import me.bully.mobfan.MobFan;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class mobFan implements Listener {
    //Fan detection and placement if the item in hand is a legit fan
    @EventHandler
    public void onFanPlacement(BlockPlaceEvent e) {
        Player placer = e.getPlayer();
        Block fan = e.getBlock();
        if (e.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Mob fan")) {
            if (e.getItemInHand().getItemMeta().getLore().get(0).equals("Mob Fan:")) {
                if (!(e.getBlock().getFace(e.getBlockAgainst()).equals(BlockFace.UP)) && !(e.getBlock().getFace(e.getBlockAgainst()).equals(BlockFace.DOWN)) && !(e.getBlock().getFace(e.getBlockAgainst()).equals(BlockFace.SELF))) {
                    Sign sign =(Sign) fan.getState();
                    sign.setLine(1, "-=[   ]=-");
                    sign.setLine(2, "MobFan");
                    sign.setColor(DyeColor.LIGHT_BLUE);
                    sign.setGlowingText(true);
                    sign.update();
                    BlockFace face = e.getBlockAgainst().getFace(e.getBlock());
                    BlockState blockState = fan.getState();
                    TileState tileState = (TileState) blockState;
                    PersistentDataContainer container = tileState.getPersistentDataContainer();
                    String setFan;
                    switch (face) {
                        case NORTH:
                            setFan = "NORTH";
                            container.set(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING, setFan);
                            tileState.update();
                            break;
                        case SOUTH:
                            setFan = "SOUTH";
                            container.set(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING, setFan);
                            tileState.update();
                            break;
                        case WEST:
                            setFan = "WEST";
                            container.set(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING, setFan);
                            tileState.update();
                            break;
                        case EAST:
                            setFan = "EAST";
                            container.set(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING, setFan);
                            tileState.update();
                            break;
                        default:
                            break;
                    }
                    String display = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                    placer.sendMessage("Fan placed successfully facing " + display);
                } else {
                    e.setCancelled(true);
                    placer.sendMessage("Please place the fan on a wall");
                }
            }
        }
    }
    @EventHandler
    public void onFanRemoval(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        if (block.getState() instanceof TileState) {
            TileState tileState = (TileState) block.getState();
            PersistentDataContainer container = tileState.getPersistentDataContainer();
            if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                e.setDropItems(false);
                ItemStack fan = new ItemStack(Material.OAK_SIGN, 1);
                ItemMeta fanMeta = fan.getItemMeta();
                fanMeta.setDisplayName(ChatColor.BLUE + "Mob fan");
                fanMeta.setLore(List.of("Mob Fan:", "-Pushes entities in the direction", "it's facing.", "Please place the fan on a wall"));
                fan.setItemMeta(fanMeta);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), fan);
                player.sendMessage(ChatColor.RED+"You broke a fan !");
            }
        }
    }
}
