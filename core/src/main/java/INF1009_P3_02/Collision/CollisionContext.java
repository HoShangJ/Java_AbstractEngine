package INF1009_P3_02.Collision;

public class CollisionContext {
    // old positions captured at start of this frame (before movement)
    public final float pOldX, pOldY;
    public final float bOldX, bOldY;
    public final float dt;

    // per-frame outcomes (flags)
    public boolean botCollidedWithObstacle = false;
    public boolean botCollidedWithPlayer = false;
    public boolean playerCollidedWithObstacle = false;

    // for CollisionCounter (edge-trigger counting)
    public boolean playerBotOverlappingNow = false;

    public CollisionContext(float pOldX, float pOldY, float bOldX, float bOldY, float dt) {
        this.pOldX = pOldX;
        this.pOldY = pOldY;
        this.bOldX = bOldX;
        this.bOldY = bOldY;
        this.dt = dt;
    }
}
