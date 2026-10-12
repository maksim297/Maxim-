package com.berserkofgluttony;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class RpgStats {

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

    private int strength = 10;
    private int agility = 10;
    private int endurance = 10;
    private int magic = 10;
    private int statPoints = 0;

    public RpgStats() {
    }

    public RpgStats(int strength, int agility,
                    int endurance, int magic) {
        this(strength, agility, endurance, magic, 0);
    }

    public RpgStats(int strength, int agility,
                    int endurance, int magic, int statPoints) {
        this.strength = Math.max(0, strength);
        this.agility = Math.max(0, agility);
        this.endurance = Math.max(0, endurance);
        this.magic = Math.max(0, magic);
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

    public void setStatPoints(int value) {
        statPoints = Math.max(0, value);
    }
}
