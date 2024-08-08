package com.github.dachhack.sprout.items.rings;

import com.github.dachhack.sprout.Messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfMight extends Ring {

	{
//		name = "Ring of Might";
		name = Messages.get(this, "name");
	}

	@Override
	protected RingBuff buff() {
		return new Might();
	}

	@Override
	public String desc() {
//		return isKnown() ? "This ring enhances the physical traits of the wearer, "
//				+ "granting them greater physical strength and constitution. "
//				+ "A degraded ring will weaken the wearer."
//				: super.desc();
		return isKnown() ? Messages.get(this, "desc")
				: super.desc();
	}

	public class Might extends RingBuff {
	}
}
