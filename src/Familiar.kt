data class Familiar(val nome: String, val idade: Int? = null) {

    override fun toString(): String {
        val imprimirIdade = if (idade != null) ", Idade: $idade)" else ")"
        return "(Nome: $nome$imprimirIdade"
    }
}