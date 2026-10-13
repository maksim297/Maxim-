
package com.berserkofgluttony;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class RpgStats {

    public static final int BASE_STAT = 10;
    public static final int SKILL_UNLOCK_LEVEL = 30;

    public static final Codec<RpgStats> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.optionalFieldOf("Strength", 10)
                            .forGetter(RpgStats::getStrength),
                    Codec.INT.optionalFieldOf("Endurance", 10)
                            .forGetter(RpgStats::getEndurance),
                    Codec.INT.optionalFieldOf("Perception", 10)
                            .forGetter(RpgStats::getPerception),
                    Codec.INT.optionalFieldOf("Agility", 10)
                            .forGetter(RpgStats::getAgility),
                    Codec.INT.optionalFieldOf("Intelligence", 10)
                            .forGetter(RpgStats::getIntelligence),
                    Codec.INT.optionalFieldOf("StatPoints", 0)
                            .forGetter(RpgStats::getStatPoints)
            ).apply(instance, RpgStats::new));

    private int strength = BASE_STAT;
    private int endurance = BASE_STAT;
    private int perception = BASE_STAT;
    private int agility = BASE_STAT;
    private int intelligence = BASE_STAT;
    private int statPoints = 0;

    public RpgStats() {
    }

    public RpgStats(
            int strength,
            int endurance,
            int perception,
            int agility,
            int intelligence,
            int statPoints
    ) {
        this.strength = Math.max(BASE_STAT, strength);
        this.endurance = Math.max(BASE_STAT, endurance);
        this.perception = Math.max(BASE_STAT, perception);
        this.agility = Math.max(BASE_STAT, agility);
        this.intelligence = Math.max(BASE_STAT, intelligence);
        this.statPoints = Math.max(0, statPoints);
    }

    public int getStrength() {
        return strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getPerception() {
        return perception;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void setStrength(int value) {
        strength = Math.max(BASE_STAT, value);
    }

    public void setEndurance(int value) {
        endurance = Math.max(BASE_STAT, value);
    }

    public void setPerception(int value) {
        perception = Math.max(BASE_STAT, value);
    }

    public void setAgility(int value) {
        agility = Math.max(BASE_STAT, value);
    }

    public void setIntelligence(int value) {
        intelligence = Math.max(BASE_STAT, value);
    }

    public void setStatPoints(int value) {
        statPoints = Math.max(0, value);
    }

    public boolean hasUnlockedSkill(int statValue) {
        return statValue >= SKILL_UNLOCK_LEVEL;
    }

    public boolean isSkillUnlocked(String skillName) {
        return switch (skillName) {
            case "breach" -> hasUnlockedSkill(strength);
            case "second_breath" -> hasUnlockedSkill(endurance);
            case "hunters_mark" -> hasUnlockedSkill(perception);
            case "double_strike" -> hasUnlockedSkill(agility);
            case "mana_shield" -> hasUnlockedSkill(intelligence);
            default -> false;
        };
    }
}
