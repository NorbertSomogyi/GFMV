package verification.model

data class State(val id: String, val modelElement: ModelElement, var atomicPropositions: List<String> = listOf(modelElement.id) ) {

    constructor(id: String, atomicProposition: String) : this(id, ModelElement("${id}Dummy"), listOf(atomicProposition))

    val nextStates = mutableSetOf<State>()

    override fun toString() = id
}
