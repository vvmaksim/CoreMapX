package viewmodel.visualizationStrategy

data class AnimationParameters(
    var iterations: Int = 500,
    var area: Double = 4000000.0,
    var gravity: Double = 0.01,
    var speed: Double = 0.1,
)
