import internalElements.MAX_WINNING_LINE_LENGTH
import internalElements.MIN_WINNING_LINE_LENGTH

/**
 * AtttRules = Adjustable TicTacToe Rules
 * a single point of check if anybody wins, also container for all limitations & settings of game mechanics.
 */
class AtttRules(
    private var winningLength: Int,
    // potentially here we can later add more criteria to detect if the game is won by any of players
) {
    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (winningLength > MAX_WINNING_LINE_LENGTH) winningLength = MAX_WINNING_LINE_LENGTH
        else if (winningLength < MIN_WINNING_LINE_LENGTH) winningLength = MIN_WINNING_LINE_LENGTH
    }

    internal fun isGameWon(lineLength: Int) = lineLength >= winningLength
}
