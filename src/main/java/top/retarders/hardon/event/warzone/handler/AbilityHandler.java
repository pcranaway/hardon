package top.retarders.hardon.event.warzone.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import top.retarders.hardon.ability.Ability;

import java.util.Optional;
import java.util.function.Consumer;

public class AbilityHandler implements Consumer<PlayerInteractEvent> {

    @Override
    public void accept(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Optional<Ability> hasAbility = Ability.Abilities.ABILITIES.stream().filter(ability -> ability.getItem().isSimilar(event.getItem())).findFirst();
        if(!hasAbility.isPresent()) return;

        Ability ability = hasAbility.get();

        if(ability.hasCooldown(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You can't use this ability for another " + ability.getCooldown(player.getUniqueId()) + " milliseconds!");
            return;
        }

        ability.use(event.getPlayer());
    }

}
