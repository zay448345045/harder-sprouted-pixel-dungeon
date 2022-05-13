package com.github.dachhack.sprout.scenes;

import android.opengl.GLES20;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Badges;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.Rankings;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.effects.BannerSprites;
import com.github.dachhack.sprout.effects.Fireball;
import com.github.dachhack.sprout.ui.RedButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

import javax.microedition.khronos.opengles.GL10;

//TODO: update this class with relevant info as new versions come out.
public class WelcomeScene extends PixelScene {

	private static final String TTL_Welcome = "Welcome!";

	private static final String TTL_Update = "v0.4.1: SPD 0.2.4c: PD 1.7.5 and Some Extras!";

	private static final String TTL_Future = "Wait What?";

	private static final String TXT_Welcome = "Sprouted Pixel Dungeon\n\n"
			+"This is a rework/expansion of Watabou's Pixel Dungeon.\n\n"
			+ "Included are all additions from Shattered Pixel Dungeon (0.2.4c) by 00-Evan\n\n"
			+ "This version was adapted for those who love to grind, level up, and collect loot\n\n"
			+ "Happy Dungeoneering!\n\n"
			+"\n\n"

			+"Sprouted Pixel Dungeon differences:\n\n"
			+"Much larger levels creating a different game play and strategy experience.\n\n"
			+"Mobs drop monster meat to facilitate longer and more in-depth exploration of the larger levels.\n\n"
			+"Dew system has been revised to create a currency for upgrade opportunities.\n\n"
			+"Dew vial has been reworked as a new pivotal resource for exploring the dungeon.\n\n"
			+"Wraiths and grave hunting is now a major part of the game progression.\n\n"
			+"Boss fights have been completely reworked to be more intense and challenging requiring completely new tactics.\n\n"
			+"Mobs now adjust strength as you go deeper in the dungeon to stay balanced with upgrades.\n\n"
			+"New levels are accessible at each stage with Four additional levels available at the end of the game.\n\n"
			+"New levels include unique enemies, items and rewards.\n\n"
			+"\n\n";



	private static final String TXT_Update =


			"Version 0.4.1\n\n"
			+"Fix pet follow bugs\n\n"
			+"Fix sokoban teleport bugs\n\n"
			+"Added pet whistle\n\n"
			+"Added autopotion charm\n\n"
			+"Fix Dolyahaven level save/delete bugs\n\n"
			+"\n\n"
			+"Version 0.4.0 \n\n"
		    +"Otiluke's Journal available in first shop will take you to Sokoban levels using Journal Pages dropped by bosses. \n\n"
		    +"You will need to solve puzzles on the Sokoban levels to gather enough gold to purchase the books from the imp shop. \n\n"
		    +"New Post-Shadow Yog Play using Otiluke's Journal includes: \n\n"
		    +" * Overworld Town \n\n"
		    +" * A new mine stage overrun with demons \n\n"
		    +" * New tier-6 weapons with special powers \n\n"
		    +" * Two new boss fights with powerful wizards and their minions \n\n"
		    +"Also: \n\n"
		    +" * Spectacles \n\n"
		    +" * AutoPotion Charm \n\n"
		    +" * Fix Ranged Damage (Huntress Fix)\n\n"
		    +" * Haste Can Now Apply to Pets \n\n"
		    +" * Mossy Skeleton Unlimited Hits Fix \n\n"
		    +" * Removing Armor While Hasted Fix \n\n"
		    +" * 0 Dark Gold Drop Fix \n\n"
		    +" * Other Bug fixes \n\n"
		    +" * A new Easter Egg \n\n"

