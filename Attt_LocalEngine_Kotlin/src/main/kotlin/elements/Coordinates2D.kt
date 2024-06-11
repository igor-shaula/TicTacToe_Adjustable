package elements

// extensible alternative for CoordinatesXY
data class Coordinates2D(val xAxis: OneAxis, val yAxis: OneAxis) : Coordinates(xAxis.l, yAxis.l) {

    constructor(x: Int, y: Int) : this(OneAxis(x), OneAxis(y))

    internal fun existsWithin(sideLength: Int): Boolean =
        xAxis.l in 0 until sideLength && yAxis.l in 0 until sideLength

    internal fun getNextInTheDirection(
        xAxisDirection: LineDirectionForOneAxis, yAxisDirection: LineDirectionForOneAxis
    ) = Coordinates2D( // should be exactly Coordinates2D
        OneAxis(xAxis.l + xAxisDirection.deltaOne), OneAxis(yAxis.l + yAxisDirection.deltaOne)
    )

    internal fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionForOneAxis, yAxisDirection: LineDirectionForOneAxis, sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants")
        when {
            false || // just for the following cases alignment
                    xAxis.l <= minIndex && xAxisDirection == LineDirectionForOneAxis.Minus || // X is out of game field
                    xAxis.l >= maxIndex && xAxisDirection == LineDirectionForOneAxis.Plus || // X is out of game field
                    yAxis.l <= minIndex && yAxisDirection == LineDirectionForOneAxis.Minus || // X is out of game field
                    yAxis.l >= maxIndex && yAxisDirection == LineDirectionForOneAxis.Plus -> // X is out of game field
                return Border
        }
        return Coordinates2D(
            OneAxis(xAxis.l + xAxisDirection.deltaOne), OneAxis(yAxis.l + yAxisDirection.deltaOne)
        )
    }
}