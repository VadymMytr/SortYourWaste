package vadym.my.wastesorting.domain.base

abstract class BaseNoParamsUseCase<OUT> : BaseUseCase<Unit, OUT>() {

    suspend operator fun invoke(): OUT = invoke(Unit)
}
