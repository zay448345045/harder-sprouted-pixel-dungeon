/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.github.dachhack.sprout.levels.features;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.particles.ElmoParticle;
import com.github.dachhack.sprout.levels.DeadEndLevel;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.Terrain;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.windows.WndMessage;
import com.watabou.noosa.audio.Sample;

public class Sign {

	public static void read(int pos) {

		if (Dungeon.level instanceof DeadEndLevel) {

			GameScene.show( new WndMessage( Messages.get(Sign.class, "dead_end") ) );

		} else {

			int index = Dungeon.depth - 1;

			if (Dungeon.depth <= 21) {
				GameScene.show( new WndMessage( Messages.get(Sign.class, "tip_"+Dungeon.depth) ) );

				if (index >= 21) {

					Level.set(pos, Terrain.EMBERS);
					GameScene.updateMap(pos);
					GameScene.discoverTile(pos, Terrain.SIGN);

					GLog.w( Messages.get(Sign.class, "burn") );

					CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
					Sample.INSTANCE.play(Assets.SND_BURNING);
				}

			}
		}
	}
	
	public static void readPit(int pos) {
				GameScene.show(new WndMessage(Messages.get(Sign.class,"pit")));
			}
			
	
}
