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
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.ui.CheckBox;
import com.github.dachhack.sprout.ui.IconButton;
import com.github.dachhack.sprout.ui.Icons;
import com.github.dachhack.sprout.ui.Window;
import com.watabou.noosa.RenderedText;

import java.util.ArrayList;

public class WndChallenges extends Window {

	private static final int WIDTH = 108;
	private static final int TTL_HEIGHT = 12;
	private static final int BTN_HEIGHT = 18;
	private static final int GAP = 1;

	private static final String TITLE = Messages.get(WndChallenges.class,"title");

	private boolean editable;
	private ArrayList<CheckBox> boxes;

	public WndChallenges(int checked, boolean editable) {

		super();

		this.editable = editable;

		RenderedText title = PixelScene.renderText(Messages.get(this, "title"), 9);
		title.hardlight(TITLE_COLOR);
		title.x = PixelScene.align(camera, (WIDTH - title.width()) / 2);
		title.y = PixelScene.align(camera, (TTL_HEIGHT - title.height()) / 2);
		PixelScene.align(title);
		add(title);

		boxes = new ArrayList<CheckBox>();

		float pos = TTL_HEIGHT;
		for (int i=0; i < Challenges.NAMES.length; i++) {

			final String challenge = Challenges.NAMES[i];

			CheckBox cb = new CheckBox( Messages.get(Challenges.class, challenge) );
			cb.checked( (checked & Challenges.MASKS[i]) != 0 );
			cb.active = editable;

			if (i > 0) {
				pos += GAP;
			}
			cb.setRect( 0, pos, WIDTH-16, BTN_HEIGHT );

			add( cb );
			boxes.add( cb );

			IconButton info = new IconButton(Icons.get(Icons.INFO)){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(
							new WndMessage(Messages.get(Challenges.class, challenge+"_desc"))
					);
				}
			};
			info.setRect(cb.right(), pos, 16, BTN_HEIGHT);
			add(info);

			pos = cb.bottom();
		}

		resize(WIDTH, (int) pos);
	}

	@Override
	public void onBackPressed() {

		if (editable) {
			int value = 0;
			for (int i = 0; i < boxes.size(); i++) {
				if (boxes.get(i).checked()) {
					value |= Challenges.MASKS[i];
				}
			}
			ShatteredPixelDungeon.challenges(value);
		}

		super.onBackPressed();
	}
}