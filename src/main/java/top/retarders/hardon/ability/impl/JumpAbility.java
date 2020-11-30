package top.retarders.hardon.ability.impl;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import top.retarders.hardon.ability.Ability;


public class JumpAbility extends Ability {

    private final ItemStack item;

    public JumpAbility() {
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
        player.setVelocity(player.getEyeLocation().getDirection().add(new Vector(0, 2, 0)));
    }

    @Override
    public long getCooldown() {
        return 3000;
    }
}

