class CLI(var arvore: ArvoreGenealogica) {

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
                5 - Imprimir um relacionamento familiar
                6 - Encerrar o programa
                ___________________________________________________________
                """.trimIndent()
            )
            opcao = readln().toInt()
            when (opcao) {
                1 -> adicionarFamiliar()
                2 -> println("2")
                3 -> removerFamiliar()
                4 -> arvore.imprimirArvore(arvore.raiz)
                5 -> println("5")
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

        arvore.inserir(novoMembro, mutableListOf(Familiar(paiNovoMembro), Familiar(maeNovoMembro)))
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
//        arvore.imprimirNosRegistrados()
    }
}