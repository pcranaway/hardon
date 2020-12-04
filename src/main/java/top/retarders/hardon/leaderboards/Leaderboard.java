package top.retarders.hardon.leaderboards;

import com.google.gson.Gson;
import me.lucko.helper.Services;
import top.retarders.hardon.leaderboards.api.LeaderboardPlayer;
import top.retarders.hardon.leaderboards.api.LeaderboardQuery;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

    public final String name;
    private final LeaderboardQuery query;
    public List<LeaderboardPlayer> data;

    public Leaderboard(String name, LeaderboardQuery query) {
        this.name = name;
        this.query = query;
        this.data = new ArrayList<>();
    }

    public void update() {
        this.data.clear();

        this.query.execute().forEach(playerElem -> {
            LeaderboardPlayer player = Services.get(Gson.class).get().fromJson(playerElem, LeaderboardPlayer.class);

            this.data.add(player);
            System.out.println("added " + player);
        });
    }

}
