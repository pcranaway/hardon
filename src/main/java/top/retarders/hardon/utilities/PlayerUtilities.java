package top.retarders.hardon.utilities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtilities {

    /**
     * Completely clears the inventory of a player
     *
     * @param player the player
     */
    public static void clear(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{});
    }

    /**
     * Resets the health, food level, XP of a player
     *
     * @param player the player
     */
    public static void resetState(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20); // this might be 10, not sure
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
    }

}
