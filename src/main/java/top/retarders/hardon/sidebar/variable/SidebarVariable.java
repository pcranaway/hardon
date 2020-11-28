package top.retarders.hardon.sidebar.variable;

import top.retarders.hardon.sidebar.variable.impl.*;
import top.retarders.hardon.user.User;

import java.util.Arrays;
import java.util.List;

public interface SidebarVariable {

    String getName();

    String getValue(User user);

    class SidebarVariables {
        public static List<SidebarVariable> SIDEBAR_VARIABLES = Arrays.asList(
                new DeathsVariable(),
                new HighestKillstreakVariable(),
                new KillstreakVariable(),
                new KillsVariable(),
                new KitVariable(),
                new StateVariable()
        );

    }

}
