package INF1009_P3_02.Scene;

import INF1009_P3_02.SettingsData;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SettingsScene extends Scene {

    public enum Mode {
        SETTINGS,
        PAUSE_MENU
    }

    private final SceneManager sceneManager;
    private final SettingsData settings;
    private Mode mode;

    public SettingsScene(SceneManager sceneManager, SettingsData settings) {
        this(sceneManager, settings, Mode.SETTINGS);
    }

    public SettingsScene(SceneManager sceneManager, SettingsData settings, Mode mode) {
        this.sceneManager = sceneManager;
        this.settings = settings;
        this.mode = mode;
    }

    @Override
    protected void buildUI() {
        if (mode == Mode.PAUSE_MENU) {
            buildPauseMenuUI();
        } else {
            buildSettingsUI();
        }
    }

    public void rebuildUI(Mode nextMode) {
        this.mode = nextMode;
        if (stage == null) {
            return;
        }
        stage.clear();
        buildUI();
    }

    private void buildPauseMenuUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Paused", skin);
        title.setFontScale(3f);

        TextButton resume = new TextButton("Resume", skin);
        TextButton restart = new TextButton("Restart", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton exitToMenu = new TextButton("Return to Menu", skin);

        resume.getLabel().setFontScale(2.5f);
        restart.getLabel().setFontScale(2.5f);
        settingsBtn.getLabel().setFontScale(2.5f);
        exitToMenu.getLabel().setFontScale(2.5f);

        root.add(title).pad(20).row();
        root.add(resume).width(520).height(90).padTop(30).row();
        root.add(restart).width(520).height(90).padTop(15).row();
        root.add(settingsBtn).width(520).height(90).padTop(15).row();
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

        settingsBtn.addListener(e -> {
            if (!settingsBtn.isPressed()) return false;
            rebuildUI(Mode.SETTINGS);
            return true;
        });

        exitToMenu.addListener(e -> {
            if (!exitToMenu.isPressed()) return false;
            sceneManager.clearSettingsSourceScene();
            sceneManager.goToMainMenu();
            return true;
        });
    }

    private void buildSettingsUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Settings", skin);
        title.setFontScale(3f);

        Label volLabel = new Label("Volume", skin);
        volLabel.setFontScale(2.5f);

        Slider volSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volSlider.setScaleY(2.5f);
        volSlider.setValue(settings.volume);

        Label brightLabel = new Label("Brightness", skin);
        brightLabel.setFontScale(2.5f);

        Slider brightSlider = new Slider(0f, 1f, 0.01f, false, skin);
        brightSlider.setScaleY(2.5f);
        brightSlider.setValue(settings.brightness);

        TextButton controls = new TextButton("Controls Setting", skin);
        TextButton back = new TextButton("Back", skin);

        controls.getLabel().setFontScale(2.5f);
        back.getLabel().setFontScale(2.5f);

        root.add(title).pad(20).row();

        root.add(volLabel).padTop(20).row();
        root.add(volSlider).width(650).height(60).pad(20).row();

        root.add(brightLabel).padTop(20).row();
        root.add(brightSlider).width(650).height(60).pad(20).row();

        root.add(controls).width(520).height(90).padTop(30).row();
        root.add(back).width(520).height(90).padTop(15).row();

        volSlider.addListener(e -> {
            settings.volume = volSlider.getValue();
            if (sceneManager.getInputOutputManager() != null) {
                sceneManager.getInputOutputManager().getSpeaker().setVolume(settings.volume);
            }
            return false;
        });

        brightSlider.addListener(e -> {
            settings.brightness = brightSlider.getValue();
            if (sceneManager.getInputOutputManager() != null) {
                sceneManager.getInputOutputManager().getBrightness().setLevel(settings.brightness);
            }
            return false;
        });

        controls.addListener(e -> {
            if (!controls.isPressed()) return false;
            sceneManager.goToControlSettings();
            return true;
        });

        back.addListener(e -> {
            if (!back.isPressed()) return false;
            if (sceneManager.getSettingsSourceScene() instanceof SimulationScene) {
                rebuildUI(Mode.PAUSE_MENU);
            } else {
                sceneManager.clearSettingsSourceScene();
                sceneManager.goToMainMenu();
            }
            return true;
        });
    }
}
