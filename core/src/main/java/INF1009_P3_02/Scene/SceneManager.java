package INF1009_P3_02.Scene;

import INF1009_P3_02.GameMaster;
import INF1009_P3_02.InputOutput.InputOutputManager;
import INF1009_P3_02.Logging.GameEngineLogger;
import INF1009_P3_02.SettingsData;
import INF1009_P3_02.SimulationConfig;

import java.text.SimpleDateFormat;
import java.util.Date;



public class SceneManager {

    // Shared data owned by the manager
    private final SettingsData settings = new SettingsData();
    private SimulationConfig currentConfig;
    private GameEngineLogger logger;

    // For pause/resume
    private Scene settingsSourceScene;

    // Scenes we reuse
    private final MainMenuScene mainMenuScene;
    private final CustomizationScene customizationScene;
    private final SettingsScene settingsScene;
    private final ControlSettingsScene controlSettingsScene;
    private final PauseScene pauseScene;

    // Scenes created fresh when needed
    private SimulationScene simulationScene;

    // Current scene
    private Scene currentScene;

    // Optional external managers
    private InputOutputManager inputOutputManager;
    private GameMaster gameMaster;

    public SceneManager() {
        // Create reusable scenes once
        logger = new GameEngineLogger("engine_log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt");
        mainMenuScene = new MainMenuScene(this, settings);
        customizationScene = new CustomizationScene(this, settings);
        settingsScene = new SettingsScene(this, settings);
        controlSettingsScene = new ControlSettingsScene(this, settings);
        pauseScene = new PauseScene(this, settings);

        // Start at main menu
        switchToScene(mainMenuScene);
    }

    // =========================================
    // Core switching
    // =========================================
    private void switchToScene(Scene newScene) {
        if (newScene == null) return;

        if (currentScene != null && currentScene != newScene) {
            currentScene.hide();

            // prevent stage leaks
            if (currentScene.stage != null) {
                currentScene.stage.dispose();
                currentScene.stage = null;
            }
        }

        currentScene = newScene;
        currentScene.show();
        updateSceneBrightness(currentScene);
    }

    private void updateSceneBrightness(Scene scene) {
        if (scene != null && inputOutputManager != null) {
            scene.setBrightness(inputOutputManager.getBrightness());
            scene.setShapeRenderer(inputOutputManager.getShapeRenderer());
        }
    }

    // =========================================
    // Navigation API
    // =========================================

    public void goToMainMenu() {
        clearSettingsSourceScene();
        switchToScene(mainMenuScene);
    }

    public void goToCustomization() {
        clearSettingsSourceScene();
        switchToScene(customizationScene);
    }

    public void goToSettings() {
        // Normal settings screen
        settingsScene.rebuildUI(SettingsScene.Mode.SETTINGS);
        switchToScene(settingsScene);
    }

    public void goToControlSettings() {
        switchToScene(controlSettingsScene);
    }

    public void startSimulation(SimulationConfig config) {
        logger.info("Control scheme selected: " + getSettings().controlScheme);
        this.currentConfig = config;

        // Create fresh simulation every time
        this.simulationScene = new SimulationScene(this, settings, config);
        clearSettingsSourceScene();
        switchToScene(simulationScene);
    }

    /** Called by SimulationScene when time is up, etc. */
    public void goToEndScene(int collisionCount) {
        EndScene end = new EndScene(this, collisionCount);
        clearSettingsSourceScene();
        switchToScene(end);
    }

    public void goToPauseScene() {
        logger.info("Game paused");
        if (!(currentScene instanceof SettingsScene)) {
            setSettingsSourceScene(currentScene);
        }
        settingsScene.rebuildUI(SettingsScene.Mode.PAUSE_MENU);
        switchToScene(settingsScene);
    }

    public void resumeFromPause() {
        logger.info("Game resumed");
        Scene source = getSettingsSourceScene();
        clearSettingsSourceScene();
        if (source != null) switchToScene(source);
        else switchToScene(mainMenuScene);
    }

    public void restartSimulation() {
        clearSettingsSourceScene();
        if (currentConfig != null) {
            startSimulation(currentConfig);
        } else {
            switchToScene(customizationScene);
        }
    }

    // =========================================
    // Getters / Setters used by scenes / GameMaster
    // =========================================

    public Scene getCurrentScene() {
        return currentScene;
    }

    public SettingsData getSettings() {
        return settings;
    }

    public SimulationConfig getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(SimulationConfig config) {
        this.currentConfig = config;
    }

    public InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    public void setInputOutputManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;

        if (inputOutputManager != null) {
            inputOutputManager.getSpeaker().setVolume(settings.volume);
            inputOutputManager.getBrightness().setLevel(settings.brightness);
            inputOutputManager.getSpeaker().playBackgroundMusic();
            updateSceneBrightness(currentScene);
        }
    }

    public void setGameMaster(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

    // =========================================
    // settingsSourceScene helpers
    // =========================================

    public void setSettingsSourceScene(Scene settingsSourceScene) {
        this.settingsSourceScene = settingsSourceScene;
    }

    public Scene getSettingsSourceScene() {
        return settingsSourceScene;
    }

    public void clearSettingsSourceScene() {
        this.settingsSourceScene = null;
    }

    // =========================================
    // Forward lifecycle from GameMaster
    // =========================================

    public void update(float delta) {
        if (currentScene != null) currentScene.update(delta);
    }

    public void render() {
        if (currentScene != null) currentScene.render();
    }

    public void resize(int width, int height) {
        if (currentScene != null) currentScene.resize(width, height);
    }

    public void dispose() {
        if (currentScene != null) {
            currentScene.hide();
            currentScene.dispose();
            currentScene = null;
        }
    }
}
