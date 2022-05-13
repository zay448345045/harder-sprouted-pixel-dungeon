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

import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.items.Heap;
import com.github.dachhack.sprout.items.Heap.Type;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.artifacts.Artifact;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.sprites.ItemSprite;
import com.github.dachhack.sprout.ui.ItemSlot;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.Window;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;

public class WndInfoItem extends Window {

	private static final String TTL_CHEST = Messages.get(WndInfoItem.class,"chest");
	private static final String TTL_LOCKED_CHEST = Messages.get(WndInfoItem.class,"locked");
	private static final String TTL_CRYSTAL_CHEST = 	Messages.get(WndInfoItem.class,"crystal");
	private static final String TTL_TOMB = 	Messages.get(WndInfoItem.class,"tomb");
	private static final String TTL_SKELETON =	Messages.get(WndInfoItem.class,"skeletal");
	private static final String TTL_REMAINS = 	Messages.get(WndInfoItem.class,"heroes");
	private static final String TXT_WONT_KNOW = 	Messages.get(WndInfoItem.class,"you_wont");
	private static final String TXT_NEED_KEY = TXT_WONT_KNOW
			+ 	Messages.get(WndInfoItem.class,"but");
	private static final String TXT_INSIDE = 	Messages.get(WndInfoItem.class,"you_can");
	private static final String TXT_OWNER = 	Messages.get(WndInfoItem.class,"this");
	private static final String TXT_SKELETON = 	Messages.get(WndInfoItem.class,"this_is");
	private static final String TXT_REMAINS = 	Messages.get(WndInfoItem.class,"this_is_all");

	private static final float GAP = 2;

	private static final int WIDTH = 120;

	public WndInfoItem(Heap heap) {

		super();

		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {

			Item item = heap.peek();

			int color = TITLE_COLOR;
			if (item.levelKnown && item.level > 0) {
				color = ItemSlot.UPGRADED;
			} else if (item.levelKnown && item.level < 0) {
				color = ItemSlot.DEGRADED;
			}
			fillFields(item.image(), item.glowing(), color, item.toString(),
					item.info());

		} else {

			String title;
			String info;

			if (heap.type == Type.CHEST || heap.type == Type.MIMIC) {
				title = TTL_CHEST;
				info = TXT_WONT_KNOW;
			} else if (heap.type == Type.TOMB) {
				title = TTL_TOMB;
				info = TXT_OWNER;
			} else if (heap.type == Type.SKELETON) {
				title = TTL_SKELETON;
				info = TXT_SKELETON;
			} else if (heap.type == Type.REMAINS) {
				title = TTL_REMAINS;
				info = TXT_REMAINS;
			} else if (heap.type == Type.CRYSTAL_CHEST) {
				title = TTL_CRYSTAL_CHEST;
				if (heap.peek() instanceof Artifact)
					info = Utils.format(TXT_INSIDE,Messages.get(this,"an"));
				else
					info = Utils.format(TXT_INSIDE,
							Utils.indefinite(heap.peek().name()));
			} else {
				title = TTL_LOCKED_CHEST;
				info = TXT_NEED_KEY;
			}

			fillFields(heap.image(), heap.glowing(), TITLE_COLOR, title, info);

		}
	}

	public WndInfoItem(Item item) {

		super();

		int color = TITLE_COLOR;
		if (item.levelKnown && item.level > 0) {
			color = ItemSlot.UPGRADED;
		} else if (item.levelKnown && item.level < 0) {
			color = ItemSlot.DEGRADED;
		}

		fillFields(item.image(), item.glowing(), color, item.toString(),
				item.info());
	}

	private void fillFields(int image, ItemSprite.Glowing glowing,
			int titleColor, String title, String info) {

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(image, glowing));
		titlebar.label(Utils.capitalize(title), titleColor);
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline txtInfo = PixelScene.renderMultiline(info, 6);
		txtInfo.maxWidth(WIDTH);
		txtInfo.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add(txtInfo);

		resize(WIDTH, (int) (txtInfo.top() + txtInfo.height()));
	}
}
