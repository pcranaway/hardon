package top.retarders.hardon.sidebar.variable.impl;

import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;

public class KitVariable implements SidebarVariable {

    @Override
    public String getName() {
        return "kit";
    }

    @Override
    public String getValue(User user) {
        try {
            return String.valueOf(user.kit.name);
        } catch (NullPointerException exception) {
            return "None";
        }
    }

}
