package INF1009_P3_02.Scene;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EndScene extends Scene {

    private final SceneManager sceneManager;
    private final int collisionCount;

    public EndScene(SceneManager sceneManager, int collisionCount) {
        this.sceneManager = sceneManager;
        this.collisionCount = collisionCount;
    }

    @Override
    protected void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Simulation Ended", skin);
        title.setFontScale(3f);

        Label score = new Label("Collisions: " + collisionCount, skin);
        score.setFontScale(2.5f);

        TextButton restart = new TextButton("Restart", skin);
        TextButton exitToMenu = new TextButton("Exit to Menu", skin);

        restart.getLabel().setFontScale(2.5f);
        exitToMenu.getLabel().setFontScale(2.5f);

        root.add(title).pad(20).row();
        root.add(score).padTop(20).row();

        root.add(restart).width(520).height(90).padTop(40).row();
        root.add(exitToMenu).width(520).height(90).padTop(15).row();

        restart.addListener(e -> {
            if (!restart.isPressed()) return false;
            sceneManager.goToCustomization();
            return true;
        });

        exitToMenu.addListener(e -> {
            if (!exitToMenu.isPressed()) return false;
            sceneManager.goToMainMenu();
            return true;
        });
    }
}
