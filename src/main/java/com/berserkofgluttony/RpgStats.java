
package com.berserkofgluttony;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class RpgStats {

    private static final int BASE_STAT = 10;

    public static final Codec<RpgStats> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("Strength")
                            .forGetter(RpgStats::getStrength),
                    Codec.INT.fieldOf("Agility")
                            .forGetter(RpgStats::getAgility),
                    Codec.INT.fieldOf("Endurance")
                            .forGetter(RpgStats::getEndurance),
                    Codec.INT.fieldOf("Magic")
                            .forGetter(RpgStats::getMagic),
                    Codec.INT.optionalFieldOf("StatPoints", 0)
                            .forGetter(RpgStats::getStatPoints)
            ).apply(instance, RpgStats::new));

    private int strength = BASE_STAT;
    private int agility = BASE_STAT;
    private int endurance = BASE_STAT;
    private int magic = BASE_STAT;
    private int statPoints = 0;

    public RpgStats() {
    }

    public RpgStats(int strength, int agility,
                    int endurance, int magic) {
        this(strength, agility, endurance, magic, 0);
    }

    public RpgStats(int strength, int agility,
                    int endurance, int magic, int statPoints) {
        this.strength = Math.max(BASE_STAT, strength);
        this.agility = Math.max(BASE_STAT, agility);
        this.endurance = Math.max(BASE_STAT, endurance);
        this.magic = Math.max(BASE_STAT, magic);
        this.statPoints = Math.max(0, statPoints);
    }

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

    public int getStatPoints() {
        return statPoints;
    }

    public void setStrength(int value) {
        strength = Math.max(BASE_STAT, value);
    }

    public void setAgility(int value) {
        agility = Math.max(BASE_STAT, value);
    }

    public void setEndurance(int value) {
        endurance = Math.max(BASE_STAT, value);
    }

    public void setMagic(int value) {
        magic = Math.max(BASE_STAT, value);
    }

    public void setStatPoints(int value) {
        statPoints = Math.max(0, value);
    }
}
