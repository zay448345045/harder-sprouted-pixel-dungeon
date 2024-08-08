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

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.items.OtilukesJournal;
import com.github.dachhack.sprout.items.artifacts.DriedRose;
import com.github.dachhack.sprout.items.artifacts.TimekeepersHourglass;
import com.github.dachhack.sprout.scenes.InterlevelScene;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.ItemSprite;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.Game;

public class WndOtiluke extends Window {

	//	private static final String TXT_FARAWELL = "Where would you like to go?";
	public static final float TIME_TO_USE = 1;

    
	private static final int PAGES = 10;
	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndOtiluke(final boolean[] rooms, final OtilukesJournal item) {

		super();
		
		String[] roomNames = new String[PAGES];
		//		roomNames[0] = "Safe Room";
		//		roomNames[1] = "Sokoban 1";
		//		roomNames[2] = "Sokoban 2";
		//		roomNames[3] = "Sokoban 3";
		//		roomNames[4] = "Sokoban 4";
		roomNames[0] = Messages.get(WndOtiluke.class, "s0");
		roomNames[1] = Messages.get(WndOtiluke.class, "s1");
		roomNames[2] = Messages.get(WndOtiluke.class, "s2");
		roomNames[3] = Messages.get(WndOtiluke.class, "s3");
		roomNames[4] = Messages.get(WndOtiluke.class, "s4");
		roomNames[5] = Messages.get(WndOtiluke.class, "dol");
		roomNames[6] = Messages.get(WndOtiluke.class, "vau");
		roomNames[7] = Messages.get(WndOtiluke.class, "dragon");
	
		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Utils.capitalize(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(Messages.get(WndOtiluke.class, "where"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, 0);
		add(message);
		
		if (rooms[0]){
		RedButton btn1 = new RedButton(roomNames[0]) {
			@Override
			protected void onClick() {
				item.returnDepth = Dungeon.depth;
				item.returnPos = Dungeon.hero.pos;
				port(0, item.firsts[0]);
				if (!Dungeon.playtest){
				   item.firsts[0]=false;
				}
				item.charge=0;
			}
		};
		btn1.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add(btn1);
		resize(WIDTH, (int) btn1.bottom());
		}
		
		int buttons=1;
		
		for (int i=1; i<PAGES; i++) {	
			final int portnum=i;
			if (rooms[i]){
				buttons++;
				RedButton btn = new RedButton(roomNames[i]) {
					@Override
					protected void onClick() {
						item.returnDepth = Dungeon.depth;
						item.returnPos = Dungeon.hero.pos;
						port(portnum, item.firsts[portnum]);
						item.firsts[portnum]=false;
						item.charge=0;
					}
				};
				
				btn.setRect(0, buttons*BTN_HEIGHT + (buttons+2)*GAP, WIDTH, BTN_HEIGHT);	
				
				add(btn);
				resize(WIDTH, (int) btn.bottom());
			}
		}		
	}

	
	public static void port(int room, boolean first){
		
		 Hero hero = Dungeon.hero;
		 hero.spend(TIME_TO_USE);
		 
		Buff buff = Dungeon.hero
				.buff(TimekeepersHourglass.timeFreeze.class);
		if (buff != null)
			buff.detach();

		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
			if (mob instanceof DriedRose.GhostHero)
				mob.destroy();
		       	
    	  
		InterlevelScene.mode = InterlevelScene.Mode.JOURNAL;
		
		InterlevelScene.returnDepth = Dungeon.depth;
		InterlevelScene.returnPos = Dungeon.hero.pos;
		InterlevelScene.journalpage = room;
		if (room != 5 && room != 0) {
			InterlevelScene.first = true;
		} else {
			InterlevelScene.first = first;
		}

		Game.switchScene(InterlevelScene.class);
	}
}
