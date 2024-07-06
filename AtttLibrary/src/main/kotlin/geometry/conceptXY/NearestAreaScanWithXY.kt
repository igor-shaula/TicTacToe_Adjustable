package geometry.conceptXY

import attt.Player
import gameLogic.GameField
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.getMaxLengthAchievedForThisMove

internal class NearestAreaScanWithXY(private val gameField: GameField) : OneMoveProcessing {

    override fun getMaxLengthAchievedForThisMove(
        where: Coordinates,
        saveNewLine: (Player, Line) -> Unit,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int? = getMaxLengthAchievedForThisMove<CoordinatesXY>(
        LineDirectionForXY.getAllFromLoops(), gameField, where, saveNewLine, addNewMark
    )

    override fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = CoordinatesXY(x, y)
}