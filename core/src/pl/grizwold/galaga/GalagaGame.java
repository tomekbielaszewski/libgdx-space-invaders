package pl.grizwold.galaga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import pl.grizwold.galaga.screens.MainMenuScreen;

public class GalagaGame extends Game {
    public static int WIDTH;
    public static int HEIGHT;

    public SpriteBatch batch;
    public BitmapFont font;

    public GalagaGame(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        Box2D.init();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
