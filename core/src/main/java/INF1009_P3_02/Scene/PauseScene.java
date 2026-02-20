package INF1009_P3_02.Scene;

import INF1009_P3_02.SettingsData;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseScene extends Scene {

    private final SceneManager sceneManager;
    private final SettingsData settings;

    public PauseScene(SceneManager sceneManager, SettingsData settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
    }

    @Override
    protected void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Paused", skin);
        title.setFontScale(3f);

        TextButton resume = new TextButton("Resume", skin);
        TextButton restart = new TextButton("Restart", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton exitToMenu = new TextButton("Exit to Menu", skin);

        resume.getLabel().setFontScale(2.5f);
        restart.getLabel().setFontScale(2.5f);
        settings.getLabel().setFontScale(2.5f);
        exitToMenu.getLabel().setFontScale(2.5f);

        root.add(title).pad(20).row();
        root.add(resume).width(520).height(90).padTop(40).row();
        root.add(restart).width(520).height(90).padTop(15).row();
        root.add(settings).width(520).height(90).padTop(15).row();
        root.add(exitToMenu).width(520).height(90).padTop(15).row();

        resume.addListener(e -> {
            if (!resume.isPressed()) return false;
            sceneManager.resumeFromPause();
            return true;
        });

        restart.addListener(e -> {
            if (!restart.isPressed()) return false;
            sceneManager.restartSimulation();
            return true;
        });

        settings.addListener(e -> {
            if (!settings.isPressed()) return false;
            sceneManager.goToSettings();
            return true;
        });

        exitToMenu.addListener(e -> {
            if (!exitToMenu.isPressed()) return false;
            sceneManager.goToMainMenu();
            return true;
        });
    }
}
