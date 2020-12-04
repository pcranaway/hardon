package top.retarders.hardon;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import me.lucko.helper.Helper;
import me.lucko.helper.Schedulers;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.MongoDatabaseCredentials;
import me.lucko.helper.mongo.MongoProvider;
import me.lucko.helper.mongo.plugin.HelperMongo;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import top.retarders.hardon.command.admin.AdminCommandsModule;
import top.retarders.hardon.command.kit.KitsCommandsModule;
import top.retarders.hardon.event.admin.AdminListener;
import top.retarders.hardon.event.connection.ConnectionListener;
import top.retarders.hardon.event.improvements.ImprovementsListener;
import top.retarders.hardon.event.spawn.SpawnListener;
import top.retarders.hardon.event.statistics.StatisticsListener;
import top.retarders.hardon.event.warzone.WarzoneListener;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.leaderboards.LeaderboardsModule;
import top.retarders.hardon.sidebar.SidebarModule;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.utilities.EntityHider;

@Plugin(
        name = "hardon",
        version = "1.0",
        description = "kitpvp at its finest",
        authors = "pcranaway",
        website = "https://retarders.top",
        hardDepends = {
                "ProtocolLib",
                "helper",
                "helper-mongo",
                "KtLoader"
        }
)
public class HardonPlugin extends ExtendedJavaPlugin implements MongoProvider {

    private MongoDatabaseCredentials globalCredentials;
    private Mongo globalDataSource;

    @Override
    protected void enable() {
        // load config
        saveDefaultConfig();

        // connect to database
        globalCredentials = MongoDatabaseCredentials.fromConfig(getConfig());
        globalDataSource = getMongo(globalCredentials);
        globalDataSource.bindWith(this);

        // kinda di
        provideService(MongoProvider.class, this);
        provideService(MongoDatabaseCredentials.class, globalCredentials);
        provideService(Mongo.class, globalDataSource);

        provideService(ConfigurationSection.class, getConfig());

        provideService(Gson.class, new Gson());
        provideService(JsonParser.class, new JsonParser());

        provideService(LeaderboardsModule.class, new LeaderboardsModule());

        provideService(UserRepository.class, new UserRepository());
        provideService(KitRepository.class, new KitRepository());
        provideService(SidebarModule.class, new SidebarModule());

        provideService(EntityHider.class, new EntityHider(this, EntityHider.Policy.BLACKLIST));

        // load kits
        getService(KitRepository.class).loadKits();

        bindModules(
                new ConnectionListener(),
                new StatisticsListener(),
                new SpawnListener(),
                new ImprovementsListener(),
                new WarzoneListener(),
                new AdminListener(),
                // commands
                new KitsCommandsModule(),
                new AdminCommandsModule(),
                // other
                Helper.service(SidebarModule.class).get(),
                Helper.service(LeaderboardsModule.class).get()
        );


        // schedule accounts save task every 15 seconds (not sure if that's a bad idea)
        Schedulers.async().runRepeating(() -> {
            getService(UserRepository.class).users.forEach(user -> globalDataSource.getMorphiaDatastore().save(user.account));
        }, 15 * 20L, 15 * 20L);

        // schedule change time task every minute
        Schedulers.async().runRepeating(() -> {
            Bukkit.getWorld("world").setTime(1000);
        }, 60 * 20L, 60 * 20L);

        // schedule clear items every 15 seconds
        Schedulers.async().runRepeating(() -> {
            Bukkit.getWorld("world").getEntities().stream().filter(entity -> entity.getType() == EntityType.DROPPED_ITEM).forEach(Entity::remove);
        }, 15 * 20L, 15 * 20L);
    }

    private void bindModules(TerminableModule... modules) {
        for (TerminableModule module : modules) {
            bindModule(module);
        }
    }

    @Override
    public Mongo getMongo() {
        return globalDataSource;
    }

    @Override
    public Mongo getMongo(MongoDatabaseCredentials mongoDatabaseCredentials) {
        return new HelperMongo(mongoDatabaseCredentials);
    }

    @Override
    public MongoDatabaseCredentials getGlobalCredentials() {
        return globalCredentials;
    }
}
