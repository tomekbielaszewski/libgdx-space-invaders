package pl.grizwold.space.invaders.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.function.BiFunction;

public class Ship {
    private static final float SHIP_SPEED = 10;
    private static final Vector2 BULLET_SPEED = new Vector2(0, 30f);
    private static final int GUN_COOLDOWN = 500;

    private final Shape shape;
    private final Body body;
    private BiFunction<Vector2, Vector2, Bullet> bulletFactory;

    private final Array<Bullet> bullets;
    private long lastShot;

    public Ship(Body body, Shape shape) {
        this.shape = shape;
        this.body = body;
        this.bullets = new Array<>(10);
    }

    public void render() {
        for (Bullet bullet : this.bullets) {
            bullet.render();
        }
    }

    public void update() {
        removeDestroyedBullets();
        moveShip();
        fire();
    }

    private void removeDestroyedBullets() {
        Iterator<Bullet> iterator = this.bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if(bullet.isDestroyed) {
                iterator.remove();
                bullet.dispose();
                continue;
            }
            bullet.update();
        }
    }

    private void fire() {
        if(Gdx.input.isKeyPressed(Input.Keys.F) && TimeUtils.millis() - lastShot > GUN_COOLDOWN) {
            bullets.add(bulletFactory.apply(this.body.getPosition(), BULLET_SPEED));
            lastShot = TimeUtils.millis();
        }
    }

    private void moveShip() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-SHIP_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(SHIP_SPEED, 0);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }

    public void dispose() {
        shape.dispose();
    }

    public void setBulletFactory(BiFunction<Vector2, Vector2, Bullet> createBullet) {
        this.bulletFactory = createBullet;
    }
}
