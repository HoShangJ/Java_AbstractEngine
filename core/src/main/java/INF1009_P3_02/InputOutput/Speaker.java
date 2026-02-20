package INF1009_P3_02.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

//Manages audio volume settings and playback
public class Speaker {
    private float masterVolume;
    private Music backgroundMusic;
    private Sound collisionSound;
    private boolean audioEnabled = true;
    private long CollisionCD = 0;

    public Speaker() {
        this.masterVolume = 0.5f;
    }

    public void loadContent() {
        try {
            // Check if audio files exist before trying to load them
            if (Gdx.files.internal("audio/music/background.mp3").exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/background.mp3"));
                backgroundMusic.setLooping(true);
                backgroundMusic.setVolume(masterVolume);
            } else {
                System.out.println("Info: Background music file not found (audio/music/background.mp3). Music disabled.");
            }

            if (Gdx.files.internal("audio/Sounds/Collision.ogg").exists()) {
                collisionSound = Gdx.audio.newSound(Gdx.files.internal("audio/Sounds/collision.ogg"));
            } else {
                System.out.println("Info: Collision sound file not found (audio/Sounds/collision.ogg). Collision sounds disabled.");
            }

            // Only disable audio if both files are missing
            if (backgroundMusic == null && collisionSound == null) {
                audioEnabled = false;
                System.out.println("Info: No audio files found. Audio is disabled.");
                System.out.println("To enable audio, add files to:");
                System.out.println("  - assets/audio/music/background.mp3");
                System.out.println("  - assets/audio/Sounds/collision.ogg");
            }
        } catch (Exception e) {
            audioEnabled = false;
            System.err.println("Warning: Could not load audio files. Audio will be disabled.");
            System.err.println("To enable audio, add files to:");
            System.err.println("  - assets/audio/music/background.mp3");
            System.err.println("  - assets/audio/Sounds/collision.ogg");
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (audioEnabled && backgroundMusic != null) {
            if (!backgroundMusic.isPlaying()) {
                backgroundMusic.play();
            }
        }
    }

    public void playCollisionSound() {
        long now = com.badlogic.gdx.utils.TimeUtils.millis();
        if (now - CollisionCD < 150) return; // 150ms cooldown
        CollisionCD = now;

        if (audioEnabled && collisionSound != null) {
            long id = collisionSound.play(1.0f);
            collisionSound.setPitch(id, 1.2f);
        }
    }

    public void setVolume(float volume) {
        this.masterVolume = Math.max(0, Math.min(1, volume));
        System.out.println("[INFO] Volume changed to: " + this.masterVolume);
        if (audioEnabled && backgroundMusic != null) {
            backgroundMusic.setVolume(this.masterVolume);
        }
    }

    public float getVolume() {
        return masterVolume;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
        if (collisionSound != null) collisionSound.dispose();
    }
}
