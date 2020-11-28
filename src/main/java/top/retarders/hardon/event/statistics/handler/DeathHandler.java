package top.retarders.hardon.event.statistics.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.retarders.hardon.account.Account;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.function.Consumer;

public class DeathHandler implements Consumer<PlayerDeathEvent> {

    private final UserRepository userRepository = Helper.service(UserRepository.class).get();

    @Override
    public void accept(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        User killedUser = this.userRepository.find(killed.getUniqueId()).get();
        Account killedAccount = killedUser.account;

        killedAccount.deaths += 1;

        if(killedUser.killstreak.get() > killedAccount.highestKillstreak) {
            killedAccount.highestKillstreak = killedUser.killstreak.get();
        }

        killedUser.killstreak.set(0);

        if(!(killed.getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        Player killer = (Player) ((EntityDamageByEntityEvent) (killed.getLastDamageCause())).getDamager();

        User killerUser = this.userRepository.find(killer.getUniqueId()).get();
        Account killerAccount = killerUser.account;

        killerAccount.kills += 1;
        killerUser.killstreak.incrementAndGet();

        // TODO: Implement neural network to find the amount of coins the killer should get depending on the stats of the killed player
        int $ = (int) (Math.random() * (250 - 60)) + 60;
        killerAccount.balance += $;

        killer.sendMessage(Text.colorize("&7You've earned &f$" + $ + "&7 for killing &f" + killed.getName()));
    }

}