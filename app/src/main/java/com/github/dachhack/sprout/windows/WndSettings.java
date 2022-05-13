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

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.scenes.PixelScene;
import com.github.dachhack.sprout.scenes.TitleScene;
import com.github.dachhack.sprout.ui.CheckBox;
import com.github.dachhack.sprout.ui.GameLog;
import com.github.dachhack.sprout.ui.OptionSlider;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.Toolbar;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;
@SuppressWarnings("All")
public class WndSettings extends WndTabbed {

	private static final int WIDTH = 112;
	private static final int WIDTH_L = 226;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;
	private static final int HEIGHT = 170;
	private static final int HEIGHT_L = 120;
	private static final int SLIDER_HEIGHT = 25;
	private static final int GAP_SML = 2;
	private static final int GAP_LRG = 5;

	private GameTab game;
	private GeneralTab gen;

	private int setScale = PixelScene.defaultZoom;
	private static int last_index = 0;

	public WndSettings() {
		super();

		game = new GameTab();
		add(game);

		gen = new GeneralTab();
		add(gen);


		add(new LabeledTab(Messages.get(this, "gen")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				gen.visible = gen.active = value;
				if (value) last_index = 0;
			}
		});

		add(new LabeledTab(Messages.get(this, "game")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				game.visible = game.active = value;
				if (value) last_index = 1;
			}
		});

		if (!ShatteredPixelDungeon.landscape()) {
			resize(WIDTH, HEIGHT);
		} else {
			resize(WIDTH_L, HEIGHT_L);
		}

		layoutTabs();

		select(last_index);

	}

	private class GeneralTab extends Group {

		public GeneralTab() {

			CheckBox musicMute = new CheckBox(Messages.get(WndSettings.class, "music")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.music(!checked());
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				musicMute.setRect(0, 0, WIDTH, BTN_HEIGHT);
			} else {
				musicMute.setRect(0, 0, WIDTH*2, BTN_HEIGHT);
			}
			musicMute.checked(!ShatteredPixelDungeon.music());
			add(musicMute);

			CheckBox btnSound = new CheckBox(Messages.get(WndSettings.class, "sound")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.soundFx(!checked());
					Sample.INSTANCE.play(Assets.SND_CLICK);
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnSound.setRect(0, musicMute.bottom() + GAP_SML, WIDTH, BTN_HEIGHT);
			} else {
				btnSound.setRect(0, musicMute.bottom() + GAP_SML, WIDTH*2, BTN_HEIGHT);
			}
			btnSound.checked(!ShatteredPixelDungeon.soundFx());
			add(btnSound);

			CheckBox chkFont = new CheckBox(Messages.get(WndSettings.class, "font")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.classicFont(!checked());
					Game.switchScene(TitleScene.class);
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				chkFont.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
			} else {
				chkFont.setRect(0, btnSound.bottom() + GAP, WIDTH * 2, BTN_HEIGHT);
			}
			chkFont.checked(!ShatteredPixelDungeon.classicFont());
			add(chkFont);

			RedButton btnOrientation = new RedButton(orientationText()) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.landscape(!ShatteredPixelDungeon.landscape());
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnOrientation.setRect(0, chkFont.bottom() + GAP, WIDTH, BTN_HEIGHT);
			} else {
				btnOrientation.setRect(0, chkFont.bottom() + GAP, WIDTH*2, BTN_HEIGHT);
			}
			add(btnOrientation);
		}

	}

	private class GameTab extends Group {
		public GameTab() {
			super();

			OptionSlider slots = new OptionSlider(Messages.get(WndSettings.class, "quickslots"), "0", "4", 0, 4) {
				@Override
				protected void onChange() {
					ShatteredPixelDungeon.quickSlots(getSelectedValue());
					Toolbar.updateLayout();
				}
			};
			slots.setSelectedValue(ShatteredPixelDungeon.quickSlots());
			if (!ShatteredPixelDungeon.landscape()) {
				slots.setRect(0, 0, WIDTH, SLIDER_HEIGHT);
			} else {
				slots.setRect(0, 0, WIDTH*2, SLIDER_HEIGHT);
			}
			add(slots);

			RenderedText barDesc = PixelScene.renderText(Messages.get(WndSettings.class, "toolbar_mode"), 9);
			if (!ShatteredPixelDungeon.landscape()) {
				barDesc.x = (WIDTH - barDesc.width()) / 2;
			} else {
				barDesc.x = (WIDTH_L - barDesc.width()) / 2;
			}
			barDesc.y = slots.bottom() + GAP_LRG;
			add(barDesc);

			RedButton btnSplit = new RedButton(Messages.get(WndSettings.class, "split")) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.toolbarMode(Toolbar.Mode.SPLIT.name());
					Toolbar.updateLayout();
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnSplit.setRect(1, barDesc.y + barDesc.height(), 36, BTN_HEIGHT);
			} else {
				btnSplit.setRect(1, barDesc.y + barDesc.height(), 74, BTN_HEIGHT);
			}
			add(btnSplit);

			RedButton btnGrouped = new RedButton(Messages.get(WndSettings.class, "group")) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.toolbarMode(Toolbar.Mode.GROUP.name());
					Toolbar.updateLayout();
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnGrouped.setRect(btnSplit.right() + 1, barDesc.y + barDesc.height(), 36, BTN_HEIGHT);
			} else {
				btnGrouped.setRect(btnSplit.right() + 1, barDesc.y + barDesc.height(), 74, BTN_HEIGHT);
			}
			add(btnGrouped);

			RedButton btnCentered = new RedButton(Messages.get(WndSettings.class, "center")) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.toolbarMode(Toolbar.Mode.CENTER.name());
					Toolbar.updateLayout();
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnCentered.setRect(btnGrouped.right() + 1, barDesc.y + barDesc.height(), 36, BTN_HEIGHT);
			} else {
				btnCentered.setRect(btnGrouped.right() + 1, barDesc.y + barDesc.height(), 72, BTN_HEIGHT);
			}
			add(btnCentered);

			CheckBox chkFlipToolbar = new CheckBox(Messages.get(WndSettings.class, "flip_toolbar")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.flipToolbar(checked());
					Toolbar.updateLayout();
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				chkFlipToolbar.setRect(0, btnCentered.bottom() + GAP, WIDTH, BTN_HEIGHT);
			} else {
				chkFlipToolbar.setRect(0, btnCentered.bottom() + GAP, WIDTH*2, BTN_HEIGHT);
			}
			chkFlipToolbar.checked(ShatteredPixelDungeon.flipToolbar());
			add(chkFlipToolbar);

			CheckBox btnBrightness = new CheckBox(Messages.get(WndSettings.class, "brtes")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.brightness(checked());
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnBrightness.setRect(0, chkFlipToolbar.bottom() + GAP, WIDTH, BTN_HEIGHT);
			} else {
				btnBrightness.setRect(0, chkFlipToolbar.bottom() + GAP, WIDTH*2, SLIDER_HEIGHT);
			}
			btnBrightness.checked(ShatteredPixelDungeon.brightness());
			add(btnBrightness);

			OptionSlider btnScaleUp = new OptionSlider(Messages.get(WndSettings.class, "scale"),
					(int) Math.ceil(2 * Game.density) + "X",
					PixelScene.maxDefaultZoom + "X",
					(int) Math.ceil(2 * Game.density),
					PixelScene.maxDefaultZoom) {
				@Override
				protected void onChange() {
					if (getSelectedValue() != ShatteredPixelDungeon.scale()) {
						ShatteredPixelDungeon.scale(getSelectedValue());
						GameLog.wipe();
						try {
							Dungeon.saveAll();
						} catch (Exception e) {
							// Do nothing
						}
						Game.switchScene(TitleScene.class);
					}
				}
			};
			if (!ShatteredPixelDungeon.landscape()) {
				btnScaleUp.setRect(0, btnBrightness.bottom() + GAP, WIDTH, SLIDER_HEIGHT);
			} else {
				btnScaleUp.setRect(9000,9000,0,0);
			}
			btnScaleUp.setSelectedValue(PixelScene.defaultZoom);
			add(btnScaleUp);

		}
	}

	private String orientationText() {
		return ShatteredPixelDungeon.landscape() ? Messages.get(WndSettings.class, "port")
				: Messages.get(WndSettings.class, "land");
	}

	@Override
	public void hide() {
		super.hide();
		if (setScale != PixelScene.defaultZoom) {
			ShatteredPixelDungeon.scale(setScale);
			ShatteredPixelDungeon.switchScene(TitleScene.class);
		}
	}
}
