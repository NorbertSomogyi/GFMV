package verification

import verification.model.Exclusion
import verification.model.ModelElement
import verification.model.Relation
import verification.model.State

/*
     MODULE main
     VAR
        state : {s0,s1,s2,s3,s4};
     ASSIGN
        init(state) := s0;
        next(state):=
             case
                 state = s0 : {s1,s2};
                 state = s1 : {s1,s2};
                 state = s2 : {s1,s2,s3};
                 state = s3 : {s1,s4};
                 state = s4 : {s4};
             esac;
     DEFINE
        p := state = s1 | state = s2 | state = s3 | state = s4;
        q := state = s1 | state = s2;
        r := state = s3;
     SPEC
       EG p;
     SPEC
       AG p;
     SPEC
       EF (AG p);
 */

class ModelVerifier(
    private val modelElements: Set<ModelElement>,
    private val relations: Set<Relation>,
    private val initialStateTransitions: Set<ModelElement>,
    private val ctlFormulas: Set<String>,
    private val exclusion: Exclusion? = null
) {
    private val states = mutableListOf<State>()
    private val script = StringBuilder()

    private val initialStateName = "s0"
    private val scriptStateName = "state"

    private var transitionCount = 0

    init {
        createStatesAndTransitions()
    }

    fun generateNuSMVScript(): String {
        generateBeginning()
        generateTransitions()
        generatePropositions()
        generateCTLFormulas()
        return script.toString()
    }

    private fun generateBeginning() {

        script.appendLine("MODULE main")
        script.appendLine("VAR")
        script.append("\tstate : { ")
        script.append(states.joinToString())
        script.appendLine("};")
    }

    private fun generateTransitions() {
        script.appendLine("ASSIGN")
        script.appendLine("\tinit($scriptStateName) := s0;")
        script.appendLine("\tnext($scriptStateName) := ")
        script.appendLine("\t\tcase")
        for (state in states) {
            if (state.nextStates.isNotEmpty()) {
                script.append("\t\t\tstate = $state : {")
                script.append(state.nextStates.joinToString())
                script.appendLine("};")
            } else {
                script.append("\t\t\tstate = $state : {")
                script.append(state)
                script.appendLine("};")
            }
        }
        script.appendLine("\t\tesac;")
    }

    private fun generatePropositions() {
        script.appendLine("DEFINE")
        val propositions = states.getUniquePropositions()
        for (proposition in propositions) {
            script.append("\t$proposition := ")
            generateStatesForProposition(states.getStatesByAtomicProposition(proposition))
        }
        if (exclusion != null) {
            script.append("\t${exclusion.exclusionProposition} := ")
            val excludedStates = mutableListOf<State>()
            for (proposition in exclusion.excludedModelElements.map { it.id }) {
                excludedStates.addAll(states.getStatesByAtomicProposition(proposition))
            }
            generateStatesForProposition(excludedStates)
        }
    }

    private fun generateStatesForProposition(states: List<State>) {
        for (state in states) {
            script.append("state = $state")
            if (state.id != states.takeLast(1)[0].id) script.append(" | ")
            else script.appendLine(";")
        }
    }

    private fun generateCTLFormulas() {
        for (formula in ctlFormulas) {
            script.appendLine("SPEC")
            script.append(formula)
            script.appendLine(";")
        }
    }

    private fun createStatesAndTransitions() {
        states.add(State("s0", "InitialState"))
        for ((index, element) in modelElements.withIndex()) {
            states.add(State("s${index + 1}", element))
        }

        val initState = states.getStateByID(initialStateName)
        initialStateTransitions.forEach {
            initState.nextStates.add(states.getStateByModelElement(it))
        }

        for (relation in relations) {
            for (transition in relation.transitions) {
                val source = states.getStateByModelElement(transition.first)
                val target = states.getStateByModelElement(transition.second)

                val id = "$source${relation.id}"
                var relationalState = states.firstOrNull { it.id == id }
                if (relationalState == null) {
                    relationalState = State(id, relation.id)
                    states += relationalState
                    source.nextStates += relationalState
                }
                relationalState.nextStates += target

                transitionCount++
            }
        }
    }
}