			+"\n\n"
			+"Version 0.3.5a \n\n"
			+"Nerf Huntress missle bonus.\n\n"
			+"Buff pets.\n\n"
			+"Added Golden Skeleton Key.\n\n"
			+"Added Ring of Frost Artifact.\n\n"
			+"Various bug fixes.\n\n"
			+"\n\n"
			+"Version 0.3.5 adds the Dew Charge system and Pets\n\n"
			+"Please note that saves to previous versions might not work in 0.3.5.\n\n"
			+"\n\n"
			+"Egg - How you treat it determines what hatches.\n\n"
			+"Keep it warm in your pack or absorb energy by dropping it on traps.\n\n"
			+"Don't break it open until you hear something alive inside!\n\n"
			+"\n\n"
			+"Dew Charge - For those who choose 'Dew Draw' a non-grinding option.\n\n"
			+"At the start of each new level, you are dew charged for 50 moves.\n\n"
			+"When dew charged, killing mobs draws out dew from the dungeon.\n\n"
			+"Each dungeon floor has a move goal.\n\n"
			+"If you kill all mobs on the floor (excepting statues, piranha, mimics) under the goal you add moves to your dew charge for the next level.\n\n"
			+"Descending to the next level evaporates the dew on your current level.\n\n"
			+"So you have to use all the dew before you start a new level.\n\n"
			+"Dew bless lets you pick the item you want to apply the upgrade to.\n\n"
			+"You can still grind dew on the key levels if you like.\n\n"
			+"\n\n"
			+"Version 0.3.0b fixes a bug with the weakness debuff and adds the Ring of Disintegration to Artifacts\n\n"
			+"Fixes bugs with seed drops. A few other bugs squashed.\n\n"
			+"\n\n"
			+"Version 0.3.0a fixes a couple in-game messages and adds the Dewcatcher Plant\n\n"
			+"\n\n"
			+"Version 0.3.0 adds in new mini-boss fights where you can earn adamant ore.\n\n"
			+"Items max out at 15 upgrades.\n\n"
			+"You can upgrade items past 15 if they have been reinforced with adamantite ore.\n\n"
			+"There are five pieces of adamantite available in the game obtainable by completing new mini-boss fights.\n\n"
			+"To reinforce your items, you need to take them to the blacksmiths.\n\n"
			+"He takes all your black gold when reinforcing so any black gold over 50 is wasted.\n\n"
			+"Keys to the mini-boss battles drop on ancient key levels sometime after killing 50 mobs.\n\n"
			+"\n\n"
			+"Enhancements:\n\n"
			+"Autotarget\n\n"
			+"Surprise attack indicator\n\n"
			+"Dropped items visible on level in fog of war\n\n"
			+"Keyring!\n\n"
			+"\n\n"
			+"Rebalance:\n\n"
			+"New mobs in sewers and prison rebalanced to make these stages more survivable.\n\n"
			+"Gray Rat\n\n"
			+"Brown Bat\n\n"
			+"Fossil Skeleton\n\n"
			+"\n\n"
			+"Special Halloween Item!\n\n"
			+"The Goo will drop a Bloodlust Enchanted Chainsaw if he doesn't drop a Mr Destucto\n\n"
			+"Also added a giant Venus Flytrap plant that converts your spare upgraded items to upgrade goo.\n\n"
			+"Happy Halloween!\n\n"
			+"\n\n"
			+"This new update features many new sprites from Pavel Provotorov. He's done a great job making Sprouted come alive. Many thanks!\n\n"

            +"\n\n"
            +"\n\n"
			+"Version 0.2.5 adds back optional watering to the dew vial and re-works dew levels.\n\n"
			+"Added Tinkerer mini-quest to level 2 where you will choose how to grind dew drops.\n\n"
			+"Added Tinkerer mini-quest to level 12.\n\n"
			+"Added Tinkerer mini-quest to Oni level.\n\n"
			+"Reworked Shadow Yog battle to be extremely challenging.\n\n"
			+"Added protectors to the dew levels.\n\n"
			+"Mushrooms on dew levels\n\n"
			+"Orb of Zot is active now.\n\n"
			+"Added Starflower seed and plant (Thanks Shattered!).\n\n"
			+"Added Phase Pitcher seed and plant.\n\n"
			+"Added a sign to the pits to point out the scroll of teleport.\n\n"
			+"Buffed mobs throughout to balance upgrades.\n\n"
			+"Ranking reflect Shadow Yog battle.\n\n"
			+"Featuring new sprites for Books, Mushrooms, Berries by Pavel Provotorov!"
			+"\n\n"

			+"Version 0.2.0a is a bug fix from 0.2.0.\n\n"
			+"Fixed Overfill mechanic\n\n"
			+"Buffed Key level mobs and removed meat drop form archers\n\n"
			+"Fixed Gold Thief gold drop\n\n"
			+"A couple other fixes to hidden items\n\n"
			+"\n\n"

			+"Please note that saves from the previous release (0.1.5) will not open properly on this version.\n\n"
			+"\n\n"
			+"Major Revisions:\n\n"
			+"Water command has been removed from the dew vial.\n\n"
			+"Instead of watering you can grind dew on one of four new levels.\n\n"
			+"Each stage has a level accessable by finding an ancient key.\n\n"
			+"These new levels are populated by new mobs and are rich in dew.\n\n"
			+"Each level has an opportunity to earn special rewards by dispatching 100+ mobs before you leave.\n\n"
			+"\n\n"
			+"Each level of the demon halls is sealed and you will need to find a way to unseal to go down.\n\n"
			+"\n\n"
			+"Three new levels are available in the imp shop for post-yog play.\n\n"
			+"Completing these three levels allows you to unlock a final Yog boss.\n\n"
			+"A special trophy item is dropped if you vanquish this final Yog.\n\n"
			+"\n\n"
			+"Smaller Tweaks:\n\n"
			+"Lloyd's Beacon replaced by Mr Desructo.\n\n"
			+"Buffed wand of magic missle including buffing the disenchant function to carry over more upgrades.\n\n"
			+"Nerfed Berry Regen.\n\n"
			+"Buffed wand of magic missle including buffing the disenchant function to carry over more upgrades.\n\n"
			+"Tweaked Yog fight to make it less annoying.\n\n"
			+"Seeds no longer drop as random loot in the dungeon. (Still get from high grass.)\n\n"
			+"\n\n"
			+"\n\n"

