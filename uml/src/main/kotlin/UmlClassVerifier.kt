package uml

import verification.ModelVerifier
import verification.model.ModelElement
import verification.model.Relation

class UmlClassVerifier() {
    fun generateNuSMVScript(): String {
        // Meta level model elements
        val modelElements = mutableMapOf(
            /* PhysicalPerson */
            Pair("PhysicalPerson", ModelElement("PhysicalPerson")),
            Pair("PhysicalPerson.birthYear", ModelElement("PhysicalPerson.birthYear")),
            Pair("PhysicalPerson.disabilityRate", ModelElement("PhysicalPerson.disabilityRate")),
            Pair("PhysicalPerson.disabilityType", ModelElement("PhysicalPerson.disabilityType")),
            Pair("PhysicalPerson.getAge", ModelElement("PhysicalPerson.getAge")),

            /* PhysicalPerson subtypes*/
            Pair("Child", ModelElement("Child")),
            Pair("TaxPayer", ModelElement("TaxPayer")),
            Pair("TaxPayer.isResident", ModelElement("TaxPayer.isResident")),

            /* Address and Enums */
            Pair("Address", ModelElement("Address")),
            Pair("Address.country", ModelElement("Address.country")),
            Pair("Country", ModelElement("Country")),
            Pair("Country.LU", ModelElement("Country.LU")),
            Pair("Country.FR", ModelElement("Country.FR")),
            Pair("Country.BE", ModelElement("Country.BE")),
            Pair("Country.DE", ModelElement("Country.DE")),
            Pair("Country.OTHER", ModelElement("Country.OTHER")),
            Pair("Disability", ModelElement("Disability")),
            Pair("Disability.None", ModelElement("Disability.None")),
            Pair("Disability.Vision", ModelElement("Disability.Vision")),
            Pair("Disability._A", ModelElement("Disability._A")),

            /* Associations */
            Pair("PhysicalPerson.addresses", ModelElement("PhysicalPerson.addresses")),
            Pair("TaxPayer.incomes", ModelElement("TaxPayer.incomes")),
            Pair("TaxPayer.children", ModelElement("TaxPayer.children")),
            Pair("Income.taxCard", ModelElement("Income.taxCard")),

            /* The rest */
            Pair("Income", ModelElement("Income")),
            Pair("Income.isLocal", ModelElement("Income.isLocal")),
            Pair("Pension", ModelElement("Pension")),
            Pair("Employment", ModelElement("Employment")),
            Pair("Other", ModelElement("Other")),
            Pair("Constants", ModelElement("Constants")),
            Pair("Constants.YEAR", ModelElement("Constants.YEAR")),
            Pair("TaxCard", ModelElement("TaxCard"))
        )

        /* Literal model elements */
        modelElements.putAll(
            mapOf(
                Pair("Literal_1984", ModelElement("Literal_1984")),
                Pair("Literal_1.0", ModelElement("Literal_1.0")),
                Pair("Literal_2010", ModelElement("Literal_2010")),
                Pair("Literal_0.0", ModelElement("Literal_0.0")),
                Pair("Literal_false", ModelElement("Literal_false")),
                Pair("Literal_true", ModelElement("Literal_true")),
                Pair("Literal_Star", ModelElement("Literal_Star")),
                Pair("Literal_Integer", ModelElement("Literal_Integer")),
                Pair("Literal_Real", ModelElement("Literal_Real")),
                Pair("Literal_Boolean", ModelElement("Literal_Boolean"))
            )
        )

        val fragments = 1
        val relations = listOf(
            "Inheritance", "Attributes", "Value", "EnumerationLiterals",
            "Associations", "AssociationTargets", "CardinalityMin", "CardinalityMax", "Type", "Instantiation"
        )
        val inheritanceTransitions = setOf(
            Pair(modelElements["Child"], modelElements["PhysicalPerson"]),
            Pair(modelElements["TaxPayer"], modelElements["PhysicalPerson"])
        )
        val attributesTransitions = mutableSetOf(
            /* Meta level */
            Pair(modelElements["PhysicalPerson"], modelElements["PhysicalPerson.birthYear"]),
            Pair(modelElements["PhysicalPerson"], modelElements["PhysicalPerson.disabilityRate"]),
            Pair(modelElements["PhysicalPerson"], modelElements["PhysicalPerson.disabilityType"]),
            Pair(modelElements["TaxPayer"], modelElements["TaxPayer.isResident"]),
            Pair(modelElements["Address"], modelElements["Address.country"]),
            Pair(modelElements["Income"], modelElements["Income.isLocal"]),
            Pair(modelElements["Constants"], modelElements["Constants.YEAR"])
        )
        val valueTransitions = mutableSetOf<Pair<ModelElement?, ModelElement?>>()
        val enumerationLiteralTransitions = setOf(
            Pair(modelElements["Country"], modelElements["Country.LU"]),
            Pair(modelElements["Country"], modelElements["Country.FR"]),
            Pair(modelElements["Country"], modelElements["Country.BE"]),
            Pair(modelElements["Country"], modelElements["Country.DE"]),
            Pair(modelElements["Country"], modelElements["Country.OTHER"]),

            Pair(modelElements["Disability"], modelElements["Disability.None"]),
            Pair(modelElements["Disability"], modelElements["Disability.Vision"]),
            Pair(modelElements["Disability"], modelElements["Disability._A"])
        )
        val associationTransitions = mutableSetOf(
            Pair(modelElements["PhysicalPerson"], modelElements["PhysicalPerson.addresses"]),
            Pair(modelElements["TaxPayer"], modelElements["TaxPayer.incomes"]),
            Pair(modelElements["TaxPayer"], modelElements["TaxPayer.children"]),
            Pair(modelElements["Income"], modelElements["Income.taxCard"])
        )
        val associationTargetTransitions = mutableSetOf<Pair<ModelElement?, ModelElement?>>()
        val cardinalityMinTransitions = setOf(
            /* Attributes */
            Pair(modelElements["PhysicalPerson.birthYear"], modelElements["Literal_1.0"]),
            Pair(modelElements["PhysicalPerson.disabilityRate"], modelElements["Literal_1.0"]),
            Pair(modelElements["PhysicalPerson.disabilityType"], modelElements["Literal_1.0"]),
            Pair(modelElements["TaxPayer.isResident"], modelElements["Literal_1.0"]),
            Pair(modelElements["Address.country"], modelElements["Literal_1.0"]),
            Pair(modelElements["Income.isLocal"], modelElements["Literal_1.0"]),
            Pair(modelElements["Constants.YEAR"], modelElements["Literal_1.0"]),

            /* Associations */
            Pair(modelElements["PhysicalPerson.addresses"], modelElements["Literal_1.0"]),
            Pair(modelElements["TaxPayer.incomes"], modelElements["Literal_1.0"]),
            Pair(modelElements["TaxPayer.children"], modelElements["Literal_0.0"]),
            Pair(modelElements["Income.taxCard"], modelElements["Literal_0.0"])
        )
        val cardinalityMaxTransitions = setOf(
            /* Attributes */
            Pair(modelElements["PhysicalPerson.birthYear"], modelElements["Literal_1.0"]),
            Pair(modelElements["PhysicalPerson.disabilityRate"], modelElements["Literal_1.0"]),
            Pair(modelElements["PhysicalPerson.disabilityType"], modelElements["Literal_1.0"]),
            Pair(modelElements["TaxPayer.isResident"], modelElements["Literal_1.0"]),
            Pair(modelElements["Address.country"], modelElements["Literal_1.0"]),
            Pair(modelElements["Income.isLocal"], modelElements["Literal_1.0"]),
            Pair(modelElements["Constants.YEAR"], modelElements["Literal_1.0"]),

            /* Associations */
            Pair(modelElements["PhysicalPerson.addresses"], modelElements["Literal_Star"]),
            Pair(modelElements["TaxPayer.incomes"], modelElements["Literal_Star"]),
            Pair(modelElements["TaxPayer.children"], modelElements["Literal_Star"]),
            Pair(modelElements["Income.taxCard"], modelElements["Literal_1.0"])
        )
        val typeTransitions = setOf(
            /* Attributes */
            Pair(modelElements["PhysicalPerson.birthYear"], modelElements["Literal_Integer"]),
            Pair(modelElements["PhysicalPerson.disabilityRate"], modelElements["Literal_Real"]),
            Pair(modelElements["PhysicalPerson.disabilityType"], modelElements["Disability"]),
            Pair(modelElements["TaxPayer.isResident"], modelElements["Literal_Boolean"]),
            Pair(modelElements["Address.country"], modelElements["Country"]),
            Pair(modelElements["Income.isLocal"], modelElements["Literal_Boolean"]),
            Pair(modelElements["Constants.YEAR"], modelElements["Literal_Integer"])
        )
        val instantiationTransitions = mutableSetOf<Pair<ModelElement?, ModelElement?>>()
        for (i in 1..fragments) {
            // Instance level model elements
            modelElements.putAll(
                mapOf(
                    /* Taxpayer */
                    Pair("TaxPayer_$i", ModelElement("TaxPayer_$i")),
                    Pair("TaxPayer_$i.birthYear", ModelElement("TaxPayer_$i.birthYear")),
                    Pair("TaxPayer_$i.disabilityRate", ModelElement("TaxPayer_$i.disabilityRate")),
                    Pair("TaxPayer_$i.disabilityType", ModelElement("TaxPayer_$i.disabilityType")),
                    Pair("TaxPayer_$i.getAge", ModelElement("TaxPayer_$i.getAge")),
                    Pair("TaxPayer_$i.isResident", ModelElement("TaxPayer_$i.isResident")),
                    Pair("TaxPayer_$i.addresses", ModelElement("TaxPayer_$i.addresses")),
                    Pair("TaxPayer_$i.children", ModelElement("TaxPayer_$i.children")),
                    Pair("TaxPayer_$i.incomes", ModelElement("TaxPayer_$i.incomes")),
                    /* Child */
                    Pair("Child_$i", ModelElement("Child_$i")),
                    Pair("Child_$i.birthYear", ModelElement("Child_$i.birthYear")),
                    Pair("Child_$i.disabilityRate", ModelElement("Child_$i.disabilityRate")),
                    Pair("Child_$i.disabilityType", ModelElement("Child_$i.disabilityType")),
                    Pair("Child_$i.getAge", ModelElement("Child_$i.getAge")),
                    Pair("Child_$i.addresses", ModelElement("Child_$i.addresses")),
                    /* Pension */
                    Pair("Pension_$i", ModelElement("Pension_$i")),
                    Pair("Pension_$i.isLocal", ModelElement("Pension_$i.isLocal")),
                    Pair("Pension_$i.taxCard", ModelElement("Pension_$i.taxCard")),
                    /* Address */
                    Pair("Address_$i", ModelElement("Address_$i")),
                    Pair("Address_$i.country", ModelElement("Address_$i.country")),
                    /* TaxCard */
                    Pair("TaxCard_$i", ModelElement("TaxCard_$i"))
                )
            )
            attributesTransitions.addAll(setOf(
                /* Instance level */
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.birthYear"]),
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.disabilityRate"]),
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.disabilityType"]),
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.isResident"]),

                Pair(modelElements["Child_$i"], modelElements["Child_$i.birthYear"]),
                Pair(modelElements["Child_$i"], modelElements["Child_$i.disabilityRate"]),
                Pair(modelElements["Child_$i"], modelElements["Child_$i.disabilityType"]),

                Pair(modelElements["Pension_$i"], modelElements["Pension_$i.isLocal"]),

                Pair(modelElements["Address_$i"], modelElements["Address_$i.country"])
            ))
            valueTransitions.addAll(setOf(
                Pair(modelElements["TaxPayer_$i.birthYear"], modelElements["Literal_1984"]),
                Pair(modelElements["TaxPayer_$i.disabilityRate"], modelElements["Literal_1.0"]),
                Pair(modelElements["TaxPayer_$i.disabilityType"], modelElements["Disability.None"]),
                Pair(modelElements["TaxPayer_$i.isResident"], modelElements["Literal_false"]),

                Pair(modelElements["Child_$i.birthYear"], modelElements["Literal_2010"]),
                Pair(modelElements["Child_$i.disabilityRate"], modelElements["Literal_0.0"]),
                Pair(modelElements["Child_$i.disabilityType"], modelElements["Disability.None"]),

                Pair(modelElements["Pension_$i.isLocal"], modelElements["Literal_true"]),

                Pair(modelElements["Address_$i.country"], modelElements["Country.LU"])
            ))
            associationTransitions.addAll(setOf(
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.addresses"]),
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.children"]),
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer_$i.incomes"]),
                Pair(modelElements["Pension_$i"], modelElements["Pension_$i.taxCard"])
            ))
            associationTargetTransitions.addAll(setOf(
                Pair(modelElements["TaxPayer_$i.addresses"], modelElements["Address_$i"]),
                Pair(modelElements["TaxPayer_$i.children"], modelElements["Child_$i"]),
                Pair(modelElements["TaxPayer_$i.incomes"], modelElements["Pension_$i"]),
                Pair(modelElements["Pension_$i.taxCard"], modelElements["TaxCard_$i"])
            ))
            instantiationTransitions.addAll(setOf(
                Pair(modelElements["TaxPayer_$i"], modelElements["TaxPayer"]),
                Pair(modelElements["TaxPayer_$i.birthYear"], modelElements["PhysicalPerson.birthYear"]),
                Pair(modelElements["TaxPayer_$i.disabilityRate"], modelElements["PhysicalPerson.disabilityRate"]),
                Pair(modelElements["TaxPayer_$i.disabilityType"], modelElements["PhysicalPerson.disabilityType"]),
                Pair(modelElements["TaxPayer_$i.addresses"], modelElements["PhysicalPerson.addresses"]),
                Pair(modelElements["TaxPayer_$i.isResident"], modelElements["TaxPayer.isResident"]),
                Pair(modelElements["TaxPayer_$i.children"], modelElements["TaxPayer.children"]),
                Pair(modelElements["TaxPayer_$i.incomes"], modelElements["TaxPayer.incomes"]),

                /* Child */
                Pair(modelElements["Child_$i"], modelElements["Child"]),
                Pair(modelElements["Child_$i.addresses"], modelElements["PhysicalPerson.addresses"]),
                Pair(modelElements["Child_$i.birthYear"], modelElements["PhysicalPerson.birthYear"]),
                Pair(modelElements["Child_$i.disabilityRate"], modelElements["PhysicalPerson.disabilityRate"]),
                Pair(modelElements["Child_$i.disabilityType"], modelElements["PhysicalPerson.disabilityType"]),

                /* Pension */
                Pair(modelElements["Pension_$i"], modelElements["Pension"]),
                Pair(modelElements["Pension_$i.isLocal"], modelElements["Income.isLocal"]),
                Pair(modelElements["Pension_$i.taxCard"], modelElements["Income.taxCard"]),

                /* TaxCard */
                Pair(modelElements["TaxCard_$i"], ModelElement("TaxCard"))
            ))
        }
        /* "Inheritance", "Attributes", "Value", "EnumerationLiterals",
        "Associations", "AssociationTargets", "CardinalityMin", "CardinalityMax", "Type", "Instantiation" */

        val ctlFormulas = mutableSetOf(
            /* C2 */ "AG ( (!InitialState & !TaxPayer & ${conforms("PhysicalPerson")}) -> (EX (Attributes & ( EX (${instantiate("PhysicalPerson.disabilityType")} & EX Value & EX Disability.None    )     ) -> EX (${instantiate("PhysicalPerson.disabilityRate")} & EX Value & EX Literal_0.0 )) ) ) ",
            /* C3 */ "AG ( (!InitialState & !TaxPayer & ${conforms("TaxPayer")}  ) -> !((( EX (Associations & AX( (${instantiate("PhysicalPerson.addresses")}) -> EX (AssociationTargets & AX EX Value & !EX Country.LU)))) -> EX Attributes & EX ((${instantiate("TaxPayer.isResident")}) & EX Value & EX Literal_true))))",
            /* C4 */ "AG ( ( !InitialState & !TaxPayer & ${conforms("TaxPayer")} ) -> (( (EX (Associations & EX (${instantiate("TaxPayer.incomes")} & EX EX (Attributes & EX (${instantiate("Income.isLocal")} & EX Value & EX Literal_true))))) & !(EX (Associations & EX (${instantiate("PhysicalPerson.addresses")} & EX EX (Attributes & EX (${instantiate("Address.country")} & EX Value & EX Country.LU ))))) ) -> EX (Attributes & EX (${instantiate("TaxPayer.isResident")} & EX Value & EX Literal_false )))   )",
            /* C5 */ "AG ((${instantiate("Other")} -> (!EX(Associations & EX ${instantiate("Income.taxCard")}))) & ( (${conforms("Income")} &!${instantiate("Other")}) -> EX (Associations & EX ${instantiate("Income.taxCard")}) )   )",
        )

        val allRelations = setOf(
            Relation(relations[0], inheritanceTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[1], attributesTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[2], valueTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[3], enumerationLiteralTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[4], associationTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[5], associationTargetTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[6], cardinalityMinTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[7], cardinalityMaxTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[8], typeTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
            Relation(relations[9], instantiationTransitions.map { Pair(it.first!!, it.second!!) }.toSet()),
        )

        val verifier = ModelVerifier(
            modelElements.values.toSet(),
            allRelations,
            modelElements.values.toSet(),
            ctlFormulas
        )

        return verifier.generateNuSMVScript()
    }

    private fun instantiate(target: String) = "EX (Instantiation & EX $target)"
    private fun conforms(target: String) = "${instantiate(target)} | EX (Instantiation & ${inherit(target)})"

    private fun inherit(target: String) =
        "E[(!Instantiation & !Attributes & !Value & !EnumerationLiterals & !Associations & !AssociationTargets & !CardinalityMin & !CardinalityMax & !Type) U ($target)]"
}