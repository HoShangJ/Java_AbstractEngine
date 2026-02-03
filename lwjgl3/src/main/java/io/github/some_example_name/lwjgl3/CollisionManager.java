package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class CollisionManager {
    private boolean playerBotWasColliding = false;

    public static class Result {
        public int playerBotCollisionCount;
        public Result(int count) { this.playerBotCollisionCount = count; }
    }
}
