package top.retarders.hardon.account;

import me.lucko.helper.mongo.external.morphia.annotations.Entity;
import me.lucko.helper.mongo.external.morphia.annotations.Id;

import java.util.UUID;

@Entity(value = "accounts", noClassnameStored = true)
public class Account {

    @Id
    public UUID uuid;

    public int deaths;
    public int kills;
    public int highestKillstreak;
    public int balance;

    public Account() {}

    public Account(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", deaths=" + deaths +
                ", kills=" + kills +
                ", highestKillstreak=" + highestKillstreak +
                ", balance=" + balance +
                '}';
    }
}
