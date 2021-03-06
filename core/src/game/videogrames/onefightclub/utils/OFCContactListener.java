package game.videogrames.onefightclub.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.videogrames.onefightclub.actors.Enemy;
import game.videogrames.onefightclub.actors.Player;
import game.videogrames.onefightclub.actors.PowerUp;
import game.videogrames.onefightclub.actors.Weapon;
import game.videogrames.onefightclub.screens.GameScreen;

public class OFCContactListener implements ContactListener {
	// private boolean playerGrounded;

	int numFootContacts = 0;
	private int killstreak = 0;
	private int kills = 0;

	private Sound sound_death = Gdx.audio.newSound(Gdx.files.internal("sounds/Enemy_Death.wav"));
	private Sound sound_player_damage = Gdx.audio.newSound(Gdx.files.internal("sounds/Player_Damage.wav"));

	public boolean isPlayerGrounded() {
		return (numFootContacts > 0);
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getUserData().equals("enemy") && fb.getUserData() instanceof Weapon) {
			GameScreen.addScore();
			if (!GameScreen.isOver()) {
				sound_death.play(0.07f);
			}

			((Enemy) fa.getBody().getUserData()).setIsDead(true);
			Constants.ui.setnumKills(Constants.ui.numKills() + 1);
			killstreak++;
			kills++;
			if (kills == 20) {
				if (Constants.ui.killStreak() < killstreak) {
					Constants.ui.setkillStreak(killstreak);
				}
			}
		} else if (fa.getUserData() instanceof Weapon && fb.getUserData().equals("enemy")) {
			GameScreen.addScore();
			if (!GameScreen.isOver()) {
				sound_death.play(0.07f);
			}
			((Enemy) fb.getBody().getUserData()).setIsDead(true);
			Constants.ui.setnumKills(Constants.ui.numKills() + 1);
			killstreak++;
			kills++;
			if (kills == 20) {
				if (Constants.ui.killStreak() < killstreak) {
					Constants.ui.setkillStreak(killstreak);
				}
			}
		}

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("player")) {
			((Player) fb.getBody().getUserData()).takeDamage(1);
			if (Constants.ui.killStreak() < killstreak) {
				Constants.ui.setkillStreak(killstreak);
			}
			if (!GameScreen.isOver()) {
				sound_player_damage.play(1.5f);
			}

			killstreak = 0;
			Constants.ui.setnumDeaths(Constants.ui.numDeaths() + 1);
		} else if (fa.getUserData().equals("player") && fb.getUserData().equals("enemy")) {
			((Player) fa.getBody().getUserData()).takeDamage(1);
			if (Constants.ui.killStreak() < killstreak) {
				Constants.ui.setkillStreak(killstreak);
			}
			if (!GameScreen.isOver()) {
				sound_player_damage.play(2.0f);
			}
			killstreak = 0;
			Constants.ui.setnumDeaths(Constants.ui.numDeaths() + 1);
		}

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("enemy")) {
			Enemy enemyA = (Enemy) fa.getBody().getUserData();
			if (enemyA.getBody().getLinearVelocity().x > 0.0f) {
				enemyA.moveLeft();
			} else {
				enemyA.moveRight();
			}
			Enemy enemyB = (Enemy) fb.getBody().getUserData();
			if (enemyB.getBody().getLinearVelocity().x > 0.0f) {
				enemyB.moveLeft();
			} else {
				enemyB.moveRight();
			}
		}

		if (fa.getUserData().equals("enemy") && fb.getUserData().equals("edge")) {
			Enemy enemy = (Enemy) fa.getBody().getUserData();
			if (enemy.getBody().getLinearVelocity().x > 0.0f) {
				enemy.moveLeft();
			} else {
				enemy.moveRight();
			}
		}

		if (fa.getUserData().equals("edge") && fb.getUserData().equals("enemy")) {
			Enemy enemy = (Enemy) fb.getBody().getUserData();
			if (enemy.getBody().getLinearVelocity().x > 0.0f) {
				enemy.moveLeft();
			} else {
				enemy.moveRight();
			}
		}

		if (fa.getUserData().equals("player") && fb.getUserData().equals("powerup")) {
			((Player) fa.getBody().getUserData()).GetPowerUp((PowerUp) fb.getBody().getUserData());
			((PowerUp) fb.getBody().getUserData()).setIsDead(true);
		}
		if (fb.getUserData().equals("powerup") && fb.getUserData().equals("player")) {
			((Player) fb.getBody().getUserData()).GetPowerUp((PowerUp) fb.getBody().getUserData());
			((PowerUp) fa.getBody().getUserData()).setIsDead(true);
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
