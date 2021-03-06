package top.retarders.hardon.spawn;

import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.retarders.hardon.kit.gui.KitSelectorGui;
import top.retarders.hardon.leaderboards.gui.LeaderboardsGui;
import top.retarders.hardon.user.preferences.PreferencesGui;
import top.retarders.hardon.utilities.ImmutableTriplet;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SpawnItems {

    public static final ItemStack KIT_SELECTOR_ITEM = ItemStackBuilder.of(Material.ENCHANTED_BOOK)
            .name("&7Kit Selector")
            .lore("&7Right click to open the Kit Selector")
            .build();

    public static final Consumer<Player> KIT_SELECTOR_ACTION = player -> {
        new KitSelectorGui(player).open();
    };

    public static final ItemStack PREFERENCES_ITEM = ItemStackBuilder.of(Material.ANVIL)
            .name("&7Preferences")
            .lore("&7Right click to open the Preferences Menu")
            .build();

    public static final Consumer<Player> PREFERENCES_ACTION = player -> {
        new PreferencesGui(player).open();
    };

    public static final ItemStack LEADERBOARDS_ITEM = ItemStackBuilder.of(Material.BOOKSHELF)
            .name("&7Leaderboards")
            .lore("&7Right click to open the Leaderboards Menu")
            .build();

    public static final Consumer<Player> LEADERBOARDS_ACTION = player -> {
        new LeaderboardsGui(player).open();
    };

    /**
     * A map of all spawn items and the slots they should be placed at
     */
    public static final List<ImmutableTriplet<ItemStack, Integer, Consumer<Player>>> ITEMS = Arrays.asList(
            new ImmutableTriplet<>(PREFERENCES_ITEM, 2, PREFERENCES_ACTION),
            new ImmutableTriplet<>(KIT_SELECTOR_ITEM, 4, KIT_SELECTOR_ACTION),
            new ImmutableTriplet<>(LEADERBOARDS_ITEM, 6, LEADERBOARDS_ACTION)
    );

    /**
     * Checks if an item is a spawn item
     *
     * @param item the item to be checked
     * @return whether the item is a spawn item or not
     */
    public static boolean isSpawnItem(ItemStack item) {
        return ITEMS.stream().anyMatch(triplet -> triplet.first.isSimilar(item));
    }

}
