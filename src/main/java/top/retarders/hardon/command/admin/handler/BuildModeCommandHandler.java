package top.retarders.hardon.command.admin.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

public class BuildModeCommandHandler implements FunctionalCommandHandler<Player> {

    private UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        User user = this.repository.find(context.sender().getUniqueId()).get();

        user.buildmode = !user.buildmode;
        context.reply("&aBuild Mode: " + (user.buildmode ? "&aEnabled" : "&cDisabled"));
    }

}
