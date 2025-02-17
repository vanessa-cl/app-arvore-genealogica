fun main() {
    var arvore = ArvoreGenealogica<Familiar>()
    var cli = CLI(arvore)

    var jean = Familiar("Jean Eu")

    var jerrivan = Familiar("Jerrivan Pai")
    var eliane = Familiar("Eliane Mãe")

    var eli = Familiar("Eli Tia")
    var isis = Familiar("Isis Prima")
    var leo = Familiar("Leo Marido da Tia")

    var matildes = Familiar("Maria Matildes Vó Materna")
    var valter = Familiar("Valter Vô Materno")

    var carmelita = Familiar("Carmelita Vó Paterna")
    var joao = Familiar("João Vô Paterno")

    arvore.inserir(jean.nome, jean, mutableListOf(jerrivan, eliane))
    arvore.inserir(jerrivan.nome, jerrivan, mutableListOf(carmelita, joao))
    arvore.inserir(eliane.nome, eliane, mutableListOf(matildes, valter))
    arvore.inserir(eli.nome, eli, mutableListOf(matildes, valter))
    arvore.inserir(isis.nome, isis, mutableListOf(eli, leo))


    var bisa1 = Familiar("Bisa 1 - Mãe de Maria Matildes")
    var bisa2 = Familiar("Bisa 2 - Pai de Maria Matildes")

    var bisa3 = Familiar("Bisa 3 - Mãe de Valter")
    var bisa4 = Familiar("Bisa 4 - Pai de Valter")

    var bisa5 = Familiar("Bisa 5 - Mãe de Carmelita")
    var bisa6 = Familiar("Bisa 6 - Pai de Carmelita")

    var bisa7 = Familiar("Bisa 7 - Mãe de João")
    var bisa8 = Familiar("Bisa 8 - Pai de João")

    arvore.inserir(matildes.nome, matildes, mutableListOf(bisa1, bisa2))
    arvore.inserir(valter.nome, valter, mutableListOf(bisa3, bisa4))
    arvore.inserir(carmelita.nome, carmelita, mutableListOf(bisa5, bisa6))
    arvore.inserir(joao.nome, joao, mutableListOf(bisa7, bisa8))

    cli.exibirMenu()
}

