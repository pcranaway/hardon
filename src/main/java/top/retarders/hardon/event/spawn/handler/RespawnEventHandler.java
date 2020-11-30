package top.retarders.hardon.event.spawn.handler;

import me.lucko.helper.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import top.retarders.hardon.spawn.SpawnItems;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

import java.util.function.Consumer;

public class RespawnEventHandler implements Consumer<PlayerRespawnEvent> {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void accept(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        User user = this.repository.find(player.getUniqueId()).get();

        user.kit = null;
        user.state(UserState.SPAWN);

        event.setRespawnLocation(player.getWorld().getSpawnLocation());

        SpawnItems.ITEMS.forEach(triplet -> event.getPlayer().getInventory().setItem(triplet.second, triplet.first));
        player.updateInventory();
    }

}
