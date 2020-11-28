package top.retarders.hardon.sidebar.variable.impl;

import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;

public class KillstreakVariable implements SidebarVariable {

    @Override
    public String getName() {
        return "killstreak";
    }

    @Override
    public String getValue(User user) {
        return String.valueOf(user.killstreak.get());
    }

}
