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
package com.github.dachhack.sprout.windows;

import com.github.dachhack.sprout.Challenges;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.mobs.npcs.Ghost;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.FetidRatSprite;
import com.github.dachhack.sprout.sprites.GnollTricksterSprite;
import com.github.dachhack.sprout.sprites.GreatCrabSprite;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.GLog;

public class WndSadGhost extends Window {

//	private static final String TXT_RAT = "Thank you, that horrid rat is slain and I can finally rest..."
//			+ "I wonder what twisted magic created such a foul creature...\n\n";
//	private static final String TXT_GNOLL = "Thank you, that scheming gnoll is slain and I can finally rest..."
//			+ "I wonder what twisted magic made it so smart...\n\n";
//	private static final String TXT_CRAB = "Thank you, that giant crab is slain and I can finally rest..."
//			+ "I wonder what twisted magic allowed it to live so long...\n\n";
//	private static final String TXT_GIVEITEM = "Please take one of these items, they are useless to me now... "
//			+ "Maybe they will help you in your journey...\n\n"
//			+ "Also... There is an item lost in this dungeon that is very dear to me..."
//			+ "If you ever... find my... rose......";
//	private static final String TXT_WEAPON = "Ghost's weapon";
//	private static final String TXT_ARMOR = "Ghost's armor";
private static final String TXT_RAT = Messages.get(WndSadGhost.class, "rat");
	private static final String TXT_GNOLL = Messages.get(WndSadGhost.class, "gnoll");
	private static final String TXT_CRAB = Messages.get(WndSadGhost.class, "crab");
	private static final String TXT_GIVEITEM = Messages.get(WndSadGhost.class, "giveitem");
	private static final String TXT_WEAPON = Messages.get(WndSadGhost.class, "weapon");
	private static final String TXT_ARMOR = Messages.get(WndSadGhost.class, "armor");

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndSadGhost(final Ghost ghost, final int type) {

		super();

		IconTitle titlebar = new IconTitle();
		RenderedTextMultiline message;
		switch (type) {
		case 1:
		default:
			titlebar.icon(new FetidRatSprite());
			titlebar.label(Messages.get(WndSadGhost.class, "dr"));
			message = PixelScene.renderMultiline(Messages.get(WndSadGhost.class, "rat") + Messages.get(WndSadGhost.class, "giveitem"), 6);
			break;
			case 2:
				titlebar.icon(new GnollTricksterSprite());
				titlebar.label(Messages.get(WndSadGhost.class, "dt"));
				message =
						PixelScene.renderMultiline(Messages.get(WndSadGhost.class, "gnoll") + Messages.get(WndSadGhost.class, "giveitem"), 6);
				break;
			case 3:
				titlebar.icon(new GreatCrabSprite());
				titlebar.label(Messages.get(WndSadGhost.class, "dc"));
				message = PixelScene.renderMultiline(Messages.get(WndSadGhost.class, "crab") + Messages.get(WndSadGhost.class, "giveitem"), 6);
				break;

		}

		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnWeapon = new RedButton(Messages.get(WndSadGhost.class, "weapon")) {
			@Override
			protected void onClick() {
				selectReward(ghost, Ghost.Quest.weapon);
			}
		};
		btnWeapon.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnWeapon);

		if (!Dungeon.isChallenged(Challenges.NO_ARMOR)) {
			RedButton btnArmor = new RedButton(Messages.get(WndSadGhost.class, "armor")) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.armor);
				}
			};
			btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnArmor);

			resize(WIDTH, (int) btnArmor.bottom());
		} else {
			resize(WIDTH, (int) btnWeapon.bottom());
		}
	}

	private void selectReward(Ghost ghost, Item reward) {

		hide();

		if (reward.doPickUp(Dungeon.hero)) {
			GLog.i(Messages.get(Hero.class,"have"), reward.name());
		} else {
			Dungeon.level.drop(reward, ghost.pos).sprite.drop();
		}

//		ghost.yell("Farewell, adventurer!");
		ghost.yell(Messages.get(WndSadGhost.class, "farewell"));
		ghost.die(null);

		Ghost.Quest.complete();
	}
}
