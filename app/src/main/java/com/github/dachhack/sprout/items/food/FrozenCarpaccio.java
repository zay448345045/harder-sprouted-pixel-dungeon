/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.github.dachhack.sprout.items.food;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.buffs.Barkskin;
import com.github.dachhack.sprout.actors.buffs.Bleeding;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Cripple;
import com.github.dachhack.sprout.actors.buffs.Drowsy;
import com.github.dachhack.sprout.actors.buffs.Hunger;
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.actors.buffs.Poison;
import com.github.dachhack.sprout.actors.buffs.Slow;
import com.github.dachhack.sprout.actors.buffs.Vertigo;
import com.github.dachhack.sprout.actors.buffs.Weakness;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Random;

public class FrozenCarpaccio extends Food {

	{
//		name = "frozen carpaccio";
		name = Messages.get(this, "name");
		image = ItemSpriteSheet.CARPACCIO;

		energy = Hunger.STARVING - Hunger.HUNGRY;
		hornValue = 1;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(5)) {
			case 0:
				if(Dungeon.depth != 29){
//				GLog.i("You see your hands turn invisible!");
					GLog.i(Messages.get(this, "invis"));
				Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
				}
				break;
			case 1:
//				GLog.i("You feel your skin harden!");
				GLog.i(Messages.get(this, "hard"));
				Buff.affect(hero, Barkskin.class).level(hero.HT / 4);
				break;
			case 2:
//				GLog.i("Refreshing!");
				GLog.i(Messages.get(this, "refresh"));
				Buff.detach(hero, Poison.class);
				Buff.detach(hero, Cripple.class);
				Buff.detach(hero, Weakness.class);
				Buff.detach(hero, Bleeding.class);
				Buff.detach(hero, Drowsy.class);
				Buff.detach(hero, Slow.class);
				Buff.detach(hero, Vertigo.class);
				break;
			case 3:
//				GLog.i("You feel better!");
				GLog.i(Messages.get(this, "better"));
				if (hero.HP < hero.HT) {
					hero.HP = Math.min(hero.HP + hero.HT / 4, hero.HT);
					hero.sprite.emitter()
							.burst(Speck.factory(Speck.HEALING), 1);
				}
				break;
			}
		}
	}

//	@Override
//	public String info() {
//		return "It's a piece of frozen raw meat. The only way to eat it is "
//				+ "by cutting thin slices of it. And this way it's suprisingly good.";
//	}
@Override
public String info() {
	return Messages.get(this, "desc");
}

	@Override
	public int price() {
		return 10 * quantity;
	};

	public static Food cook(MysteryMeat ingredient) {
		FrozenCarpaccio result = new FrozenCarpaccio();
		result.quantity = ingredient.quantity();
		return result;
	}
	public static Food cook(Meat ingredient) {
		FrozenCarpaccio result = new FrozenCarpaccio();
		result.quantity = ingredient.quantity();
		return result;
	}
}
