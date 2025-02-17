class CLI(var arvore: ArvoreGenealogica<Familiar>) {

    fun exibirMenu() {
        var opcao = 0
        var encerrar = false
        println(
            """
            ___________________________________________________________
            Bem-vindo ao Sistema de Árvore Genealógica!
            Selecione uma das opções abaixo:
            ___________________________________________________________
            """.trimIndent()
        )
        while (!encerrar) {
            println(
                """
                1 - Inserir um familiar na árvore
                2 - Buscar um familiar na árvore
                3 - Remover um familiar da árvore
                4 - Visualizar a árvore
                5 - Visualizar relacionamentos de um familiar
                6 - Encerrar o programa
                ___________________________________________________________
                """.trimIndent()
            )
            opcao = readln().toInt()
            when (opcao) {
                1 -> adicionarFamiliar()
                2 -> buscarFamiliar()
                3 -> removerFamiliar()
                4 -> arvore.imprimirArvore(arvore.raiz)
                5 -> visualizarRelacionamentos()
                6 -> {
                    encerrar = true
                    println("Encerrando o programa...")
                }

                7 -> arvore.imprimirNosRegistrados()
                else -> println("Opção inválida! Tente novamente.")
            }
        }

    }

    fun adicionarFamiliar() {
        println("Digite o nome do novo membro: ")
        val nome = readln()
        val novoMembro = Familiar(nome)

        println("Digite o nome do pai do novo membro: ")
        val paiNovoMembro = readln()
        println("Digite o nome da mãe do novo membro: ")
        val maeNovoMembro = readln()

        val sucesso = arvore.inserir(nome, novoMembro, mutableListOf(Familiar(paiNovoMembro), Familiar(maeNovoMembro)))
        if (!sucesso) {
            println("Erro ao adicionar novo membro!")
            return
        }
        println("Novo membro adicionado com sucesso!")
    }

    fun removerFamiliar() {
        println("Digite o nome do familiar que deseja remover da árvore: ")
        val nomeRemover = readln()
        val noEncontrado = arvore.buscarNo(arvore.raiz, nomeRemover)
        var sucesso = arvore.remover(noEncontrado, nomeRemover)
        if (!sucesso) {
            return println("Erro ao remover familiar da árvore!")
        }
        println("Familiar e descendentes removidos com sucesso!")
    }

    fun buscarFamiliar() {
        println("Digite o nome do familiar que deseja buscar: ")
        val nomeBusca = readln()
        val familiarExistente = arvore.buscarNo(arvore.raiz, nomeBusca)
        if (familiarExistente != null) {
            println("Familiar encontrado: ")
            println(familiarExistente.toString())
            return
        }
        println("Familiar não encontrado na árvore!")
    }

    fun visualizarRelacionamentos() {
        println("Digite o nome do familiar que deseja consultar: ")
        val nomeFamiliar = readln()
        val noEncontrado = arvore.buscarNo(arvore.raiz, nomeFamiliar)

        val nivelEncontrado = arvore.obterNivel(arvore.raiz, nomeFamiliar)
        println("nivelEncontrado: " + nivelEncontrado)
        if (noEncontrado != null) {
            arvore.imprimirRelacionamentos(arvore.raiz, nomeFamiliar, 0, mutableSetOf(), nivelEncontrado, noEncontrado)
            return
        }
        println("Familiar não encontrado na árvore!")
    }
}