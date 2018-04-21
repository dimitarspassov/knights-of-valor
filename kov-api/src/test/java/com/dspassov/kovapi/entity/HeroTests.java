package com.dspassov.kovapi.entity;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.entities.Inventory;
import com.dspassov.kovapi.exceptions.IllegalParamException;

import com.dspassov.kovapi.web.ResponseMessageConstants;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;


public class HeroTests {

    private Hero hero;

    @Before
    public void init() {
        //arrange
        this.hero = new Hero();
        this.hero.setInventory(new Inventory());
    }

    @Test
    public void testAddGold_positiveInteger_expectGoldToIncreaseAccordingly() {

        //arrange
        final long GOLD_TO_ADD = 50;

        //act
        this.hero.addGold(GOLD_TO_ADD);
        long increasedGold = this.hero.getGold();

        assertEquals(
                "Gold not increased or increased incorrectly" + GOLD_TO_ADD,
                GOLD_TO_ADD,
                increasedGold
        );
    }

    @Test
    public void testAddGold_negativeInteger_shouldThrowException() {

        //arrange
        final long GOLD_TO_ADD = -50;

        //act
        try {
            this.hero.addGold(GOLD_TO_ADD);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {
            //assert
            assertEquals(
                    ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT,
                    ipe.getMessage());
        }
    }

    @Test
    public void testSubtractGold_positiveInteger_expectGoldToDecreaseAccordingly() {

        //arrange
        final long GOLD_TO_ADD = 50;
        final long GOLD_TO_SUBTRACT = 30;

        //act
        this.hero.addGold(GOLD_TO_ADD);
        this.hero.subtractGold(GOLD_TO_SUBTRACT);

        long expected = GOLD_TO_ADD - GOLD_TO_SUBTRACT;
        long actual = this.hero.getGold();

        //assert
        assertEquals(
                "Gold not subtracted or subtracted incorrectly",
                expected,
                actual
        );
    }

    @Test
    public void testSubtractGold_negativeInteger_shouldThrowException() {

        //arrange
        final long GOLD_TO_SUBTRACT = -10;

        //act
        try {
            this.hero.subtractGold(GOLD_TO_SUBTRACT);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {
            //assert
            assertEquals(
                    ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT,
                    ipe.getMessage());
        }
    }

    @Test
    public void testSubtractGold_subtractMoreThanTheCurrentGold_expectGoldToBecomeZero() {

        //arrange
        final long GOLD_TO_ADD = 20;
        final long GOLD_TO_SUBTRACT = 30;

        //act
        this.hero.addGold(GOLD_TO_ADD);
        this.hero.subtractGold(GOLD_TO_SUBTRACT);


        long actual = this.hero.getGold();

        //assert
        assertEquals(
                "Gold not set to 0, after the subtraction required more than the available gold",
                0,
                actual
        );
    }

    @Test
    public void testAddExperience_positiveInteger_expectExperienceToIncreaseAccordingly() {

        //arrange
        final long XP_TO_ADD = 250;

        //act
        this.hero.addExperience(XP_TO_ADD);
        long actual = this.hero.getExperience();

        //assert
        assertEquals(
                "Experience not increased or increased incorrectly",
                XP_TO_ADD,
                actual
        );
    }

    @Test
    public void testAddExperience_negativeInteger_shouldThrowException() {

        //arrange
        final long XP_TO_ADD = -10;

        //act
        try {
            this.hero.addExperience(XP_TO_ADD);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {
            //assert
            assertEquals(
                    ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT,
                    ipe.getMessage());
        }
    }

    @Test
    public void testAddExperience_bigNumber_expectHeroToLevelUp() {

        //arrange
        int heroStartingLevel = this.hero.getLevel();

        //act
        this.levelUp();
        int expected = heroStartingLevel + 1;
        int actual = this.hero.getLevel();

        //assert
        assertEquals(
                "Hero has not leveled up",
                expected,
                actual
        );
    }

    @Test
    public void testAddExperience_twiceBigNumber_expectHeroToLevelUpTwice() {

        //arrange
        int heroStartingLevel = this.hero.getLevel();

        //act

        // Make sure it is the same in the Hero class
        final int XP_MULTIPLIER = 3150 * 2;
        final long XP_TO_ADD = XP_MULTIPLIER + 1;
        this.hero.addExperience(XP_TO_ADD);

        int expected = heroStartingLevel + 2;
        int actual = this.hero.getLevel();

        //assert
        assertEquals(
                "Hero has not leveled up twice",
                expected,
                actual
        );
    }

    @Test
    public void testLevelUp_toEvenLevel_expectStatsToIncreaseAccordingly() {

        //arrange
        int expectedLevel = this.hero.getLevel() + 1;
        int expectedHealth = (expectedLevel * 2) + this.hero.getHealth();
        int expectedStrength = this.hero.getStrength() + expectedLevel;
        int expectedDefense = this.hero.getDefense() + 1;
        int expectedStamina = this.hero.getStamina() + 1;

        //act
        this.levelUp();

        int increasedLevel = this.hero.getLevel();
        int increasedHealth = this.hero.getHealth();
        int increasedStrength = this.hero.getStrength();
        int increasedDefense = this.hero.getDefense();
        int increasedStamina = this.hero.getStamina();

        //assert
        assertEquals("Hero has not leveled up",
                expectedLevel,
                increasedLevel
        );

        assertEquals("Health not increased correctly",
                expectedHealth,
                increasedHealth
        );

        assertEquals("Strength not increased correctly",
                expectedStrength,
                increasedStrength
        );

        assertEquals("Defense not increased correctly",
                expectedDefense,
                increasedDefense
        );

        assertEquals("Stamina not increased correctly",
                expectedStamina,
                increasedStamina
        );
    }

    @Test
    public void testLevelUp_toOddLevel_expectStatsToIncreaseAccordingly() {

        //arrange

        //after first level up
        int expectedLevel = this.hero.getLevel() + 1;
        int expectedHealth = (expectedLevel * 2) + this.hero.getHealth();
        int expectedStrength = this.hero.getStrength() + expectedLevel;
        int expectedDefense = this.hero.getDefense() + 1;
        int expectedStamina = this.hero.getStamina() + 1;

        //after second level up
        expectedLevel = expectedLevel + 1;
        expectedHealth = expectedLevel + expectedHealth;
        expectedStrength = expectedStrength + 1;
        expectedDefense = expectedDefense + expectedLevel;
        expectedStamina = expectedStamina + 5;

        //act

        this.levelUp();
        this.levelUp();

        int increasedLevel = this.hero.getLevel();
        int increasedHealth = this.hero.getHealth();
        int increasedStrength = this.hero.getStrength();
        int increasedDefense = this.hero.getDefense();
        int increasedStamina = this.hero.getStamina();

        //assert
        assertEquals("Hero has not leveled up",
                expectedLevel,
                increasedLevel
        );

        assertEquals("Health not increased correctly",
                expectedHealth,
                increasedHealth
        );

        assertEquals("Strength not increased correctly",
                expectedStrength,
                increasedStrength
        );

        assertEquals("Defense not increased correctly",
                expectedDefense,
                increasedDefense
        );

        assertEquals("Stamina not increased correctly",
                expectedStamina,
                increasedStamina
        );
    }


    @Test
    public void testExperiencePercentToNextLevel_initialState_expectPercentageToBeZero() {

        //act
        int expected = 0;
        int actual = this.hero.experiencePercentToNextLevel();

        //assert
        assertEquals("Percentage returned is not correct",
                expected,
                actual
        );
    }

    @Test
    public void testExperiencePercentToNextLevel_middleOfRequiredToLevelUp_expectPercentageToBeFifty() {

        //arrange
        final int XP_MULTIPLIER = 3150;

        //act
        int expected = 50;
        this.hero.addExperience(XP_MULTIPLIER / 2L);
        int actual = this.hero.experiencePercentToNextLevel();

        //assert
        assertEquals("Percentage returned is not correct",
                expected,
                actual
        );
    }

    private void levelUp() {
        // Make sure it is the same in the Hero class
        final int XP_MULTIPLIER = 3150;
        final long XP_TO_ADD = XP_MULTIPLIER + 1;
        this.hero.addExperience(XP_TO_ADD);
    }
}
