package xyz.devcomp.elytralock.util;

import java.util.function.Consumer;

import xyz.devcomp.elytralock.ElytraLock;

public class RunOnceOnToggle<T> {
    private static boolean hasRun = false;
    private Consumer<T> toRun;

    public RunOnceOnToggle(Consumer<T> toRun) {
        this.toRun = toRun;
    }

    public boolean run(T param) {
        boolean isLocked = ElytraLock.isLocked();
        if (isLocked) {
            if (!hasRun) {
                toRun.accept(param);
                hasRun = true;
            }
        } else {
            hasRun = false;
        }

        return isLocked;
    }
}
