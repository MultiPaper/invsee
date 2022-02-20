package com.github.puregero.invsee;

import org.bukkit.plugin.java.JavaPlugin;

public class InvSee extends JavaPlugin {

    @Override
    public void onEnable() {
        new InvSeeCommand(this);
    }
}
