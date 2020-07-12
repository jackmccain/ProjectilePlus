package com.mcip.projectileplus;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ProjectilePlus extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ProjectilePlus enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ProjectilePlus disabled!");
    }

    @EventHandler
    public void onFireObj(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        Projectile proj = (Projectile) entity;
        if (proj.getShooter() instanceof Player) {
            ProjectileSource shooter = proj.getShooter();
            Player player = (Player) shooter;
            World world = player.getWorld();
            Location loc = proj.getLocation();
            String bowName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (bowName.equalsIgnoreCase("Explode")) {
                if (player.hasPermission("ProjectilePlus.explode")) {
                    expBow(world, loc);
                }
            }
            if (bowName.equalsIgnoreCase("Lightning")) {
                if (player.hasPermission("ProjectilePlus.lightning")) {
                    lngBow(world, loc);
                }
            }
            if (bowName.equalsIgnoreCase("Teleport")) {
                if (player.hasPermission("ProjectilePlus.teleport")) {
                    tpBow(player, world, loc);
                }
            }
            if (bowName.equalsIgnoreCase("Cobweb")) {
                if (player.hasPermission("ProjectilePlus.cobweb")) {
                    cbwb(loc);
                }
            }
            if (bowName.equalsIgnoreCase("Charge")) {
                if (player.hasPermission("ProjectilePlus.charge")) {
                    firecharge(loc, world);
                }
            }
            if (bowName.equalsIgnoreCase("Firework")) {
                if (player.hasPermission("ProjectilePlus.firework")) {
                    frewrk(loc);
                }
            }
            if (event.getHitEntity() != null) {
                if (event.getHitEntity() instanceof Player) {
                    Player player2 = (Player) event.getHitEntity();
                    if (bowName.equalsIgnoreCase("Kill")) {
                        if (player.hasPermission("ProjectilePlus.kill")) {
                            kllBow(player2, loc, world);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Regen")) {
                        if (player.hasPermission("ProjectilePlus.regen")) {
                            rgnBow(player2, world, loc);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Grapple")) {
                        if (player.hasPermission("ProjectilePlus.grapple")) {
                            grple(player, player2, loc);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Inventory Swap")) {
                        if (player.hasPermission("ProjectilePlus.inventoryswap")) {
                            invswp(player, player2);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Location Swap")) {
                        if (player.hasPermission("ProjectilePlus.locationswap")) {
                            locswp(player, player2);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Blind")) {
                        if (player.hasPermission("ProjectilePlus.blind")) {
                            blind(player2);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Freeze")) {
                        if (player.hasPermission("ProjectilePlus.freeze")) {
                            trap(player2);
                        }
                    }
                    if (bowName.equalsIgnoreCase("Strengthen")) {
                        if (player.hasPermission("ProjectilePlus.strengthen")) {
                            strength(player2);
                        }
                    }
                }
            }
        }
    }

    public void expBow(World world, Location loc) {
        world.createExplosion(loc, 4);
    }

    public void lngBow(World world, Location loc) {
        world.strikeLightning(loc);
    }

    public void tpBow(Player player, World world, Location loc) {
        player.teleport(loc);
        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 3, 1);
        world.playEffect(loc, Effect.DRAGON_BREATH, 0);

    }

    public void grple(Player player, Player player2, Location loc) {
        Vector direction = player.getLocation().getDirection();
        direction.multiply(-3);
        direction.setY(0.5);
        player2.setVelocity(direction);
    }

    public void invswp(Player player, Player player2) {
        ItemStack[] inv = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack[] inv2 = player2.getInventory().getContents();
        ItemStack[] armor2 = player2.getInventory().getArmorContents();

        player.getInventory().clear();
        player2.getInventory().clear();

        for (ItemStack j : inv) {
            player2.getInventory().addItem(j);
        }
        for (ItemStack i : inv2) {
            player.getInventory().addItem(i);
        }
        for (ItemStack j : armor) {
            player2.getInventory().addItem(j);
        }
        for (ItemStack i : armor2) {
            player.getInventory().addItem(i);
        }
    }

    public void locswp(Player player, Player player2) {
        Location p1 = player.getLocation();
        Location p2 = player2.getLocation();
        player.teleport(p2);
        player2.teleport(p1);
    }

    public void blind(Player player2) {
        player2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100));
    }

    public void trap(Player player2) {
        player2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 100));
    }

    public void strength(Player player2) {
        player2.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
    }

    public void cbwb(Location loc) {
        loc.getBlock().setType(Material.COBWEB);
    }

    public void firecharge(Location loc, World world) {
        Fireball fireball = world.spawn(loc, Fireball.class);
        Vector v = loc.getDirection();
        fireball.setDirection(v);
        fireball.setVelocity(v);
        fireball.setBounce(false);
        fireball.setIsIncendiary(true);
    }

    public void frewrk(Location loc) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }

    public void kllBow(Player player2, Location loc, World world) {
        player2.setHealth(0);
        world.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
    }

    public void rgnBow(Player player2, World world, Location loc) {
        player2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
    }
}