import java.util.Random;

public class HW_4 {
    public static int bossHealth = 800;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 300, 500};
    public static int[] heroesDamage = {20, 15, 10, 0, 0};
    public static int medicDamage = 50;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Witcher"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        printStatistics();
        medicHeal();
        witchResurrection();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 2); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal() {
        if (heroesHealth[3] > 0) {
            boolean healed = false;
            for (int i = 0; i < heroesHealth.length - 2; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && !healed) {
                    heroesHealth[i] = heroesHealth[i] + medicDamage;
                    System.out.println("Medic healed " + heroesAttackType[i] + ": " + medicDamage + " points");
                    healed = true;
                }
            }
        }
    }

    public static void witchResurrection() {
        if (heroesHealth[4] > 0) {
            boolean resurrected = false;
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] == 0 && !resurrected) {
                    heroesHealth[i] = heroesHealth[4] + heroesHealth[i];
                    heroesHealth[4] = 0;

                    System.out.println("Witch resurrcted " + heroesAttackType[i]);
                    resurrected = true;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }


    }
}
