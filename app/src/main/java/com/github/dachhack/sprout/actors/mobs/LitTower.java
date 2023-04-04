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
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.blobs.ConfusionGas;
import com.github.dachhack.sprout.actors.blobs.ToxicGas;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Terror;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.particles.SparkParticle;
import com.github.dachhack.sprout.items.RedDewdrop;
import com.github.dachhack.sprout.items.scrolls.ScrollOfPsionicBlast;
import com.github.dachhack.sprout.items.weapon.enchantments.Death;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.traps.LightningTrap;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.sprites.LitTowerSprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class LitTower extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		name = Messages.get(this, "name");
		spriteClass = LitTowerSprite.class;

		HP = HT = 600;
		defenseSkill = 1000;

		EXP = 25;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
		lootChance = 1f;
		
	}
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public int damageRoll() {
		return 0;
	}

		
	@Override
	public int attackSkill(Char target) {
		return 100;
	}

	@Override
	public int dr() {
		return 1000;
	}
	

	@Override
	public void damage(int dmg, Object src) {
	}
	
	@Override
	protected boolean act() {
		if(Level.distance(pos, Dungeon.hero.pos)<5 && Dungeon.hero.isAlive() && checkOtiluke()){
			zapAll(Dungeon.hero.pos);
		}
		return super.act();
	}
	
	@Override
	public void call() {
		next();
	}
	
	protected boolean checkOtiluke(){
      boolean check = false;
		
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (mob instanceof Otiluke) {
			check=true;	
			}
		}
		return check;
	}
	

	protected boolean heroNear (){
		boolean check=false;
		for (int i : Level.NEIGHBOURS9DIST2){
			int cell=pos+i;
			if (Actor.findChar(cell) != null	
				&& (Actor.findChar(cell) instanceof Hero)
				){
				check=true;
			}			
		}		
		return check;
	}
	
	
	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		return false;
	}


	public void zapAll(int loc){

		yell(Messages.get(this, "zap"));
		
		Char hero=Dungeon.hero;
				
	    int mobDmg=Random.Int(100, 600);
		
		
		 boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[loc];
			
			
			  if (visible) {
				((LitTowerSprite) sprite).zap(loc);
			  }
			
			  
			  hero.damage(mobDmg, LightningTrap.LIGHTNING);

			  hero.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			  hero.sprite.flash();
			
			  Camera.main.shake(2, 1f);
//			  GLog.n("The lightning towers are too strong. Perhaps you can disable them by going to the right?");
	}

	@Override
	public String description() {
		return Messages.get(this, "desc");
	}
	
	@Override
	public void add(Buff buff) {
	}
	

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(ConfusionGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
