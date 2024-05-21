package xyz.devcomp.elytralock.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;

public class ConfigModel {
    @SerialEntry(comment = "The status of the lock toggle")
    @AutoGen(category = "elytralock")
    @TickBox
    public boolean toggle = false;
}