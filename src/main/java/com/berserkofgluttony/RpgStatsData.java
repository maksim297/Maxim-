
package com.berserkofgluttony;

import net.minecraft.nbt.CompoundTag;

public class RpgStatsData {
    private int strength = 10;
    private int agility = 10;
    private int endurance = 10;
    private int magic = 10;

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getMagic() {
        return magic;
    }

    public void save(CompoundTag tag) {
        tag.putInt("Strength", strength);
        tag.putInt("Agility", agility);
        tag.putInt("Endurance", endurance);
        tag.putInt("Magic", magic);
    }

    public void load(CompoundTag tag) {
        strength = tag.contains("Strength")
                ? tag.getInt("Strength") : 10;
        agility = tag.contains("Agility")
                ? tag.getInt("Agility") : 10;
        endurance = tag.contains("Endurance")
                ? tag.getInt("Endurance") : 10;
        magic = tag.contains("Magic")
                ? tag.getInt("Magic") : 10;
    }
}