			+"Sprouted Pixel Dungeon differences:\n\n"
			+"Much larger levels creating a different game play and strategy experience.\n\n"
			+"Mobs drop monster meat to facilitate longer and more in-depth exploration of the larger levels.\n\n"
			+"Dew system has been revised to create a currency for upgrade opportunities.\n\n"
			+"Dew vial has been reworked as a new pivotal resource for exploring the dungeon.\n\n"
			+"Wraiths and grave hunting is now a major part of the game progression.\n\n"
			+"Boss fights have been completely reworked to be more intense and challenging requiring completely new tactics.\n\n"
			+"Mobs now adjust strength as you go deeper in the dungeon to stay balanced with upgrades.\n\n"
			+"New levels are accessible at each stage with Four additional levels available at the end of the game.\n\n"
			+"New levels include unique enemies, items and rewards.\n\n"
			+"\n\n"


			;

	private static final String TXT_Future = "It seems that your current saves are from a future version of Sprouted Pixel Dungeon!\n\n"
			+ "Either you're messing around with older versions of the app, or something has gone buggy.\n\n"
			+ "Regardless, tread with caution! Your saves may contain things which don't exist in this version, "
			+ "this could cause some very weird errors to occur.";

	private static final String LNK = "https://play.google.com/store/apps/details?id=com.github.dachhack.sprout";
	private static int LATEST_UPDATE = 90;
	@Override
	public void create() {
		super.create();

		final int previousVersion = ShatteredPixelDungeon.version();

		if (ShatteredPixelDungeon.versionCode == previousVersion) {
			ShatteredPixelDungeon.switchNoFade(TitleScene.class);
			return;
		}

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		title.brightness(0.6f);
		add( title );

		float height = title.height +
				(ShatteredPixelDungeon.landscape() ? 48 : 96);

		title.x = (w - title.width()) / 2;
		title.y = (h - height) / 2;

		placeTorch(title.x + 18, title.y + 20);
		placeTorch(title.x + title.width - 18, title.y + 20);

		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
				super.draw();
				GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add( signs );

		DarkRedButton okay = new DarkRedButton(Messages.get(this, "continue")){
			@Override
			protected void onClick() {
				super.onClick();
				updateVersion(previousVersion);
				ShatteredPixelDungeon.switchScene(TitleScene.class);
			}
		};

		if (previousVersion != 0){
			DarkRedButton changes = new DarkRedButton(Messages.get(this, "changelist")){
				@Override
				protected void onClick() {
					super.onClick();
					updateVersion(previousVersion);
					ShatteredPixelDungeon.switchScene(TitleScene.class);
				}
			};
			okay.setRect(title.x, h-20, (title.width()/2)-2, 16);
			okay.textColor(0xBBBB33);
			add(okay);

			changes.setRect(okay.right()+2, h-20, (title.width()/2)-2, 16);
			changes.textColor(0xBBBB33);
			add(changes);
		} else {
			okay.setRect(title.x, h-20, title.width(), 16);
			okay.textColor(0xBBBB33);
			add(okay);
		}

		RenderedTextMultiline text = PixelScene.renderMultiline(6);
		String message;
		if (previousVersion == 0) {
			message = Messages.get(this, "welcome_msg");
		} else if (previousVersion <= ShatteredPixelDungeon.versionCode) {
			if (previousVersion < LATEST_UPDATE){
				message = Messages.get(this, "update_intro");
				message += "\n\n" + Messages.get(this, "update_msg");
			} else {
				//TODO: change the messages here in accordance with the type of patch.
				message = Messages.get(this, "patch_intro");
				message += "\n\n" + Messages.get(this, "patch_bugfixes");
				message += "\n" + Messages.get(this, "patch_translations");
				message += "\n\n" + Messages.get(this, "patch_msg");

			}
		} else {
			message = Messages.get(this, "what_msg");
		}
		text.text(message, w-20);
		float textSpace = h - title.y - (title.height() - 10) - okay.height() - 2;
		text.setPos(10, title.y+(title.height() - 10) + ((textSpace - text.height()) / 2));
		add(text);

	}

	private void updateVersion(int previousVersion){
		if (previousVersion <= 32){
			//removes all bags bought badge from pre-0.2.4 saves.
			Badges.disown(Badges.Badge.ALL_BAGS_BOUGHT);
			Badges.saveGlobal();

			//imports new ranking data for pre-0.2.3 saves.
			if (previousVersion <= 29){
				Rankings.INSTANCE.load();
				Rankings.INSTANCE.save();
			}
		}
		ShatteredPixelDungeon.version(ShatteredPixelDungeon.versionCode);
	}

	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}

	private class DarkRedButton extends RedButton{
		{
			bg.brightness(0.4f);
		}

		DarkRedButton(String text){
			super(text);
		}

		@Override
		protected void onTouchDown() {
			bg.brightness(0.5f);
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
			super.onTouchUp();
			bg.brightness(0.4f);
		}
	}
}