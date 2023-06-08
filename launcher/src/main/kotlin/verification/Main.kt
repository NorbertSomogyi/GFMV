package verification

import uml.UmlClassVerifier
import java.io.File

fun main() {
    val verifier = UmlClassVerifier()
    createNuSMVScript(verifier.generateNuSMVScript())
}

private fun createNuSMVScript(nusmv: String) {
    File("./output/verify.smv").writeText(nusmv)
}