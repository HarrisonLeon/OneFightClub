package game.videogrames.onefightclub.actors;

import com.badlogic.gdx.physics.box2d.Body;

public class Weapon extends StillSprite {

	private boolean active;

	public Weapon(Body body) {
		super(body);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
