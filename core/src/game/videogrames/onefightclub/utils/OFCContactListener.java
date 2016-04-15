package game.videogrames.onefightclub.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.videogrames.onefightclub.actors.Enemy;
import game.videogrames.onefightclub.actors.Weapon;

public class OFCContactListener implements ContactListener {
	private boolean playerGrounded;

	public boolean isPlayerGrounded() {
		return playerGrounded;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData().equals("enemy") && fb.getUserData() instanceof Weapon) {
			((Enemy) fa.getBody().getUserData()).setIsDead(true);
			// ((Player) fb.getBody().getUserData()).takeDamage(1);
		} else if (fa.getUserData() instanceof Weapon && fb.getUserData().equals("enemy")) {
			((Enemy) fb.getBody().getUserData()).setIsDead(true);
			// ((Player) fa.getBody().getUserData()).takeDamage(1);
		}

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("enemy")) {
			Enemy faEnemy = (Enemy) fa.getBody().getUserData();
			if (faEnemy.getMovingRight()) {
				faEnemy.setMovingRight(false);
				faEnemy.setMovingLeft(true);
			} else {
				faEnemy.setMovingRight(true);
				faEnemy.setMovingLeft(false);
			}
			Enemy fbEnemy = (Enemy) fb.getBody().getUserData();
			if (fbEnemy.getMovingRight()) {
				fbEnemy.setMovingRight(false);
				fbEnemy.setMovingLeft(true);
			} else {
				fbEnemy.setMovingRight(true);
				fbEnemy.setMovingLeft(false);
			}
		}

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			playerGrounded = true;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
			playerGrounded = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			playerGrounded = false;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
			playerGrounded = false;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
