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

import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndOptions extends Window {

	private static final int WIDTH = 120;
	private static final int MARGIN = 2;
	private static final int BUTTON_HEIGHT = 20;

	public WndOptions(String title, String message, String... options) {
		super();

		RenderedTextMultiline tfTitle = PixelScene.renderMultiline(title, 9);
		tfTitle.hardlight(TITLE_COLOR);
		tfTitle.setPos(MARGIN, MARGIN);
		tfTitle.maxWidth(width - MARGIN * 2);
		add(tfTitle);

		RenderedTextMultiline tfMesage = PixelScene.renderMultiline(6);
		tfMesage.text(message, width - MARGIN * 2);
		tfMesage.setPos(MARGIN, tfTitle.top() + tfTitle.height() + MARGIN);
		add(tfMesage);

		float pos = tfMesage.bottom() + MARGIN;

		for (int i = 0; i < options.length; i++) {
			final int index = i;
			RedButton btn = new RedButton(options[i]) {
				@Override
				protected void onClick() {
					hide();
					onSelect(index);
				}
			};
			btn.setRect(MARGIN, pos, WIDTH - MARGIN * 2, BUTTON_HEIGHT);
			add(btn);

			pos += BUTTON_HEIGHT + MARGIN;
		}

		resize(WIDTH, (int) pos);
	}

	protected void onSelect(int index) {
	};
}
