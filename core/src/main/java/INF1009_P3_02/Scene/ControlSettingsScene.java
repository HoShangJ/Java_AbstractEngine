package INF1009_P3_02.Scene;

import INF1009_P3_02.ControlScheme;
import INF1009_P3_02.SettingsData;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ControlSettingsScene extends Scene {

    private final SceneManager sceneManager;
    private final SettingsData settings;

    public ControlSettingsScene(SceneManager sceneManager, SettingsData settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
    }

    @Override
    protected void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Control Settings", skin);
        title.setFontScale(3f);

        Label controlLabel = new Label("Control Scheme", skin);
        controlLabel.setFontScale(2.5f);

        // Create dropdown for control schemes
        SelectBox<ControlScheme> controlSchemeBox = new SelectBox<>(skin);
        controlSchemeBox.setItems(ControlScheme.values());
        controlSchemeBox.setSelected(settings.controlScheme);
        controlSchemeBox.getStyle().font.getData().setScale(2f);
        controlSchemeBox.getStyle().listStyle.font.getData().setScale(2f);

        TextButton back = new TextButton("Back", skin);
        back.getLabel().setFontScale(2.5f);

        // Layout
        root.add(title).pad(20).row();
        root.add(controlLabel).padTop(40).row();
        root.add(controlSchemeBox).width(520).height(80).pad(20).row();
        root.add(back).width(520).height(90).padTop(30).row();

        // Live update control scheme
        controlSchemeBox.addListener(e -> {
            settings.controlScheme = controlSchemeBox.getSelected();
            System.out.println("Control scheme changed to: " + settings.controlScheme);
            return false;
        });

        // Navigation
        back.addListener(e -> {
            if (!back.isPressed()) return false;
            sceneManager.goToSettings();
            return true;
        });
    }
}
