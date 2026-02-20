package INF1009_P3_02.Scene;

import INF1009_P3_02.SettingsData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScene extends Scene {

    private final SceneManager sceneManager;
    private final SettingsData settings;

    public MainMenuScene(SceneManager sceneManager, SettingsData settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
    }

    @Override
    protected void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        float w = stage.getViewport().getWorldWidth() * 0.35f; // 35% of screen width
        float h = 80f;

        TextButton start = new TextButton("Start", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton quit = new TextButton("Quit", skin);

        root.add(start).width(w).height(h).pad(18).row();
        root.add(settingsBtn).width(w).height(h).pad(18).row();
        root.add(quit).width(w).height(h).pad(18).row();

        start.getLabel().setFontScale(2.4f);
        settingsBtn.getLabel().setFontScale(2.4f);
        quit.getLabel().setFontScale(2.4f);

        start.addListener(e -> {
            if (!start.isPressed()) return false;
            sceneManager.goToCustomization();
            System.out.println("Customization");
            return true;
        });

        settingsBtn.addListener(e -> {
            if (!settingsBtn.isPressed()) return false;
            sceneManager.clearSettingsSourceScene();
            sceneManager.goToSettings();
            System.out.println("Settings");
            return true;
        });

        quit.addListener(e -> {
            if (!quit.isPressed()) return false;
            Gdx.app.exit();
            return true;
        });
    }
}
