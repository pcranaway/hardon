package top.retarders.hardon.user;

import me.lucko.helper.Helper;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.external.morphia.Datastore;
import me.lucko.helper.scoreboard.ScoreboardObjective;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.retarders.hardon.ability.Ability;
import top.retarders.hardon.account.Account;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.spawn.SpawnItems;
import top.retarders.hardon.user.state.UserState;
import top.retarders.hardon.utilities.PlayerUtilities;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class User {

    public final UUID uuid;
    public Account account;

    public UserState state;
    public Kit kit;
    public AtomicInteger killstreak;
    public boolean buildmode;
    public ScoreboardObjective objective;
    private final HashMap<Ability, Long> abilityCooldowns;

    public User(UUID uuid) {
        this.uuid = uuid;

        this.objective = null;
        this.buildmode = false;
        this.kit = null;
        this.state(UserState.SPAWN);
        this.killstreak = new AtomicInteger(0);
        this.abilityCooldowns = new HashMap<>();

        this.loadAccount();
    }

    public Account loadAccount() {
        Mongo mongo = Helper.service(Mongo.class).get();
        Datastore datastore = mongo.getMorphiaDatastore();

        Account account = datastore.find(Account.class)
                .filter("uuid", this.uuid)
                .get();

        if (account != null) {
            this.account = account;
            return account;
        }

        account = new Account(this.uuid);
        account.sidebar = true;
        account.deathMessages = true;
        account.globalChat = true;
        datastore.save(account);

        return account;
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public User state(UserState state) {
        this.state = state;

        System.out.println("new state of " + this.toPlayer().getName() + ": " + this.state.name());
        if(this.state == UserState.SPAWN) {

            PlayerUtilities.resetState(toPlayer());
            PlayerUtilities.clear(toPlayer());

            SpawnItems.ITEMS.forEach(triplet -> this.toPlayer().getInventory().setItem(triplet.second, triplet.first));
            this.toPlayer().updateInventory();
        }

        return this;
    }

    public void addCooldown(Ability ability, long duration) {
        this.abilityCooldowns.put(ability, System.currentTimeMillis() + duration);
    }

    public boolean hasCooldown(Ability ability) {
        return this.getTimeLeft(ability) != 0;
    }

    public long getTimeLeft(Ability ability) {
//        if(this.abilityCooldowns.containsKey(ability)) {
//            if((System.currentTimeMillis() - this.abilityCooldowns.get(ability) <= 0)) {
//                this.abilityCooldowns.remove(ability);
//            }
//        }

        long timeLeft = this.abilityCooldowns.getOrDefault(ability, (long) 0) - System.currentTimeMillis();

        if (timeLeft <= 0) {
            this.abilityCooldowns.remove(ability);
            return 0;
        }

        return timeLeft;
    }

}

