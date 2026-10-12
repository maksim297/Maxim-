
package com.berserkofgluttony;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public class RpgStats {
    private static final String TAG = "BerserkRpgStats";

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

    public void setStrength(int value) {
        strength = Math.max(0, value);
    }

    public void setAgility(int value) {
        agility = Math.max(0, value);
    }

    public void setEndurance(int value) {
        endurance = Math.max(0, value);
    }

    public void setMagic(int value) {
        magic = Math.max(0, value);
    }

    public void save(CompoundTag tag) {
        tag.putInt("Strength", strength);
        tag.putInt("Agility", agility);
        tag.putInt("Endurance", endurance);
        tag.putInt("Magic", magic);
    }

    public void load(CompoundTag tag) {
        strength = tag.getInt("Strength");
        agility = tag.getInt("Agility");
        endurance = tag.getInt("Endurance");
        magic = tag.getInt("Magic");
    }
}
