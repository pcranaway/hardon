package top.retarders.hardon.kit;

import me.lucko.helper.mongo.external.morphia.annotations.Entity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Entity(value = "kits", noClassnameStored = true)
public class Kit {

    public String name;
    public String description;
    public ChatColor color;
    public int price;

    public Material displayItem;
    public List<ItemStack> inventory;
    public List<ItemStack> armor;

    public Kit() {}

}
