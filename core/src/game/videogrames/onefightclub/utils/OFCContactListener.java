package game.videogrames.onefightclub.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.videogrames.onefightclub.actors.Enemy;

public class OFCContactListener implements ContactListener {
	// private boolean playerGrounded;
	private int numFootContacts;

	public boolean isPlayerGrounded() {
		return (numFootContacts > 0);
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData() == null || fb.getUserData() == null)
			return;

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("player")) {
			((Enemy) fa.getBody().getUserData()).setIsDead(true);
		} else if (fa.getUserData().equals("player") && fb.getUserData().equals("enemy")) {
			((Enemy) fb.getBody().getUserData()).setIsDead(true);
		}

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			// playerGrounded = true;
			numFootContacts++;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
			// playerGrounded = true;
			numFootContacts++;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			// playerGrounded = false;
			numFootContacts--;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
			// playerGrounded = false;
			numFootContacts--;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
