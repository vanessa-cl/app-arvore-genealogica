data class No(val familiar: Familiar) {
    var pais: MutableList<No?> = mutableListOf()
    var filhos: MutableList<No?> = mutableListOf()

    override fun toString(): String {
        val paisNomes = pais.joinToString { it?.familiar?.nome.toString() }
        val filhosNomes = filhos.joinToString { it?.familiar?.nome.toString() }
        return "No(${familiar.nome}) → Pais: [$paisNomes], Filhos: [$filhosNomes]"
    }
}