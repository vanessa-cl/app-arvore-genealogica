import java.util.*

class ArvoreGenealogica {
    private val nosRegistrados: MutableList<No?> = mutableListOf()
    var raiz: No? = null

    fun criarNo(familiar: Familiar): No? {
        var noCriado = No(familiar)
        nosRegistrados.add(noCriado)
        return noCriado
    }

    fun buscarNo(noAtual: No?, nome: String, visitados: MutableSet<No> = mutableSetOf()): No? {
        if (noAtual == null || noAtual in visitados) return null
        if (noAtual.familiar.nome == nome) return noAtual

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
        familiar: Familiar,
        pais: List<Familiar> = emptyList(),
        filhos: List<Familiar> = emptyList(),
    ) {
        val novoNo = buscarNo(raiz, familiar.nome) ?: criarNo(familiar)

        if (raiz == null) {
            raiz = novoNo
        } else if (raiz?.familiar?.nome == familiar.nome) {
            return
        }

        if (pais.isNotEmpty()) {
            for (pai in pais) {
                var noPaiExistente = buscarNo(raiz, pai.nome) ?: criarNo(pai)

                if (noPaiExistente != null) {
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

        if (filhos.isNotEmpty()) {
            for (filho in filhos) {
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

    fun imprimirArvore(raiz: No?) {
        if (raiz == null) {
            println("A árvore está vazia.")
            return
        }

        val fila: Queue<Pair<No, Int>> = LinkedList()
        val niveis = mutableMapOf<Int, MutableList<String>>()
        val visitados = mutableSetOf<No>()

        fila.add(raiz to 0)
        visitados.add(raiz)

        while (fila.isNotEmpty()) {
            val (noAtual, nivel) = fila.poll()

            niveis.computeIfAbsent(nivel) { mutableListOf() }.add(noAtual.familiar.nome)

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
            println(no.toString())
        }
    }

    fun remover(noAtual: No?, nome: String): Boolean {
        if (noAtual == null) return false

        if (noAtual == raiz) {
            if (noAtual.familiar.nome == nome) {
                raiz = noAtual
            } else {
                raiz = noAtual.pais[0]
            }
        }

        val filhosCopia = noAtual.filhos.toList()
        for (filho in filhosCopia) {
            if (filho != null && noAtual.familiar.nome == nome) {
                filho.pais.remove(noAtual)
            }
            if (filho != null && filho.pais.size < 1) {
                remover(filho, nome)
            }
        }

        val paisCopia = noAtual.pais.toList()
        for (pai in paisCopia) {
            if (noAtual.familiar.nome == nome) {
                pai?.filhos?.remove(noAtual)
            }
        }

        noAtual.filhos.clear()
        noAtual.pais.clear()

        return true
    }

    fun obterNivel(noAtual: No?, nome: String, nivel: Int = 0, visitados: MutableSet<No> = mutableSetOf()): Int? {
        if (noAtual == null || noAtual in visitados) return null

        visitados.add(noAtual)

        if (noAtual.familiar.nome == nome) {
            return nivel
        }

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


    fun imprimirRelacionamentos(
        noAtual: No?,
        nome: String,
        nivel: Int = 0,
        visitados: MutableSet<No> = mutableSetOf(),
        nivelBase: Int?,
        noBase: No
    ) {
        if (noAtual == null) return
        if (noAtual in visitados) return

        visitados.add(noAtual)

        if (nivelBase != null) {
            val relacionamento = when {
                nivel == nivelBase && noAtual.familiar.nome != nome -> return
                nivel == nivelBase -> "Eu"
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
            println("$relacionamento: ${noAtual.familiar.nome}")
        }


        for (pai in noAtual.pais) {
            if (nivelBase != null) {
                if ((nivelBase + nivel) >= 2 && !noBase.filhos.contains(pai) && nivel == 0) {
                    continue
                }
            }
            if (nivel == nivelBase && noAtual.familiar.nome != nome) {
                return
            }
            imprimirRelacionamentos(pai, nome, nivel + 1, visitados, nivelBase, noBase)
        }
        for (filho in noAtual.filhos) {
            imprimirRelacionamentos(filho, nome, nivel - 1, visitados, nivelBase, noBase)
        }

    }

}