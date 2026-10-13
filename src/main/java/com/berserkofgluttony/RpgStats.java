
package com.berserkofgluttony;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class RpgStats {

    public static final int BASE_STAT = 10;
    public static final int SKILL_UNLOCK_LEVEL = 30;

    private static final int[] WEIGHTS = {20, 20, 23, 20, 17};

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
                            .forGetter(RpgStats::getStatPoints),
                    Codec.INT.optionalFieldOf("AutoPointsAllocated", 0)
                            .forGetter(RpgStats::getAutoPointsAllocated)
            ).apply(instance, RpgStats::new));

    private int strength = BASE_STAT;
    private int endurance = BASE_STAT;
    private int perception = BASE_STAT;
    private int agility = BASE_STAT;
    private int intelligence = BASE_STAT;
    private int statPoints = 0;
    private int autoPointsAllocated = 0;

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
        this(strength, endurance, perception, agility, intelligence,
                statPoints, 0);
    }

    public RpgStats(
            int strength,
            int endurance,
            int perception,
            int agility,
            int intelligence,
            int statPoints,
            int autoPointsAllocated
    ) {
        this.strength = Math.max(BASE_STAT, strength);
        this.endurance = Math.max(BASE_STAT, endurance);
        this.perception = Math.max(BASE_STAT, perception);
        this.agility = Math.max(BASE_STAT, agility);
        this.intelligence = Math.max(BASE_STAT, intelligence);
        this.statPoints = Math.max(0, statPoints);
        this.autoPointsAllocated = Math.max(0, autoPointsAllocated);
    }

    public int getStrength() { return strength; }
    public int getEndurance() { return endurance; }
    public int getPerception() { return perception; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getStatPoints() { return statPoints; }
    public int getAutoPointsAllocated() { return autoPointsAllocated; }

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

    public void setAutoPointsAllocated(int value) {
        autoPointsAllocated = Math.max(0, value);
    }

    public void distributeAutomaticPoints(int points) {
        if (points <= 0) {
            return;
        }

        for (int i = 0; i < points; i++) {
            int nextTotal = autoPointsAllocated + 1;
            int selected = 0;
            int largestDeficit = Integer.MIN_VALUE;

            int[] values = {
                    strength - BASE_STAT,
                    endurance - BASE_STAT,
                    perception - BASE_STAT,
                    agility - BASE_STAT,
                    intelligence - BASE_STAT
            };

            for (int stat = 0; stat < WEIGHTS.length; stat++) {
                int target = nextTotal * WEIGHTS[stat];
                int currentScaled = values[stat] * 100;
                int deficit = target - currentScaled;

                if (deficit > largestDeficit) {
                    largestDeficit = deficit;
                    selected = stat;
                }
            }

            switch (selected) {
                case 0 -> strength++;
                case 1 -> endurance++;
                case 2 -> perception++;
                case 3 -> agility++;
                case 4 -> intelligence++;
                default -> {
                }
            }

            autoPointsAllocated++;
        }
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
