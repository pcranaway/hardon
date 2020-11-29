package top.retarders.hardon.event.warzone;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import top.retarders.hardon.event.warzone.handler.DeathHandler;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

public class WarzoneListener implements TerminableModule {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(PlayerDeathEvent.class)
                .filter(event -> this.repository.find(event.getEntity().getUniqueId()).get().state == UserState.WARZONE)
                .handler(new DeathHandler())
                .bindWith(consumer);

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getItem() != null)
                .filter(event -> event.getItem().getType() == Material.MUSHROOM_SOUP)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .handler(event -> event.getPlayer().setHealth(event.getPlayer().getMaxHealth() + 3.5));
    }
}
