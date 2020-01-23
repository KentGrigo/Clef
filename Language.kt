package staticanalysis

sealed class Language

data class Variable(val name: String) : Language()
data class Integer(val value: Int) : Language()
data class Bool(val value: Boolean) : Language()

data class Addition(val expression1: Language, val expression2: Language) : Language()
data class Subtraction(val expression1: Language, val expression2: Language) : Language()
data class Multiplication(val expression1: Language, val expression2: Language) : Language()
data class Division(val expression1: Language, val expression2: Language) : Language()

data class LessThan(val expression1: Language, val expression2: Language) : Language()
data class LessThanOrEqual(val expression1: Language, val expression2: Language) : Language()
data class Equal(val expression1: Language, val expression2: Language) : Language()

data class VariableAssignment(val variable: Variable, val expression: Language) : Language()
data class FunctionAssignment(val variable: Variable, val parameters: List<Variable>, val expression: Language) :
    Language()

data class If(val condition: Language, val expression1: Language, val expression2: Language) : Language()
data class Statement(val statement: Language, val expression: Language) : Language()
data class FunctionCall(val name: Variable, val arguments: List<Language>) : Language()
