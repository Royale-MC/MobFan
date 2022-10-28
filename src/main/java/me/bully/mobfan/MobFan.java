package me.bully.mobfan;

import me.bully.mobfan.utils.cTest;
import me.bully.mobfan.utils.mobFan;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.List;

public final class MobFan extends JavaPlugin implements Listener {
    private static MobFan plugin;

    public static MobFan getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Mob fan version 1.0-SNAPSHOT");
        getServer().getPluginManager().registerEvents(new mobFan(), this);

        //Fan Crafting recipe
        ItemStack fan = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta fanMeta = fan.getItemMeta();
        fanMeta.setDisplayName(ChatColor.BLUE + "Mob fan");
        fanMeta.setLore(List.of("Mob Fan:", "-Pushes entities in the direction", "it's facing.", "Please place the fan on a wall"));
        fan.setItemMeta(fanMeta);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "sign"), fan);
        recipe.shape("aba", "bxb", "aba");
        recipe.setIngredient('a', Material.REDSTONE);
        recipe.setIngredient('b', Material.FEATHER);
        recipe.setIngredient('x', Material.OAK_SIGN);
        Bukkit.addRecipe(recipe);

        getCommand("fancheck").setExecutor(new cTest());
        plugin = this;
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            //entity detection
            final double VELOCITY = 0.2;
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if(entity.getType().equals(EntityType.PLAYER)){
                        continue;
                    }
                    Block block = entity.getLocation().getBlock();
                    Vector entityDirection = entity.getVelocity();
                    for (int i = 0; i < 8; i++) {
                        block = block.getLocation().subtract(0, 0, i).getBlock();
                        if (block.getType().equals(Material.OAK_WALL_SIGN)) {
                            TileState fanState = (TileState) block.getState();
                            PersistentDataContainer container = fanState.getPersistentDataContainer();
                            if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                                String face = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                                if (face.equals("SOUTH")) {
                                    entityDirection.setZ(VELOCITY);
                                }
                            }
                        }
                        entity.setVelocity(entityDirection);
                        block = entity.getLocation().getBlock();
                    }
                    block = entity.getLocation().getBlock();
                    for (int i = 0; i < 8; i++) {
                        block = block.getLocation().add(0, 0, i).getBlock();
                        if (block.getType().equals(Material.OAK_WALL_SIGN)) {
                            TileState fanState = (TileState) block.getState();
                            PersistentDataContainer container = fanState.getPersistentDataContainer();
                            if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                                String face = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                                if (face.equals("NORTH")) {
                                    entityDirection.setZ(-VELOCITY);
                                }
                            }
                        }
                        entity.setVelocity(entityDirection);
                        block = entity.getLocation().getBlock();
                    }
                    block = entity.getLocation().getBlock();
                    for (int i = 0; i < 8; i++) {
                        block = block.getLocation().subtract(i, 0, 0).getBlock();
                        if (block.getType().equals(Material.OAK_WALL_SIGN)) {
                            TileState fanState = (TileState) block.getState();
                            PersistentDataContainer container = fanState.getPersistentDataContainer();
                            if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                                String face = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                                if (face.equals("EAST")) {
                                    entityDirection.setX(VELOCITY);
                                }
                            }
                        }
                        entity.setVelocity(entityDirection);
                        block = entity.getLocation().getBlock();
                    }
                    block = entity.getLocation().getBlock();
                    for (int i = 0; i < 8; i++) {
                        block = block.getLocation().add(i, 0, 0).getBlock();
                        if (block.getType().equals(Material.OAK_WALL_SIGN)) {
                            TileState fanState = (TileState) block.getState();
                            PersistentDataContainer container = fanState.getPersistentDataContainer();
                            if (container.has(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING)) {
                                String face = container.get(new NamespacedKey(MobFan.getPlugin(), "fanFace"), PersistentDataType.STRING);
                                if (face.equals("WEST")) {
                                    entityDirection.setX(-VELOCITY);
                                }
                            }
                        }
                        entity.setVelocity(entityDirection);
                        block = entity.getLocation().getBlock();
                    }

                }
            }
        }, 0L, 5L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Mob fan version 1.0-SNAPSHOT");
    }
}
