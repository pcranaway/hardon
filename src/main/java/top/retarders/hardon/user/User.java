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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

        this.updateHidden();

        return this;
    }

    public void updateHidden() {
        List<User> users = Helper.service(UserRepository.class).get().users;
        EntityHider entityHider = Helper.service(EntityHider.class).get();

        // if player just got in warzone
        if(this.state == UserState.WARZONE) {

            // find all players that are in spawn
            users.stream().filter(user -> user.state == UserState.SPAWN).forEach(user -> {

                // hide the current player from the user
                entityHider.hideEntity(this.toPlayer(), user.toPlayer());

            });
        }
    }

}

