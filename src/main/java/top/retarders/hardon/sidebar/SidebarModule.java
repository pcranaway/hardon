package top.retarders.hardon.sidebar;

import me.lucko.helper.Schedulers;
import me.lucko.helper.Services;
import me.lucko.helper.scoreboard.Scoreboard;
import me.lucko.helper.scoreboard.ScoreboardObjective;
import me.lucko.helper.scoreboard.ScoreboardProvider;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scoreboard.DisplaySlot;
import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SidebarModule implements TerminableModule {

    private final ConfigurationSection section = Services.get(ConfigurationSection.class).get()
            .getConfigurationSection("sidebar");

    private final UserRepository repository = Services.get(UserRepository.class).get();

    private final Scoreboard scoreboard = Services.load(ScoreboardProvider.class).getScoreboard();

    private final BiConsumer<User, ScoreboardObjective> updater = (user, objective) -> {
        objective.setDisplayName(section.getString("title"));

        List<String> lines = section.getStringList("lines");

        List<String> newLines = new ArrayList<>();

        lines.forEach(line -> {
            for (SidebarVariable variable : SidebarVariable.SidebarVariables.SIDEBAR_VARIABLES) {
                String placeholder = "${" + variable.getName() + "}";

                if (line.contains(placeholder)) {
                    newLines.add(line.replace(placeholder, variable.getValue(user)));
                    return;
                }

            }

            newLines.add(line);
        });

        objective.applyLines(newLines);
    };

    @Override
    public void setup(TerminableConsumer consumer) {
        Schedulers.async().runRepeating(() -> {
            this.repository.users.stream().forEach(this::update);
        }, 20L, 20L).bindWith(consumer);
    }

    public void apply(User user) {
        ScoreboardObjective objective = this.scoreboard.createPlayerObjective(user.toPlayer(), "null", DisplaySlot.SIDEBAR);

        user.objective = objective;
        updater.accept(user, objective);
    }

    public void update(User user) {
        if (!user.account.sidebar) {
            if (user.objective == null) return;

            user.objective.clearScores();
            user.objective = null;

            return;
        }

        if (user.objective == null) {
            this.apply(user);
            return;
        }

        updater.accept(user, user.objective);
    }

}