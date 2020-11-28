package top.retarders.hardon;

import me.lucko.helper.Helper;
import me.lucko.helper.Schedulers;
import me.lucko.helper.maven.LibraryLoader;
import me.lucko.helper.maven.MavenLibrary;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.MongoDatabaseCredentials;
import me.lucko.helper.mongo.MongoProvider;
import me.lucko.helper.mongo.plugin.HelperMongo;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import top.retarders.hardon.command.admin.AdminCommandsModule;
import top.retarders.hardon.command.kit.KitsCommandsModule;
import top.retarders.hardon.event.connection.ConnectionListener;
import top.retarders.hardon.event.improvements.ImprovementsListener;
import top.retarders.hardon.event.spawn.SpawnListener;
import top.retarders.hardon.event.statistics.StatisticsListener;
import top.retarders.hardon.event.warzone.WarzoneListener;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.sidebar.SidebarModule;
import top.retarders.hardon.user.repo.UserRepository;

@Plugin(
        name = "hardon",
        version = "1.0",
        description = "kitpvp at its finest",
        authors = "pcranaway",
        website = "https://retarders.top",
        hardDepends = {
                "helper",
                "helper-mongo"
        }
)
public class HardonPlugin extends ExtendedJavaPlugin implements MongoProvider {

    private MongoDatabaseCredentials globalCredentials;
    private Mongo globalDataSource;

    @Override
    protected void enable() {
        // load config
        this.saveDefaultConfig();

        // connect to database
        this.globalCredentials = MongoDatabaseCredentials.fromConfig(this.getConfig());
        this.globalDataSource = this.getMongo(this.globalCredentials);
        this.globalDataSource.bindWith(this);

        // kinda di
        this.provideService(MongoProvider.class, this);
        this.provideService(MongoDatabaseCredentials.class, this.globalCredentials);
        this.provideService(Mongo.class, this.globalDataSource);

        this.provideService(ConfigurationSection.class, this.getConfig());

        this.provideService(UserRepository.class, new UserRepository());
        this.provideService(KitRepository.class, new KitRepository());
        this.provideService(SidebarModule.class, new SidebarModule());

        // load kits
        this.getService(KitRepository.class).loadKits();

        // register listeners
        this.bindModule(new ConnectionListener());
        this.bindModule(new StatisticsListener());
        this.bindModule(new SpawnListener());
        this.bindModule(new ImprovementsListener());
        this.bindModule(new WarzoneListener());

        // register commands
        this.bindModule(new KitsCommandsModule());
        this.bindModule(new AdminCommandsModule());

        // register other modules
        this.bindModule(Helper.service(SidebarModule.class).get());

        // schedule accounts save task every 15 seconds (not sure if that's a bad idea)
        Schedulers.async().runRepeating(() -> {
            this.getService(UserRepository.class).users.forEach(user -> this.globalDataSource.getMorphiaDatastore().save(user.account));
        }, 15 * 20L, 15 * 20L);

        // schedule change time task every minute
        Schedulers.async().runRepeating(() -> {
            Bukkit.getWorld("world").setTime(1000);
        }, 60 * 20L, 60 * 20L);
    }

    @Override
    public Mongo getMongo() {
        return this.globalDataSource;
    }

    @Override
    public Mongo getMongo(MongoDatabaseCredentials mongoDatabaseCredentials) {
        return new HelperMongo(mongoDatabaseCredentials);
    }

    @Override
    public MongoDatabaseCredentials getGlobalCredentials() {
        return this.globalCredentials;
    }
}
