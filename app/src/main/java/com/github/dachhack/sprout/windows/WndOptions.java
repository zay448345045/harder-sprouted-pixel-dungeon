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
import com.watabou.noosa.Image;

public class WndOptions extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;

	private static final int MARGIN 		= 2;
	private static final int BUTTON_HEIGHT	= 20;

	public WndOptions( String title, String message, String... options ) {
		super();

		int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

		float pos = MARGIN;
		if (title != null) {
			RenderedTextMultiline tfTitle = PixelScene.renderMultiline(title, 9);
			tfTitle.hardlight(TITLE_COLOR);
			tfTitle.setPos(MARGIN, pos);
			tfTitle.maxWidth(width - MARGIN * 2);
			add(tfTitle);

			pos = tfTitle.bottom() + 3*MARGIN;
		}

		RenderedTextMultiline tfMesage = PixelScene.renderMultiline( 6 );
		tfMesage.text(message, width - MARGIN * 2);
		tfMesage.setPos( MARGIN, pos );
		add( tfMesage );

		pos = tfMesage.bottom() + 2*MARGIN;

		for (int i=0; i < options.length; i++) {
			final int index = i;
			RedButton btn = new RedButton( options[i] ) {
				@Override
				protected void onClick() {
					hide();
					onSelect( index );
				}
			};
			btn.enable(enabled(i));
			btn.setRect( MARGIN, pos, width - MARGIN * 2, BUTTON_HEIGHT );
			add( btn );

			pos += BUTTON_HEIGHT + MARGIN;
		}

		resize( width, (int)pos );
	}

	public WndOptions(Image image, String message, String desc, String update, String changes) {
	}

	protected boolean enabled( int index ){
		return true;
	}

	protected void onSelect( int index ) {}
}
