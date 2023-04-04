package com.github.dachhack.sprout.items.artifacts;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.Egg;
import com.github.dachhack.sprout.items.Generator;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.potions.Potion;
import com.github.dachhack.sprout.items.potions.PotionOfExperience;
import com.github.dachhack.sprout.items.potions.PotionOfOverHealing;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by debenhame on 24/11/2014.
 */
public class AlchemistsToolkit extends Artifact {

	{
//		name = "Alchemists Toolkit";
		name = Messages.get(this, "name");

		image = ItemSpriteSheet.ARTIFACT_TOOLKIT;

		level = 0;
		levelCap = 10;
	}

//	public static final String AC_BREW = "BREW";
public static final String AC_BREW = Messages.get(AlchemistsToolkit.class, "ac_brew");

	// arrays used in containing potion collections for mix logic.
	public final ArrayList<Class> combination = new ArrayList<Class>();
	public ArrayList<Class> curGuess = new ArrayList<Class>();
	public ArrayList<Class> bstGuess = new ArrayList<Class>();

	public int numWrongPlace = 0;
	public int numRight = 0;

	private int seedsToPotion = 0;

//	protected String inventoryTitle = "Select a potion";
protected String inventoryTitle = Messages.get(AlchemistsToolkit.class, "invtitle");
	protected WndBag.Mode mode = WndBag.Mode.POTION;

