package top.retarders.hardon.event.warzone.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class RefillSignHandler implements Consumer<PlayerInteractEvent> {

    @Override
    public void accept(PlayerInteractEvent event) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, "Free Soups :D");
        ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);

        while (inventory.firstEmpty() != -1) {
            inventory.addItem(soup);
        }

        event.getPlayer().openInventory(inventory);
    }

}