
package com.berserkofgluttony;

/**
 * Система навичок RPG для мода
 * «Ненаситний Берсерк».
 *
 * Зберігає рівні навичок окремо
 * від основних характеристик.
 */
public final class SkillSystem {

    private SkillSystem() {
    }

    public static final int ABSORPTION = 0;
    public static final int GREEDY_STRIKE = 1;
    public static final int BERSERKER_RAGE = 2;

    public static final int SKILL_COUNT = 3;

    public static String getSkillName(int skillId) {
        return switch (skillId) {
            case ABSORPTION -> "Поглинання";
            case GREEDY_STRIKE -> "Удар Жадібності";
            case BERSERKER_RAGE -> "Лють Берсерка";
            default -> "Невідома навичка";
        };
    }

    public static String getSkillDescription(int skillId) {
        return switch (skillId) {
            case ABSORPTION ->
                    "Поглинання сили переможених ворогів";
            case GREEDY_STRIKE ->
                    "Особлива атака мечем Жадібності";
            case BERSERKER_RAGE ->
                    "Тимчасове посилення Берсерка";
            default -> "";
        };
    }

    public static boolean isValidSkill(int skillId) {
        return skillId >= 0 && skillId < SKILL_COUNT;
    }
  }