	public AlchemistsToolkit() {
		super();

		Generator.Category cat = Generator.Category.POTION;
		for (int i = 1; i <= 3; i++) {
			Class potion;
			do {
				potion = cat.classes[Random.chances(cat.probs)];
				// forcing the player to use experience potions would be
				// completely unfair.
			}
			while (combination.contains(potion) || potion == PotionOfExperience.class || potion == Egg.class || potion == PotionOfOverHealing.class);
			combination.add(potion);
		}
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_BREW);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREW)) {
			GameScene.selectItem(itemSelector, mode, inventoryTitle);
		} else {
			super.execute(hero, action);
		}
	}

	public void guessBrew() {
		if (curGuess.size() != 3)
			return;

		int numWrongPlace = 0;
		int numRight = 0;

		for (Class potion : curGuess) {
			if (combination.contains(potion)) {
				if (curGuess.indexOf(potion) == combination.indexOf(potion)) {
					numRight++;
				} else {
					numWrongPlace++;
				}
			}
		}

		int score = (numRight * 3) + numWrongPlace;

		if (score == 9)
			score++;

		if (score == 0) {

			GLog.i(Messages.get(this, "0"));

		} else if (score > level) {

			level = score;
			seedsToPotion = 0;
			bstGuess = curGuess;
			this.numRight = numRight;
			this.numWrongPlace = numWrongPlace;

			if (level == 10) {
				bstGuess = new ArrayList<>();
				GLog.p(Messages.get(this, "10"));
			} else {
				GLog.w(Messages.get(this, "finish")
						+ brewDesc(numWrongPlace, numRight)
						+ Messages.get(this, "bestbrew"));
			}

		} else {

//			GLog.w("you finish mixing potions, "
//					+ brewDesc(numWrongPlace, numRight)
//					+ ". This brew isn't as good as the current one, you throw it away.");
			GLog.w(Messages.get(this, "finish")
					+ brewDesc(numWrongPlace, numRight)
					+ Messages.get(this, "throw"));
		}
		curGuess = new ArrayList<Class>();

	}

	private String brewDesc(int numWrongPlace, int numRight) {
		String result = "";
		if (numWrongPlace > 0) {
//			result += numWrongPlace + " reacted well, but in the wrong order";
			result += numWrongPlace + Messages.get(this, "bdorder");
			if (numRight > 0)
				result += Messages.get(this, "and");
		}
		if (numRight > 0) {
//			result += numRight + " reacted perfectly";
			result += numRight + Messages.get(this, "perfect");
		}
		return result;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new alchemy();
	}

	@Override
	public String desc() {
		String result = Messages.get(this, "desc1");

		if (isEquipped(Dungeon.hero))
			if (cursed)
//				result += "The cursed toolkit has bound itself to your side, and refuses to let you use alchemy.\n\n";
				result += Messages.get(this, "desc2");
			else
//				result += "The toolkit rests on your hip, the various tools inside make a light jingling sound as you move.\n\n";
				result += Messages.get(this, "desc3");

		if (level == 0) {
//			result += "The toolkit seems to be missing a key tool, a catalyst mixture. You'll have to make your own "
//					+ "out of three common potions to get the most out of the toolkit.";
			result += Messages.get(this, "desc4");
		} else if (level == 10) {
//			result += "The mixture you have created seems perfect, and the toolkit is working at maximum efficiency.";
			result += Messages.get(this, "desc5");
		} else if (!bstGuess.isEmpty()) {
//			result += "Your current best mixture is made from: "
//					+ bstGuess.get(0) + ", " + bstGuess.get(1) + ", "
//					+ bstGuess.get(2) + ", in that order.\n\n";
//			result += "Of the potions in that mix, "
//					+ brewDesc(numWrongPlace, numRight) + ".";
			result += Messages.get(this, "desc6")
					+ Messages.get(bstGuess.get(0), "name") + ", " + Messages.get(bstGuess.get(1), "name") + ", "
					+ Messages.get(bstGuess.get(2), "name") + Messages.get(this, "desc7");
			result += Messages.get(this, "desc8")
					+ brewDesc(numWrongPlace, numRight) + Messages.get(this, "desc9");

			// would only trigger if an upgraded toolkit was gained through
			// transmutation or bones.z
		} else {
//			result += "The toolkit seems to have a catalyst mixture already in it, but it isn't ideal. Unfortunately "
//					+ "you have no idea what's in the mixture.";
			result += Messages.get(this, "desc10");
		}
		return result;
	}

	private static final String COMBINATION = "combination";
	private static final String CURGUESS = "curguess";
	private static final String BSTGUESS = "bstguess";

	private static final String NUMWRONGPLACE = "numwrongplace";
	private static final String NUMRIGHT = "numright";

	private static final String SEEDSTOPOTION = "seedstopotion";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(NUMWRONGPLACE, numWrongPlace);
		bundle.put(NUMRIGHT, numRight);

		bundle.put(SEEDSTOPOTION, seedsToPotion);

		//todo String is Crashed,Used Class!
		bundle.put(COMBINATION,
				combination.toArray(new Class[combination.size()]));
		bundle.put(CURGUESS, curGuess.toArray(new Class[curGuess.size()]));
		bundle.put(BSTGUESS, bstGuess.toArray(new Class[bstGuess.size()]));
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		numWrongPlace = bundle.getInt(NUMWRONGPLACE);
		numRight = bundle.getInt(NUMRIGHT);

		seedsToPotion = bundle.getInt(SEEDSTOPOTION);

		combination.clear();
		Collections.addAll(combination, bundle.getClassArray(COMBINATION));
		Collections.addAll(curGuess, bundle.getClassArray(CURGUESS));
		Collections.addAll(bstGuess, bundle.getClassArray(BSTGUESS));
	}

	public class alchemy extends ArtifactBuff {

		public boolean tryCook(int count) {

			// this logic is handled inside the class with a variable so that it
			// may be stored.
			// to prevent manipulation where a player could keep throwing in 1-2
			// seeds until they get lucky.
			if (seedsToPotion == 0) {
				if (Random.Int(20) < 10 + level) {
					if (Random.Int(20) < level) {
						seedsToPotion = 1;
					} else
						seedsToPotion = 2;
				} else
					seedsToPotion = 3;
			}

			if (count >= seedsToPotion) {
				seedsToPotion = 0;
				return true;
			} else
				return false;

		}

	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Potion && item.isIdentified()) {
				if (!curGuess.contains(convertName(item.getClass()
						.getSimpleName()))) {

					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(2f);
					Sample.INSTANCE.play(Assets.SND_DRINK);

					item.detach(hero.belongings.backpack);

					curGuess.add(item.getClass());
					if (curGuess.size() == 3) {
						guessBrew();
					} else {
//						GLog.i("You mix the " + item.name()
//								+ " into your current brew.");
						GLog.i(Messages.get(AlchemistsToolkit.class, "mix1") + item.name()
								+ Messages.get(AlchemistsToolkit.class, "mix2"));
					}
				} else {
//					GLog.w("Your current brew already contains that potion.");
					GLog.w(Messages.get(AlchemistsToolkit.class, "mix3"));
				}
			} else if (item != null) {
//				GLog.w("You need to select an identified potion.");
				GLog.w(Messages.get(AlchemistsToolkit.class, "mix4"));
			}
		}
	};

}
