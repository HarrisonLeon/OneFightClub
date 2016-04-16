package game.videogrames.onefightclub.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.videogrames.onefightclub.actors.Enemy;
import game.videogrames.onefightclub.actors.Player;
import game.videogrames.onefightclub.actors.PowerUp;
import game.videogrames.onefightclub.actors.Weapon;

public class OFCContactListener implements ContactListener {
	// private boolean playerGrounded;

	int numFootContacts = 0;

	public boolean isPlayerGrounded() {
		return (numFootContacts > 0);
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if (fa.getUserData().equals("enemy") && (fb.getUserData().equals("player"))) {
			((Enemy) fa.getBody().getUserData()).setIsDead(true);
			 ((Player) fb.getBody().getUserData()).takeDamage(1);
		} else if (fa.getUserData().equals("player") && fb.getUserData().equals("enemy")) {
			((Enemy) fb.getBody().getUserData()).setIsDead(true);
			 ((Player) fa.getBody().getUserData()).takeDamage(1);
		}
		
		if (fa.getUserData().equals("player") && fb.getUserData().equals("enemy")) {
			System.out.println("PLZ");
			//((Player)fa.getBody().getUserData()).GetPowerUp();
			//((PowerUp)fb.getBody().getUserData()).setIsDead(true);
		}
		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("player")) {
			System.out.println("PLZZZZ");
			//((Player)fb.getBody().getUserData()).GetPowerUp();
			//((PowerUp)fa.getBody().getUserData()).setIsDead(true);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("enemy")) {
			Enemy faEnemy = (Enemy) fa.getBody().getUserData();
			if (faEnemy.getMovingRight()) {
				faEnemy.setMovingRight(false);
				faEnemy.setMovingLeft(true);
			} else {
				faEnemy.setMovingLeft(false);
				faEnemy.setMovingRight(true);
			}
			Enemy fbEnemy = (Enemy) fb.getBody().getUserData();
			if (fbEnemy.getMovingRight()) {
				fbEnemy.setMovingRight(false);
				fbEnemy.setMovingLeft(true);
			} else {
				fbEnemy.setMovingLeft(false);
				fbEnemy.setMovingRight(true);
			}
		}
		
		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("edge")) {
			Enemy faEnemy = (Enemy) fa.getBody().getUserData();
			if (faEnemy.getMovingRight()) {
				//System.out.println("in enemy 1");
				faEnemy.setMovingRight(false);
				faEnemy.setMovingLeft(true);
			} else {
				//System.out.println("in enemy 1 reverse");
				faEnemy.setMovingLeft(false);
				faEnemy.setMovingRight(true);
			}
		}
		
		if (fa.getUserData().equals("edge") && fb.getUserData().equals("enemy")) {
			Enemy fbEnemy = (Enemy) fb.getBody().getUserData();
			if (fbEnemy.getMovingRight()) {
				//System.out.println("in enemy 2");
				fbEnemy.setMovingRight(false);
				fbEnemy.setMovingLeft(true);
			} else {
				//System.out.println("in enemy 2 reverse");
				fbEnemy.setMovingLeft(false);
				fbEnemy.setMovingRight(true);
			}
		}
		
		if (fa.getUserData().equals("player") && fb.getUserData().equals("powerup")) {
			((Player)fa.getBody().getUserData()).GetPowerUp();
			((PowerUp)fb.getBody().getUserData()).setIsDead(true);
		}
		if (fb.getUserData().equals("powerup") && fb.getUserData().equals("player")) {
			((Player)fb.getBody().getUserData()).GetPowerUp();
			((PowerUp)fa.getBody().getUserData()).setIsDead(true);
		}

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			numFootContacts++;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
			numFootContacts++;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData() != null && fa.getUserData().equals("player.foot")) {
			numFootContacts--;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("player.foot")) {
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
