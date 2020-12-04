package top.retarders.hardon.event.warzone.handler;

import me.lucko.helper.Services;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import top.retarders.hardon.ability.Ability;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.Optional;
import java.util.function.Consumer;

public class AbilityHandler implements Consumer<PlayerInteractEvent> {

    private final UserRepository repository = Services.get(UserRepository.class).get();

    @Override
    public void accept(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Optional<Ability> hasAbility = Ability.Abilities.ABILITIES.stream().filter(ability -> ability.getItem().isSimilar(event.getItem())).findFirst();
        if (!hasAbility.isPresent()) return;

        User user = this.repository.find(player.getUniqueId()).get();

        Ability ability = hasAbility.get();

        if (user.hasCooldown(ability)) {
            player.sendMessage(ChatColor.RED + "You can't use this ability for another " + user.getTimeLeft(ability) + " milliseconds!");
            return;
        }


        user.addCooldown(ability, ability.getCooldown());
        ability.use(event.getPlayer());
    }

}
