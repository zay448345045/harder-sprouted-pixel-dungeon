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
package com.github.dachhack.sprout.actors.mobs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.blobs.CorruptGas;
import com.github.dachhack.sprout.actors.blobs.ToxicGas;
import com.github.dachhack.sprout.actors.hero.HeroClass;
import com.github.dachhack.sprout.items.Generator;
import com.github.dachhack.sprout.items.weapon.enchantments.Death;
import com.github.dachhack.sprout.sprites.GullinSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Gullin extends Kupua {
	
	{
		name = Messages.get(this, "name");
		spriteClass = GullinSprite.class;

		HP = HT = 2200;

		EXP = 20;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(200, 400);
	}
	
	@Override
	public void die(Object cause) {

		if (Dungeon.limitedDrops.nornstones.count<5 
				&& Random.Int(5)<3
				){
			if(Dungeon.hero.heroClass==HeroClass.HUNTRESS){
				Dungeon.level.drop(Generator.random(Generator.Category.NORNSTONE), pos).sprite.drop();
			} else {
				Dungeon.level.drop(Generator.random(Generator.Category.NORNSTONE2), pos).sprite.drop(); 
			}			
			Dungeon.limitedDrops.nornstones.count++;
		}		
			
		super.die(cause);					
	}

	@Override
	public String description() {
		return Messages.get(this, "desc");
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(CorruptGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
