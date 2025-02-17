import java.util.*

class ArvoreGenealogica<T : Any> {
    private val nosRegistrados: MutableList<No<T>?> = mutableListOf()
    var raiz: No<T>? = null

    fun criarNo(dado: T): No<T>? {
        var noCriado = No(dado)
        nosRegistrados.add(noCriado)
        return noCriado
    }

    fun buscarNo(noAtual: No<T>?, nome: String, visitados: MutableSet<No<T>> = mutableSetOf()): No<T>? {
        if (noAtual == null || noAtual in visitados) return null
        if (noAtual.pessoa is Familiar && noAtual.pessoa.nome == nome) return noAtual

        visitados.add(noAtual)

        for (filho in noAtual.filhos) {
            val encontrado = buscarNo(filho, nome, visitados)
            if (encontrado != null) return encontrado
        }

        for (pai in noAtual.pais) {
            val encontrado = buscarNo(pai, nome, visitados)
            if (encontrado != null) return encontrado
        }

        return null
    }

    fun inserir(
        nome: String,
        dado: T,
        pais: List<T> = emptyList(),
        filhos: List<T> = emptyList(),
    ): Boolean {
        val novoNo = buscarNo(raiz, nome) ?: criarNo(dado)

        if (raiz == null) {
            raiz = novoNo
        } else if (raiz?.pessoa == nome) {
            return false
        }

        if (pais.isNotEmpty()) {
            for (pai in pais) {
                if (pai is Familiar) {
                    var noPaiExistente = buscarNo(raiz, pai.nome) ?: criarNo(pai)

                    if (noPaiExistente != null) {
                        for (filho in noPaiExistente.filhos) {
                            if (filho != null && ehIrmao(novoNo, filho)) {
                                if (novoNo != null) {
                                    println("${novoNo.pessoa} é irmão(ã) de ${filho.pessoa}")
                                }
                            }
                        }
                        if (novoNo !in noPaiExistente.filhos) {
                            noPaiExistente.filhos.add(novoNo)
                        }
                    }
                    if (novoNo != null) {
                        if (noPaiExistente !in novoNo.pais) {
                            novoNo.pais.add(noPaiExistente)
                        }
                    }
                }
            }
        }

        if (filhos.isNotEmpty()) {
            for (filho in filhos) {
                if (filho is Familiar) {
                    var noFilho = buscarNo(raiz, filho.nome) ?: criarNo(filho)

                    if (noFilho != null) {
                        if (novoNo !in noFilho.pais) {
                            noFilho.pais.add(novoNo)
                        }
                    }
                    if (novoNo != null) {
                        if (noFilho !in novoNo.filhos) {
                            novoNo.filhos.add(noFilho)
                        }
                    }
                }
            }
        }
        return true
    }

    fun imprimirArvore(raiz: No<T>?) {
        if (raiz == null) {
            println("A árvore está vazia.")
            return
        }

        val fila: Queue<Pair<No<T>, Int>> = LinkedList()
        val niveis = mutableMapOf<Int, MutableList<String>>()
        val visitados = mutableSetOf<No<T>>()

        fila.add(raiz to 0)
        visitados.add(raiz)

        while (fila.isNotEmpty()) {
            val (noAtual, nivel) = fila.poll()

            niveis.computeIfAbsent(nivel) { mutableListOf() }.add(noAtual.pessoa.toString())

            for (pai in noAtual.pais) {
                if (pai != null && pai !in visitados) {
                    fila.add(pai to nivel + 1)
                    visitados.add(pai)
                }
            }

            for (filho in noAtual.filhos) {
                if (filho != null && filho !in visitados) {
                    fila.add(filho to nivel + 1)
                    visitados.add(filho)
                }
            }
        }

        println("\n=== Árvore Genealógica ===")
        niveis.toSortedMap().forEach { (nivel, nomes) ->
            println("Nível ${nivel + 1}: ${nomes.joinToString(" | ")}")
        }
    }

    fun imprimirNosRegistrados() {
        println(nosRegistrados.size)
        for (no in nosRegistrados) {
            println(no)
        }
    }

    fun remover(noAtual: No<T>?, nome: String): Boolean {
        if (noAtual == null) return false

        if (noAtual == raiz) {
            if (noAtual.pessoa == nome) {
                raiz = noAtual
            } else {
                raiz = noAtual.pais[0]
            }
        }

        val filhosCopia = noAtual.filhos.toList()
        for (filho in filhosCopia) {
            if (filho != null && noAtual.pessoa == nome) {
                filho.pais.remove(noAtual)
            }
            if (filho != null && filho.pais.size < 1) {
                remover(filho, nome)
            }
        }

        val paisCopia = noAtual.pais.toList()
        for (pai in paisCopia) {
            if (noAtual.pessoa == nome) {
                pai?.filhos?.remove(noAtual)
            }
        }

        noAtual.filhos.clear()
        noAtual.pais.clear()

        return true
    }

