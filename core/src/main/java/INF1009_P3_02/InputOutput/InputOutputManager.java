package INF1009_P3_02.InputOutput;

import INF1009_P3_02.ControlScheme;
import INF1009_P3_02.Movement.MovementManager;
import INF1009_P3_02.SettingsData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InputOutputManager {
    private final KeyboardListener keyboardListener;
    private final MovementManager movementManager;
    private final Speaker speaker;
    private final Brightness brightness;
    private final ShapeRenderer shapeRenderer;
    private final SettingsData settings;
    private EscapeListener escapeListener;
    private ControlScheme controlScheme = ControlScheme.WASD;

    public InputOutputManager(SettingsData settings, MovementManager movementManager) {
        this.settings = settings;
        this.movementManager = movementManager;
        this.keyboardListener = new KeyboardListener(this);
        this.speaker = new Speaker();
        this.brightness = new Brightness();
        this.shapeRenderer = new ShapeRenderer();

        // Load audio content and sync settings
        this.speaker.loadContent();
        syncSettingsToOutputs();
    }

    public void setControlScheme(ControlScheme scheme) {
        this.controlScheme = scheme;
    }


    public ControlScheme getControlScheme() {
        return controlScheme;
    }

    private void syncSettingsToOutputs() {
        speaker.setVolume(settings.volume);
        brightness.setLevel(settings.brightness);
    }

    public void applySettingsChanges() {
        syncSettingsToOutputs();
    }

    public void update(float dt) {
        keyboardListener.update(dt);
    }

    // Called by KeyboardListener for movement keys
    public void onKeyDetected(char key, float dt) {
        System.out.println("IOManager: Key " + key + " detected");
        if (movementManager != null) {
            movementManager.onKeyInput(key, dt);
        }
    }

    // Called by KeyboardListener for ESC key
    public void onEscapePressed() {
        System.out.println("IOManager: ESC key detected");
        if (escapeListener != null) {
            escapeListener.onEscapePressed();
        }
    }

    // Set the listener that will handle ESC events
    public void setEscapeListener(EscapeListener listener) {
        this.escapeListener = listener;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void dispose() {
        if (speaker != null) speaker.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }

    // Interface for ESC key handling
    public interface EscapeListener {
        void onEscapePressed();
    }
}
