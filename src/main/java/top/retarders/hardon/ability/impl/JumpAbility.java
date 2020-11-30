package top.retarders.hardon.ability.impl;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import top.retarders.hardon.ability.Ability;

import java.util.HashMap;
import java.util.UUID;


public class JumpAbility implements Ability {

    private final ItemStack item;
    private final HashMap<UUID, Long> cooldowns;

    public JumpAbility() {
        this.cooldowns = new HashMap<>();

        this.item = new ItemStack(Material.GOLD_HOE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Jump");

        item.setItemMeta(meta);
    }


    @Override
    public String getName() {
        return "jump";
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }

    @Override
    public void use(Player player) {
        player.setVelocity(player.getEyeLocation().getDirection().add(new Vector(0, 3, 0)));
        player.sendMessage(ChatColor.AQUA + "You just used the jump ability");
    }

    @Override
    public long getCooldown() {
        return 3000;
    }

    @Override
    public boolean hasCooldown(UUID uuid) {
        return this.getCooldown(uuid) <= 0;
    }

    @Override
    public long getCooldown(UUID uuid) {
        return this.cooldowns.getOrDefault(uuid, (long) 0);
    }
}