package top.retarders.hardon.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.retarders.hardon.ability.impl.JumpAbility;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface Ability {

    String getName();

    ItemStack getItem();

    void use(Player player);

    long getCooldown();

    boolean hasCooldown(UUID uuid);

    long getCooldown(UUID uuid);

    class Abilities {
        public static List<Ability> ABILITIES = Arrays.asList(
                new JumpAbility()
        );
    }

}
