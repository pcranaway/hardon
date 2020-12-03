package top.retarders.hardon.leaderboard.api;

public class LeaderboardPlayer {

    public final String name;
    public final int kills;
    public final int deaths;
    public final int highestKillstreak;
    public final int balance;

    public LeaderboardPlayer(String name, int kills, int deaths, int highestKillstreak, int balance) {
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.highestKillstreak = highestKillstreak;
        this.balance = balance;
    }

}
