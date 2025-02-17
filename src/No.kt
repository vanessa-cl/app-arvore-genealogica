data class No<T>(val pessoa: T? = null) {
    var pais: MutableList<No<T>?> = mutableListOf()
    var filhos: MutableList<No<T>?> = mutableListOf()

    override fun toString(): String {
        val paisNomes = pais.joinToString { it?.pessoa.toString() }
        val filhosNomes = filhos.joinToString { it?.pessoa.toString() }
        return "No(${pessoa}) → Pais: [$paisNomes], Filhos: [$filhosNomes]"
    }
}