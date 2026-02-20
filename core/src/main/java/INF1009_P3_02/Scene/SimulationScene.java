package INF1009_P3_02.Scene;

import INF1009_P3_02.BackgroundChoice;
import INF1009_P3_02.Collision.CollisionContext;
import INF1009_P3_02.Collision.CollisionManager;
import INF1009_P3_02.Entity.*;
import INF1009_P3_02.InputOutput.InputOutputManager;
import INF1009_P3_02.Logging.GameEngineLogger;
import INF1009_P3_02.Movement.MovementManager;
import INF1009_P3_02.SettingsData;
import INF1009_P3_02.SimulationConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimulationScene extends Scene {

    private final SceneManager sceneManager;
    private final SettingsData settings;
    private final SimulationConfig config;

    // rendering
    private ShapeRenderer sr;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    private float heartbeatTimer = 0f;

    // managers
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private InputOutputManager inputOutputManager;
    private GameEngineLogger loggerManager;


    // entities
    private Player player;
    private Bot bot;
    private List<Obstacle> obstacles;
    private boolean simulationEnded = false;

    // world / state
    private float WORLD_W, WORLD_H;
    private float timeLeft;
    private int playerBotCollisionCount = 0;

    private static final int OBSTACLE_COUNT = 6;
    private static final float OBSTACLE_W = 80;
    private static final float OBSTACLE_H = 25;
    private boolean initialized = false;

    public SimulationScene(SceneManager sceneManager, SettingsData settings, SimulationConfig config) {
        this.sceneManager = sceneManager;
        this.settings = settings;
        this.config = config;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    @Override
    protected void buildUI() {
        // No Scene2D UI for this scene.
    }

    @Override
    public void show() {
        if (initialized) {
            // Clear UI input processor when resuming to gameplay.
            simulationEnded = false;

            Gdx.input.setInputProcessor(null);
            if (inputOutputManager != null) {
                inputOutputManager.setControlScheme(settings.controlScheme);
            }
            return;
        }
        initialized = true;

        loggerManager = new GameEngineLogger("engine_log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt");
        loggerManager.info("=== Simulation Started === Duration: " + config.durationSeconds + "s | Background: " + config.background + " | Shape: " +
            config.playerShape);


        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        layout = new GlyphLayout();

        WORLD_W = Gdx.graphics.getWidth();
        WORLD_H = Gdx.graphics.getHeight();

        timeLeft = config.durationSeconds; // make sure your config has this

        entityManager = new EntityManager();
        movementManager = new MovementManager(entityManager);

        inputOutputManager = new InputOutputManager(settings, movementManager);
        inputOutputManager.setControlScheme(settings.controlScheme);
        inputOutputManager.setEscapeListener(() -> {
            sceneManager.goToPauseScene();
        });
        collisionManager = new CollisionManager(inputOutputManager.getSpeaker());

        // Create Player
        player = new Player(
            100, 100,
            300, 30,
            config.playerShape,      // ShapeType expected
            WORLD_W, WORLD_H
        );

        // Create Bot
        bot = new Bot(
            400, 300,
            150,
            40, 30,
            WORLD_W, WORLD_H
        );

        // Create Obstacles
        obstacles = new ArrayList<>();
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            Obstacle o;
            do {
                o = Obstacle.spawnRandom(WORLD_W, WORLD_H, OBSTACLE_W, OBSTACLE_H);
            } while (o.getBounds().overlaps(player.getBounds()) || o.getBounds().overlaps(bot.getBounds()));
            obstacles.add(o);
        }

        entityManager.addEntity(player);
        movementManager.syncPlayerMovementFromEntity();

        entityManager.addEntity(bot);
        for (Obstacle o : obstacles) entityManager.addEntity(o);
    }

    @Override
    public void update(float dt) {
        timeLeft -= dt;

        heartbeatTimer += dt;
        if (heartbeatTimer >= 10f) {
            heartbeatTimer = 0f;
            loggerManager.info("Heartbeat — Player position: (" + (int)player.getX() + ", " + (int)player.getY() + ") | Time left: " + (int)timeLeft + "s");
        }

        // Keep control scheme synced with settings (e.g., after pause changes)
        inputOutputManager.setControlScheme(settings.controlScheme);

        // Save old positions BEFORE moving (for collision resolution)
        float pOldX = player.getX();
        float pOldY = player.getY();
        float bOldX = bot.getX();
        float bOldY = bot.getY();

        inputOutputManager.update(dt);

        // 1) Update movement (player and bot)
        movementManager.update(dt);

        // 2) Update bounds
        entityManager.updateAll(dt);

        // 3) Resolve collisions
        CollisionContext ctx = collisionManager.resolve(player, bot, obstacles, pOldX, pOldY, bOldX, bOldY, dt);

        if (ctx.playerCollidedWithObstacle || ctx.botCollidedWithPlayer) {
            movementManager.syncPlayerMovementFromEntity();
        }


        if (ctx.botCollidedWithObstacle || ctx.botCollidedWithPlayer) {
            movementManager.handleCollision(entityManager);
        }

        int prevCount = playerBotCollisionCount;
        playerBotCollisionCount = collisionManager.getPlayerBotCollisionCount();

        if (playerBotCollisionCount > prevCount && loggerManager != null) {
            loggerManager.info("Player-Bot collision #" + playerBotCollisionCount + " at Player(" + (int) player.getX() + "," + (int) player.getY() + ")");
        }

        if (loggerManager != null) {
            loggerManager.info("Total Collisions: " + playerBotCollisionCount);
            loggerManager.close();
        }

        // End
        if (timeLeft <= 0f && !simulationEnded) {
            simulationEnded = true;
            if (loggerManager != null) {
                loggerManager.info("=== Simulation Ended === Total Collisions: " + playerBotCollisionCount);
                loggerManager.close();
            }
            sceneManager.goToEndScene(playerBotCollisionCount);
            return;
        }
    }
    @Override
    public void render() {
        drawBackground(config.background);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity e : entityManager.getAll()) e.draw(sr);
        sr.end();

        drawHUD();
        renderBrightnessOverlay();
    }

    private void drawBackground(BackgroundChoice bg) {
        switch (bg) {
            case Blue:
                Gdx.gl.glClearColor(0.10f, 0.10f, 0.18f, 1f);
                break;

            case Green:
                Gdx.gl.glClearColor(0.10f, 0.18f, 0.10f, 1f);
                break;

            case Red:
                Gdx.gl.glClearColor(0.18f, 0.10f, 0.10f, 1f);
                break;

            default:
                Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
                break;
        }
    }

    private void drawHUD() {
        batch.begin();

        String text = "Collisions: " + playerBotCollisionCount + "  Time: " + Math.max(0, (int)timeLeft);
        layout.setText(font, text);

        float padding = 10f;
        font.draw(batch, layout, WORLD_W - layout.width - padding, WORLD_H - padding);

        layout.setText(font, "Press ESC to pause");
        font.draw(batch, layout, padding, WORLD_H - padding);

        batch.end();
    }

    public int getFinalCollisionCount() {
        return collisionManager.getPlayerBotCollisionCount();
    }

    @Override public void resize(int width, int height) {
        WORLD_W = width;
        WORLD_H = height;
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        if (loggerManager != null) loggerManager.close();
        if (sr != null) sr.dispose();
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
