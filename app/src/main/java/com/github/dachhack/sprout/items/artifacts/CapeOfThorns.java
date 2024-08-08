package com.github.dachhack.sprout.items.artifacts;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.ui.BuffIndicator;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 03/09/2014.
 */
public class CapeOfThorns extends Artifact {

	{
//		name = "Cape of Thorns";
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.ARTIFACT_CAPE;

		level = 0;
		levelCap = 10;

		charge = 0;
		chargeCap = 100;
		cooldown = 0;

		defaultAction = "NONE";
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Thorns();
	}

	@Override
//	public String desc() {
//		String desc = "These collapsed sheets of metal from the DM-300 have formed together into a rigid wearable "
//				+ "cape. The metal is old and coated in thick flakes of rust. It seems to store a deep energy, "
//				+ "perhaps it has some of the DM-300's power?";
//		if (isEquipped(Dungeon.hero)) {
//			desc += "\n\n";
//			if (cooldown == 0)
//				desc += "The cape feels reassuringly heavy on your shoulders. You're not sure if it will directly "
//						+ "help you in a fight, but it seems to be gaining energy from the physical damage you take.";
//			else
//				desc += "The cape seems to be releasing some stored energy, it is radiating power at all angles. "
//						+ "You feel very confident that the cape can protect you from nearby enemies right now.";
//		}
//
//		return desc;
//	}
	public String desc() {
		String desc = Messages.get(this, "desc");
		if (isEquipped(Dungeon.hero)) {
			desc += "\n\n";
			if (cooldown == 0)
				desc += Messages.get(this, "desc_inactive");
			else
				desc += Messages.get(this, "desc_active");
		}

		return desc;
	}

	public class Thorns extends ArtifactBuff {

		@Override
		public boolean act() {
			if (cooldown > 0) {
				cooldown--;
				if (cooldown == 0) {
					BuffIndicator.refreshHero();
//					GLog.w("Your Cape becomes inert again.");
					GLog.w(Messages.get(this, "inert"));
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}

		public int proc(int damage, Char attacker) {
			if (cooldown == 0) {
				charge += damage * (0.5 + level * 0.05);
				if (charge >= chargeCap) {
					charge = 0;
					cooldown = 10 + level;
//					GLog.p("Your Cape begins radiating energy, you feel protected!");
					GLog.p(Messages.get(this, "radiating"));
					BuffIndicator.refreshHero();
				}
			}

			if (cooldown != 0) {
				int deflected = Random.NormalIntRange(0, damage);
				damage -= deflected;

				attacker.damage(deflected, this);

				exp += deflected;

				if (exp >= (level + 1) * 5 && level < levelCap) {
					exp -= (level + 1) * 5;
					upgrade();
//					GLog.p("Your Cape grows stronger!");
					GLog.p(Messages.get(this, "levelup"));
				}

			}
			updateQuickslot();
			return damage;
		}

		@Override
//		public String toString() {
//			return "Thorns";
//		}

		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.THORNS;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}
		@Override
		public String desc() {
			return Messages.get(CapeOfThorns.class, "buffdesc", dispTurns(cooldown));
		}
	}

}
