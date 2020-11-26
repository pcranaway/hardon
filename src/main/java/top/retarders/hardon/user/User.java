package top.retarders.hardon.user;

import me.lucko.helper.Helper;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.external.morphia.Datastore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.retarders.hardon.account.Account;
import top.retarders.hardon.user.state.UserState;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class User {

    public final UUID uuid;
    public Account account;

    public UserState state;
    public AtomicInteger killstreak;

    public User(UUID uuid) {
        this.uuid = uuid;

        this.state = UserState.SPAWN;
        this.killstreak = new AtomicInteger(0);

        this.loadAccount();
    }

    public Account loadAccount() {
        Mongo mongo = Helper.service(Mongo.class).get();
        Datastore datastore = mongo.getMorphiaDatastore();

        Account account = datastore.find(Account.class)
                .filter("uuid", this.uuid)
                .get();

        if(account != null) {
            this.account = account;
            return account;
        }

        account = new Account(this.uuid);
        datastore.save(account);

        return account;
    }

    public Player toPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public User state(UserState state) {
        this.state = state;

        return this;
    }

}

