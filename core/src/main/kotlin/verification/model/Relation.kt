package verification.model

data class Relation(val id: String, val transitions: Set<Pair<ModelElement, ModelElement>>)
