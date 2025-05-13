package models.enums;

public enum SkillType {
        FARMING(5),
        FISHING(5),
        FORAGING(10),
        MINING(10)
        ;

        private final Integer upgradeAbility;
        SkillType(Integer upgradeAbility) {
                this.upgradeAbility = upgradeAbility;
        }
}
