package top.retarders.hardon.kit;

import me.lucko.helper.mongo.external.morphia.annotations.Entity;
import me.lucko.helper.mongo.external.morphia.annotations.Id;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import top.retarders.hardon.ability.Ability;
import top.retarders.hardon.serialization.ItemSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(value = "kits", noClassnameStored = true)
public class Kit {

    public static final List<Material> PRIMARY_WEAPONS = Arrays.asList(
            Material.DIAMOND_SWORD,
            Material.DIAMOND_AXE,

            Material.IRON_SWORD,
            Material.IRON_AXE,

            Material.GOLD_SWORD,
            Material.GOLD_AXE,

            Material.WOOD_SWORD,
            Material.WOOD_AXE
    );

    @Id
    public String name;

    public String description;
    public ChatColor color;
    public int price;

    public Material displayItem;
    public String inventory = "";
    public String armor = "";

    public Kit() {
    }

    public ItemStack[] getInventory() {
        try {
            List<ItemStack> items = Arrays.asList(ItemSerializer.itemStackArrayFromBase64(this.inventory));

            for (Ability ability : Ability.Abilities.ABILITIES) {
                items = items.stream()
//                        .filter(item -> item != null)
//                        .filter(item -> Objects.equals(item.getItemMeta().getDisplayName(), "[" + ability.getName() + "]"))
                        .map(item -> {

                            if(item != null) return null;
                            if(item.getItemMeta().getDisplayName() == "[" + ability.getName() + "]") {
                                return ability.getItem();
                            }

                            return item;

                        })
                        .collect(Collectors.toList());
            }

            return items.toArray(new ItemStack[]{});
        } catch (IOException e) {
            e.printStackTrace();
            return new ItemStack[]{};
        }
    }

    public ItemStack[] getArmor() {
        try {
            return ItemSerializer.itemStackArrayFromBase64(this.armor);
        } catch (IOException e) {
            e.printStackTrace();
            return new ItemStack[]{};
        }
    }

    public Optional<ItemStack> getPrimaryWeapon() {
        return Stream.of(this.getInventory()).filter(item -> PRIMARY_WEAPONS.contains(item.getType())).findFirst();
    }

    public void equip(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setContents(this.getInventory());
        inventory.setArmorContents(this.getArmor());
    }

}
