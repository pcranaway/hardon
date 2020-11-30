package top.retarders.hardon.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.retarders.hardon.ability.impl.JumpAbility;

import java.util.Arrays;
import java.util.List;

public abstract class Ability {

    public abstract String getName();

    public abstract ItemStack getItem();

    public abstract void use(Player player);

    public abstract long getCooldown();

    public static class Abilities {
        public static List<Ability> ABILITIES = Arrays.asList(
                new JumpAbility()
        );
    }

}
