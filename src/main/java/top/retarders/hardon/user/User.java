package top.retarders.hardon.user;

import me.lucko.helper.Helper;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.external.morphia.Datastore;
import me.lucko.helper.scoreboard.ScoreboardObjective;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.retarders.hardon.account.Account;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;
import top.retarders.hardon.utilities.EntityHider;

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

    public User(UUID uuid) {
        this.uuid = uuid;

        this.objective = null;
        this.buildmode = false;
        this.kit = null;
        this.state(UserState.SPAWN);
        this.killstreak = new AtomicInteger(0);

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

        System.out.println(this.state.name());

        if(this.state == UserState.WARZONE) {
            Helper.service(UserRepository.class).get().users.stream().filter(user -> user.state == UserState.SPAWN).forEach(user -> {
                Helper.service(EntityHider.class).get().hideEntity(this.toPlayer(), user.toPlayer());
                System.out.println("hid " + user.toPlayer().getName() + " from " + this.toPlayer().getName());
            });
        }

        if(this.state == UserState.SPAWN) {
            Helper.service(UserRepository.class).get().users.stream().filter(user -> user.state == UserState.WARZONE).forEach(user -> {
                Helper.service(EntityHider.class).get().hideEntity(user.toPlayer(), this.toPlayer());
                System.out.println("hid " + this.toPlayer().getName() + " from " + user.toPlayer().getName());
            });
        }

        return this;
    }

}

