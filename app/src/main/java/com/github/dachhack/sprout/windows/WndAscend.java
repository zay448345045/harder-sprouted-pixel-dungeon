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
import com.github.dachhack.sprout.items.DewVial;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.ItemSprite;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.Utils;

public class WndAscend extends Window {

	private static final String TXT_MESSAGE = Messages.get(WndAscend.class, "message");
	private static final String TXT_REWARD = Messages.get(WndAscend.class, "ok");

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	public WndAscend() {

		super();
		
		DewVial dewvial = new DewVial();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(dewvial.image(), null));
		titlebar.label(Utils.capitalize(dewvial.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(WndAscend.class, "message"), 6);
		message.maxWidth = WIDTH;
		//message.measure();
		//message.y = titlebar.bottom() + GAP;
		message.setPos(0, titlebar.bottom()+GAP);
		add(message);

		RedButton btnReward = new RedButton(Messages.get(WndAscend.class, "ok")) {
			@Override
			protected void onClick() {
				Dungeon.level.forcedone=true;
				hide();
			}
		};
		btnReward.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnReward);

		resize(WIDTH, (int) btnReward.bottom());
	}

	
}
