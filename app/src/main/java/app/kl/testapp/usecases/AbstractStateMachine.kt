package app.kl.testapp.usecases

abstract class AbstractStateMachine<State, Event, SideEffect>(
    private val sideEffectRunner: (SideEffect) -> Unit,
    private val stateUpdateHandler: (State) -> Unit,
    initialState: State
) {

    protected var state: State = initialState
        private set

    fun onEvent(event: Event) {
        onEventInternal(event)?.run {
            state = first
            second?.let(sideEffectRunner)
            stateUpdateHandler(first)
        }
    }
    protected abstract fun onEventInternal(event: Event): Pair<State, SideEffect?>?

    data class Update<State, SideEffect>(val newState: State, val sideEffect: SideEffect)
}