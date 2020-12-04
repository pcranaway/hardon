package top.retarders.hardon.event.warzone;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import top.retarders.hardon.event.warzone.handler.AbilityHandler;
import top.retarders.hardon.event.warzone.handler.DeathHandler;
import top.retarders.hardon.event.warzone.handler.RefillSignHandler;
import top.retarders.hardon.event.warzone.handler.SoupHandler;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

import java.util.Arrays;
import java.util.List;

public class WarzoneListener implements TerminableModule {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    private static final List<Material> NO_DROP = Arrays.asList(
            Material.MUSHROOM_SOUP,
            Material.DIAMOND_SWORD,
            Material.GOLD_SWORD,
            Material.IRON_SWORD,
            Material.STONE_SWORD,
            Material.WOOD_SWORD,
            Material.GOLD_HOE
    );

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

        Events.subscribe(PlayerDropItemEvent.class)
                .filter(event -> repository.find(event.getPlayer().getUniqueId()).get().state == UserState.WARZONE)
                .filter(event -> NO_DROP.contains(event.getItemDrop().getItemStack().getType()))
                .handler(event -> event.setCancelled(true));

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .filter(event -> event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)
//                .filter(event -> ((ChatColor.stripColor(Sign) event.getClickedBlock().getState()).getLine(0)).equalsIgnoreCase("[Refill]"))
                .filter(event -> ChatColor.stripColor(((Sign) event.getClickedBlock().getState()).getLine(0)).equalsIgnoreCase("[Refill]"))
                .filter(event -> this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.WARZONE)
                .handler(new RefillSignHandler());

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .filter(item -> item.getItem() != null)
                .filter(player -> this.repository.find(player.getPlayer().getUniqueId()).get().state == UserState.WARZONE)
                .handler(new AbilityHandler());

    }
}
