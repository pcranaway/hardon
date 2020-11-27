package top.retarders.hardon.event.statistics.handler;

import me.lucko.helper.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.retarders.hardon.account.Account;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.function.Consumer;

public class DeathHandler implements Consumer<PlayerDeathEvent> {

    private UserRepository userRepository = Helper.service(UserRepository.class).get();

    @Override
    public void accept(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        User killedUser = this.userRepository.find(killed.getUniqueId()).get();
        Account killedAccount = killedUser.account;

        killedAccount.deaths += 0;
        killedUser.killstreak.set(0);

        Player killer = (Player) ((EntityDamageByEntityEvent) (killed.getLastDamageCause())).getDamager();
        User killerUser = this.userRepository.find(killer.getUniqueId()).get();
        Account killerAccount = killerUser.account;

        killerAccount.kills += 1;
        killerUser.killstreak.incrementAndGet();
    }

}