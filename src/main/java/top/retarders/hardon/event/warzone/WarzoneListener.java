package top.retarders.hardon.event.warzone;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.util.Vector;
import top.retarders.hardon.event.warzone.handler.AbilityHandler;
import top.retarders.hardon.event.warzone.handler.DeathHandler;
import top.retarders.hardon.event.warzone.handler.RefillSignHandler;
import top.retarders.hardon.event.warzone.handler.SoupHandler;
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
                .filter(event -> event.getPlayer().getHealth() != event.getPlayer().getMaxHealth())
                .handler(new SoupHandler());

        Events.subscribe(PlayerPickupItemEvent.class)
                .filter(event -> repository.find(event.getPlayer().getUniqueId()).get().state == UserState.WARZONE)
                .filter(event -> event.getItem().getItemStack().getType() == Material.MUSHROOM_SOUP)
                .handler(event -> event.setCancelled(true));

//        Events.subscribe(PlayerDropItemEvent.class)
//                .filter(event -> repository.find(event.getPlayer().getUniqueId()).get().state == UserState.WARZONE)
//                .filter(event -> event.getItemDrop().getItemStack().getType() == Material.BOWL)
//                .handler(event -> event.getItemDrop().setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(3)));

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .filter(event -> event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)
                .handler(new RefillSignHandler());

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .filter(item -> item.getItem() != null)
                .handler(new AbilityHandler());

    }
}
