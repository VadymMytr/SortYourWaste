package vadym.my.wastesorting.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

abstract class BaseUseCase<IN, OUT> {

    suspend operator fun invoke(parameters: IN): OUT {
        return withContext(executionContext) {
            execute(parameters)
        }
    }

    protected open val executionDispatcher = Dispatchers.Default
    protected abstract suspend fun execute(parameters: IN): OUT

    private val executionJob = Job()
    private val executionContext get() = executionJob + executionDispatcher
}
