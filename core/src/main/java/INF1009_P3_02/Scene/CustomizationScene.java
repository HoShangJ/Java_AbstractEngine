package INF1009_P3_02.Scene;

import INF1009_P3_02.BackgroundChoice;
import INF1009_P3_02.Entity.ShapeType;
import INF1009_P3_02.SettingsData;
import INF1009_P3_02.SimulationConfig;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CustomizationScene extends Scene {

    private final SceneManager sceneManager;
    private final SettingsData settings;

    public CustomizationScene(SceneManager sceneManager, SettingsData settings) {
        this.sceneManager = sceneManager;
        this.settings = settings;
    }

    private <T> SelectBox<T> createBigSelectBox() {
        SelectBox<T> box = new SelectBox<>(skin);

        // Clone default style
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle(box.getStyle());

        // Scale font (only for this SelectBox style)
        style.font.getData().setScale(2.2f);

        box.setStyle(style);
        return box;
    }

    @Override
    protected void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Title
        Label title = new Label("Customization", skin);
        title.setFontScale(3f);

        // Labels
        Label bgLabel = new Label("Select Background", skin);
        bgLabel.setFontScale(2.5f);

        Label shapeLabel = new Label("Select Character Shape", skin);
        shapeLabel.setFontScale(2.5f);

        Label timeLabel = new Label("Select Simulation Time", skin);
        timeLabel.setFontScale(2.5f);

        // Select boxes
        SelectBox<BackgroundChoice> bgSelect = createBigSelectBox();
        bgSelect.setItems(BackgroundChoice.Blue, BackgroundChoice.Green, BackgroundChoice.Red);

        SelectBox<ShapeType> shapeSelect = createBigSelectBox();
        shapeSelect.setItems(ShapeType.CIRCLE, ShapeType.SQUARE);

        SelectBox<String> timeSelect = createBigSelectBox();
        timeSelect.setItems("1 min", "3 min", "5 min");
        timeSelect.setSelected("1 min");

        // Buttons
        TextButton begin = new TextButton("Begin", skin);
        TextButton back = new TextButton("Back", skin);

        begin.getLabel().setFontScale(2.5f);
        back.getLabel().setFontScale(2.5f);

        // Layout
        root.add(title).pad(20).row();

        root.add(bgLabel).padTop(20).row();
        root.add(bgSelect).width(650).height(90).pad(20).row();

        root.add(shapeLabel).padTop(10).row();
        root.add(shapeSelect).width(650).height(90).pad(20).row();

        root.add(timeLabel).padTop(10).row();
        root.add(timeSelect).width(650).height(90).pad(20).row();

        root.add(begin).width(520).height(90).padTop(30).row();
        root.add(back).width(520).height(90).padTop(15).row();

        // Behaviour
        begin.addListener(e -> {
            if (!begin.isPressed()) return false;

            SimulationConfig cfg = new SimulationConfig();
            cfg.background = bgSelect.getSelected();
            cfg.playerShape = shapeSelect.getSelected();

            String t = timeSelect.getSelected();
            if (t.startsWith("1")) cfg.durationSeconds = 60;
            else if (t.startsWith("3")) cfg.durationSeconds = 180;
            else cfg.durationSeconds = 300;

            // SceneManager owns navigation + stores config
            sceneManager.startSimulation(cfg);
            return true;
        });

        back.addListener(e -> {
            if (!back.isPressed()) return false;
            sceneManager.goToMainMenu();
            return true;
        });
    }
}
