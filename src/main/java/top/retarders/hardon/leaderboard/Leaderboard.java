package top.retarders.hardon.leaderboard;

import com.google.gson.Gson;
import me.lucko.helper.Helper;
import top.retarders.hardon.leaderboard.api.LeaderboardPlayer;
import top.retarders.hardon.leaderboard.api.LeaderboardQuery;

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
            LeaderboardPlayer player = Helper.service(Gson.class).get().fromJson(playerElem, LeaderboardPlayer.class);

            this.data.add(player);
            System.out.println("added " + player);
        });
    }

}
