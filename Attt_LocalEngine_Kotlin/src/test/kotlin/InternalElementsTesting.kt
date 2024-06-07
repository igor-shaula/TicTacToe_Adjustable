import elements.*
import logic.GameEngine
import publicApi.AtttGame
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InternalElementsTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    // region GameEngine preparation

    @Test
    fun gameIsNotStarted_classic3x3GameIsCreated_classic3x3GameFieldIsReady() {
        val game = prepareClassic3x3GameField()
        assertTrue(isGameFieldReady(game.gameField))
    }

    @Test
    fun gameIsNotStarted_gameFieldOfAnyCorrectSizeIsCreated_gameFieldWithSpecifiedSizeIsReady() {
        val game = GameEngine(7, 5)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game.gameField))
        assertEquals(7, getGameFieldSideLength(game.gameField))
    }

    @Test
    fun gameIsNotStarted_tooSmallGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = 2 -> game would have no sense in this case, the same as with field size of 1
        val game2x2 = GameEngine(2, 2)
        Log.pl("\ngameEngine is ready having this field: ${game2x2.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game2x2.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(game2x2.gameField))
        // size = maxLength = 0
        val game0x0 = GameEngine(0, 0)
        Log.pl("\ngameEngine is ready having this field: ${game0x0.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game0x0.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(game0x0.gameField))
        // size = maxLength = -1
        val gameM1M1 = GameEngine(-1, -1)
        Log.pl("\ngameEngine is ready having this field: ${gameM1M1.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameM1M1.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameM1M1.gameField))
        // size = maxLength = Int.MIN_VALUE
        val gameMxM = GameEngine(Int.MIN_VALUE, Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMxM.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMxM.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMxM.gameField))
    }

    @Test
    fun gameIsNotStarted_tooBigGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength = 1001 -> for now the limit is set to 1000 dots per side but in the future there could be more
        val game1k1 = GameEngine(1001, 1001)
        Log.pl("\ngameEngine is ready having this field: ${game1k1.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game1k1.gameField))
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(game1k1.gameField))
        // size = maxLength = Int.MAX_VALUE
        val gameMM = GameEngine(Int.MAX_VALUE, Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMM.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMM.gameField))
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMM.gameField))
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_underMinIntGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength tries to be less than Int.MIN_VALUE -> there will be overflow of the Int
        val game = GameEngine(Int.MIN_VALUE - 1, Int.MIN_VALUE - 1)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game.gameField))
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(game.gameField))
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_overMaxIntGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength tries to be more than Int.MAX_VALUE -> there will be overflow of the Int
        val game = GameEngine(Int.MAX_VALUE + 1, Int.MAX_VALUE + 1)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(game.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(game.gameField))
    }

    // assertions of this test are made mostly to see interesting effects during different kinds of limits mixing
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_MinAndMaxIntMixedGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = Int.MIN_VALUE + Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinPlusMin = GameEngine(Int.MIN_VALUE + Int.MIN_VALUE, Int.MIN_VALUE + Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMin.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMinPlusMin.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMinPlusMin.gameField))
        println(Int.MIN_VALUE + Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinMinusMin = GameEngine(Int.MIN_VALUE - Int.MIN_VALUE, Int.MIN_VALUE - Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMin.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMinMinusMin.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMinMinusMin.gameField))
        println(Int.MIN_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MAX_VALUE = -2 in fact - this is an interesting effect of the Int
        val gameMaxPlusMax = GameEngine(Int.MAX_VALUE + Int.MAX_VALUE, Int.MAX_VALUE + Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMax.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMaxPlusMax.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMaxPlusMax.gameField))
        println(Int.MAX_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MAX_VALUE = 0 which is obvious
        val gameMaxMinusMax = GameEngine(Int.MAX_VALUE - Int.MAX_VALUE, Int.MAX_VALUE - Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMax.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMaxMinusMax.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMaxMinusMax.gameField))
        println(Int.MAX_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MAX_VALUE = +1 in fact - this is an interesting effect of the Int
        val gameMinMinusMax = GameEngine(Int.MIN_VALUE - Int.MAX_VALUE, Int.MIN_VALUE - Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMax.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMinMinusMax.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMinMinusMax.gameField))
        println(Int.MIN_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MIN_VALUE = -1 which is obvious
        val gameMaxMinusMin = GameEngine(Int.MAX_VALUE - Int.MIN_VALUE, Int.MAX_VALUE - Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMin.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMaxMinusMin.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMaxMinusMin.gameField))
        println(Int.MAX_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE + Int.MAX_VALUE = -1 which is obvious
        val gameMinPlusMax = GameEngine(Int.MIN_VALUE + Int.MAX_VALUE, Int.MIN_VALUE + Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMax.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMinPlusMax.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMinPlusMax.gameField))
        println(Int.MIN_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MIN_VALUE = -1 which is obvious
        val gameMaxPlusMin = GameEngine(Int.MAX_VALUE + Int.MIN_VALUE, Int.MAX_VALUE + Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMin.gameField?.prepareForPrintingIn2d()}")
        assertTrue(isGameFieldReady(gameMaxPlusMin.gameField))
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, getGameFieldSideLength(gameMaxPlusMin.gameField))
        println(Int.MAX_VALUE + Int.MIN_VALUE)
    }

    // endregion GameEngine preparation

    // region line direction & next spot detection

    @Test
    fun having2dField_anyLineDirectionChosen_detectingOppositeDirectionIsCorrect() {
        assertEquals(LineDirection.XpY0, LineDirection.XmY0.opposite())
        assertEquals(LineDirection.XmY0, LineDirection.XpY0.opposite())
        assertEquals(LineDirection.X0Yp, LineDirection.X0Ym.opposite())
        assertEquals(LineDirection.X0Ym, LineDirection.X0Yp.opposite())
        assertEquals(LineDirection.XpYp, LineDirection.XmYm.opposite())
        assertEquals(LineDirection.XmYm, LineDirection.XpYp.opposite())
        assertEquals(LineDirection.XpYm, LineDirection.XmYp.opposite())
        assertEquals(LineDirection.XmYp, LineDirection.XpYm.opposite())
        assertEquals(LineDirection.None, LineDirection.None.opposite())
    }

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun having3x3Field_1MarkSet_adjacentMarkDetectionLogicIsCorrect() {
        checkTheNextSpotDetectionBlock(Coordinates(0, 0))
        checkTheNextSpotDetectionBlock(Coordinates(0, 1))
        checkTheNextSpotDetectionBlock(Coordinates(0, 2))
        checkTheNextSpotDetectionBlock(Coordinates(1, 0))
        checkTheNextSpotDetectionBlock(Coordinates(1, 1))
        checkTheNextSpotDetectionBlock(Coordinates(1, 2))
        checkTheNextSpotDetectionBlock(Coordinates(2, 0))
        checkTheNextSpotDetectionBlock(Coordinates(2, 1))
        checkTheNextSpotDetectionBlock(Coordinates(2, 2))
    }

    // endregion line direction & next spot detection

    // region line length measurements

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        game.makeMove(firstMark, Player.A)
        game.makeMove(secondMark, Player.A)
        game.printCurrentFieldIn2d()
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.gameField?.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.gameField?.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        game.makeMove(firstMark, Player.A)
        game.makeMove(secondMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.gameField?.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.gameField?.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(1, lengthFromFirstToSecond)
        assertEquals(1, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2RemoteMarksOfTheSamePlayerAreConnected_detectedLineLengthIsCorrect() {
        val game = prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        val connectingMark = Coordinates(1, 0)
        game.makeMove(firstMark, Player.A)
        game.makeMove(secondMark, Player.A)
        game.makeMove(connectingMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.gameField?.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.gameField?.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromFirstToSecond)
        assertEquals(3, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksOfTheSamePlayerAreAddedWithOneMoreMark_detectedLineLengthIsCorrect() {
        val game = prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val oneMoreMark = Coordinates(2, 0)
        game.makeMove(firstMark, Player.A)
        game.makeMove(secondMark, Player.A)
        game.makeMove(oneMoreMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = game.gameField?.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = game.gameField?.measureLineFrom(oneMoreMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByDifferentPlayers_noLineIsCreatedForAnyPlayer() {
        val game = prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val firstActivePlayer = game.activePlayer // should be player A
        game.makeMove(firstMark) // after this line active player is replaced with the next -> B
        Log.pl("measuring line from $firstMark for player: $firstActivePlayer in the forward direction:")
        val lengthForPlayerA = game.gameField?.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        val secondActivePlayer = game.activePlayer // should be player B
        game.makeMove(secondMark) // after this line active player is replaced with the next -> A
        Log.pl("measuring line from $secondMark for player: $secondActivePlayer in the forward direction:")
        val lengthForPlayerB = game.gameField?.measureLineFrom(secondMark, LineDirection.XpY0, 1)
        assertEquals(1, lengthForPlayerA)
        assertEquals(1, lengthForPlayerB)
        game.gameField?.prepareForPrintingIn2d()?.let { Log.pl(it) }
    }

    // endregion line length measurements

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        val game = prepareClassic3x3GameField()
        val someSpot = Coordinates(1, 1)
        game.makeMove(someSpot, Player.A)
        game.makeMove(someSpot, Player.B)
        Log.pl("\ngame field with only one player's mark: ${game.gameField?.prepareForPrintingIn2d()}")
        assertEquals(Player.A, game.gameField?.getCurrentMarkAt(1, 1))
    }

    @Test
    fun having3x3Field_TryToSetMarkForThisPlayerOnWrongPosition_currentPlayerRemainsUnchanged() {
        val game = prepareClassic3x3GameField()
        game.makeMove(-1, -1) // attempt to set the mark on a wrong place
        assertEquals(Player.A, game.activePlayer) // this player remains chosen for the next move
        game.makeMove(1, 1) // another attempt for the same player - this time successful
        assertEquals(Player.B, game.activePlayer) // this time the next player is prepared for a move
        Log.pl("\ngame field with only one player's mark: ${game.gameField?.prepareForPrintingIn2d()}")
        assertEquals(Player.A, game.gameField?.getCurrentMarkAt(1, 1))
    }

    @Test
    fun having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect() {
        println("having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect")
        val atttGame = AtttGame.create(3, 3)
        val game = atttGame as GameEngine
        game.makeMove(Coordinates(0, 0), Player.A)
        game.makeMove(Coordinates(1, 0), Player.A)
        println(game.getWinner().getMaxLineLength())
        println(game.getLeader().getMaxLineLength())
        game.makeMove(Coordinates(2, 0), Player.A)
        // gameField & winning message for player A is printed in the console
        assertEquals(Player.A, game.getWinner())
        println("3x3 Player.A: ${game.getWinner().hashCode()}")
        assertEquals(
            3,
            game.getWinner().getMaxLineLength()
        ) // actual 5 when this test is launched in scope with others
        // todo: fix
    }

    @Test
    fun having2LinesOfOnePlayerOn5x5Field_thisPlayerMarkIsSetInBetween_victoryConditionIsCorrect() {
        val game = GameEngine(5, 5)
        Log.pl("\ntest5x5Field: gameEngine ready with given field: ${game.gameField?.prepareForPrintingIn2d()}")
        game.makeMove(Coordinates(0, 0), Player.A)
        game.makeMove(Coordinates(1, 0), Player.A)
        // GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        game.makeMove(Coordinates(3, 0), Player.A)
        game.makeMove(Coordinates(4, 0), Player.A)
        game.makeMove(Coordinates(2, 0), Player.A) // intentionally placed here to connect 2 segments
        assertEquals(Player.A, game.getWinner())
        println("5x5 Player.A: ${game.getWinner().hashCode()}")
        assertEquals(5, game.getWinner().getMaxLineLength())
    }
}
