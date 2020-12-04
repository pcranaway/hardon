package top.retarders.hardon.event.spawn.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.Services;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

import java.util.function.Consumer;

public class RespawnEventHandler implements Consumer<PlayerRespawnEvent> {

    private final UserRepository repository = Services.get(UserRepository.class).get();

    @Override
    public void accept(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        User user = this.repository.find(player.getUniqueId()).get();

        event.setRespawnLocation(player.getWorld().getSpawnLocation());

        user.kit = null;
        user.state(UserState.SPAWN);
    }

}
