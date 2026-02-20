package INF1009_P3_02.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Brightness {
    private float brightnessLevel;

    public Brightness() {
        this.brightnessLevel = 1.0f;
    }

    public void setLevel(float level) {

        this.brightnessLevel = Math.max(0, Math.min(1, level));
        System.out.println("[INFO] Brightness changed to: " + this.brightnessLevel);
    }

    public float getLevel() {
        return brightnessLevel;
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (brightnessLevel >= 1.0f) return;

        float alpha = 1.0f - brightnessLevel;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