    fun obterNivel(noAtual: No<T>?, nome: String, nivel: Int = 0, visitados: MutableSet<No<T>> = mutableSetOf()): Int? {
        if (noAtual == null || noAtual in visitados) return null

        visitados.add(noAtual)

        if (noAtual.pessoa is Familiar && noAtual.pessoa.nome == nome) return nivel

        for (filho in noAtual.filhos) {
            val nivelFilho = obterNivel(filho, nome, nivel - 1, visitados)
            if (nivelFilho != null) return nivelFilho
        }

        for (pai in noAtual.pais) {
            val nivelPai = obterNivel(pai, nome, nivel + 1, visitados)
            if (nivelPai != null) return nivelPai
        }

        return null
    }

    fun ehIrmao(noBase: No<T>?, noAtual: No<T>?): Boolean {
        if (noAtual == null || noBase == null) return false
        for (pai in noBase.pais) {
            if (pai != null && pai.filhos.contains(noAtual)) {
                return true
            }
        }
        return false
    }

    fun verificaNoPeloNome(noAtual: No<T>?, nome: String): Boolean {
        if (noAtual != null) {
            return noAtual.pessoa is Familiar && noAtual.pessoa.nome == nome
        }
        return false
    }

    fun ehAntepassado(noAtual: No<T>?, antepassado: No<T>?): Boolean {
        if (noAtual == null) return false
        for (pai in noAtual.pais) {
            if (pai == antepassado) {
                return true
            }
            if (ehAntepassado(pai, antepassado)) {
                return true
            }
        }
        return false
    }

    fun ehDescendente(noAtual: No<T>?, descendente: No<T>?): Boolean {
        if (noAtual == null || descendente == null) return false
        if (noAtual.filhos.contains(descendente)) return true

        for (filho in noAtual.filhos) {
            if (filho == descendente) {
                return true
            }
            if (ehDescendente(filho, descendente)) {
                return true
            }
        }

        return false
    }


    fun imprimirRelacionamentos(
        noAtual: No<T>?,
        nome: String,
        nivel: Int = 0,
        visitados: MutableSet<No<T>> = mutableSetOf(),
        nivelBase: Int?,
        noBase: No<T>
    ) {
        if (noAtual == null) return
        if (noAtual in visitados) return

        visitados.add(noAtual)

        if (nivelBase != null) {
            val relacionamento = when {
                nivel == nivelBase && verificaNoPeloNome(noAtual, nome) -> "Eu"
                nivel == nivelBase && !verificaNoPeloNome(noAtual, nome) && ehIrmao(
                    noBase,
                    noAtual
                ) -> "Irmão/Irmã"

                nivel == nivelBase && !verificaNoPeloNome(noAtual, nome) && !ehIrmao(
                    noBase,
                    noAtual
                ) && !noBase.pais.contains(noAtual) && !noBase.filhos.contains(noAtual) -> "Cônjuge"

                nivel == nivelBase.plus(1) -> "Pai/Mãe"
                nivel == nivelBase.plus(2) -> "Avô/Avó"
                nivel == nivelBase.plus(3) -> "Bisavô/Bisavó"
                nivel == nivelBase.plus(4) -> "Tataravô/Tataravó"
                nivel > nivelBase.plus(4) -> "Antepassado de nível $nivel"
                nivel == nivelBase.minus(1) -> "Filho/Filha"
                nivel == nivelBase.minus(2) -> "Neto/Neta"
                nivel == nivelBase.minus(3) -> "Bisneto/Bisneta"
                nivel == nivelBase.minus(4) -> "Tataraneto/Tataraneta"
                else -> "Descendente de nível ${-nivel}"
            }
            println("$relacionamento: ${noAtual.pessoa}")
        }

        for (pai in noAtual.pais) {
            if (!verificaNoPeloNome(noAtual, nome) && pai != null && nivel != nivelBase) {
                if (ehAntepassado(noAtual, pai)) {
                    imprimirRelacionamentos(pai, nome, nivel + 1, visitados, nivelBase, noBase)
                } else {
                    continue
                }
            }
            if (!verificaNoPeloNome(noAtual, nome) && pai != null && nivel == nivelBase) {
                continue
            }
            imprimirRelacionamentos(pai, nome, nivel + 1, visitados, nivelBase, noBase)
        }
        for (filho in noAtual.filhos) {
            if (!verificaNoPeloNome(noAtual, nome) && filho != null && nivel != nivelBase) {
                if (ehDescendente(noAtual, filho)) {
                    imprimirRelacionamentos(filho, nome, nivel - 1, visitados, nivelBase, noBase)
                } else {
                    continue
                }
            }
            if (!verificaNoPeloNome(noAtual, nome) && filho != null && nivel == nivelBase) {
                continue
            }
            imprimirRelacionamentos(filho, nome, nivel - 1, visitados, nivelBase, noBase)
        }

    }

}