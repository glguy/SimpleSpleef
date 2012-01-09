/**
 * 
 */
package de.beimax.simplespleef.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author mkalus
 *
 */
public abstract class MaterialHelper {
	/**
	 * Returns material for entry (simple material like DIAMOND_SPADE or 277 - no damage numbers here!)
	 * @param material
	 * @return
	 */
	public static Material getMaterialFromString(String material) {
		Material m;
		// try to convert material from number
		try {
			m = Material.getMaterial(Integer.valueOf(material));
		} catch (Exception e) {
			m = Material.getMaterial(material); // try to convert it from name instead
		}

		return m;
	}
	
	/**
	 * 
	 * @param line
	 * @return
	 */
	public static ItemStack getItemStackFromString(String line) {
		// split string to stack
		String[] parts = line.split(":");
		int number;
		String item;
		short dmg = -1;
		if (parts.length > 3) return null; //syntax error
		else if (parts.length == 3) { // with damage number!
			try {
				number = Integer.valueOf(parts[0]);
			} catch (Exception e) {
				return null; //syntax error
			}
			item = parts[1];
			try { // get damage number
				dmg = Short.valueOf(parts[2]);
			} catch (Exception e) {
				return null; //syntax error
			}
		} else if (parts.length == 2) {
			try {
				number = Integer.valueOf(parts[0]);
				item = parts[1];
			} catch (Exception e) {
				// ok, then this is the material
				number = 1;
				item = parts[0];
				// second should be damage number
				try { // get damage number
					dmg = Short.valueOf(parts[1]);
				} catch (Exception ex) {
					return null; //syntax error
				}
			}
		} else {
			number = 1;
			item = line;
		}
		// get material
		Material material = getMaterialFromString(item);
		if (material == null) return null; //syntax error
		// create stack
		ItemStack stack;
		if (dmg > 0) stack = new ItemStack(material, number, dmg);
		//else stack = new ItemStack(material, number, (short) -1); //-1 to define a generic material (like all wool, not wool:0 = white wool)
		else stack = new ItemStack(material, number); //-1 to define a generic material (like all wool, not wool:0 = white wool)

		return stack;
	}
}