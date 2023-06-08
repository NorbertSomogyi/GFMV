package verification

import verification.model.ModelElement
import verification.model.State

fun List<State>.getStateByID(id: String) = first { it.id == id }
fun List<State>.getStateByModelElement(element: ModelElement) = first { it.modelElement == element }
fun List<State>.getStatesByAtomicProposition(proposition: String) = filter {it.atomicPropositions.contains(proposition)}
fun List<State>.getUniquePropositions() : Set<String> {
    val result = mutableSetOf<String>()
    for (state in this) {
        result.addAll(state.atomicPropositions)
    }
    return result
}