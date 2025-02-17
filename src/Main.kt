fun main() {
    var arvore = ArvoreGenealogica<Familiar>()
    var cli = CLI(arvore)

    // dados para testes da árvore
    // raiz
    var maria = Familiar("Maria", 12)
    var joana = Familiar("Joana", 6)

//     pais de Maria
    var joao = Familiar("João", 30)
    var julia = Familiar("Júlia", 32)

//     pais de João
    var amanda = Familiar("Amanda", 61)
    var carlos = Familiar("Carlos", 65)

//     pais de Julia
    var jose = Familiar("José", 57)
    var fernanda = Familiar("Fernanda", 60)

//     inserindo raiz e pais da raiz
    arvore.inserir(maria.nome, maria, mutableListOf(joao, julia))
    arvore.inserir(joao.nome, joao, mutableListOf(amanda, carlos))
    arvore.inserir(julia.nome, julia, mutableListOf(jose, fernanda))
    arvore.inserir(joana.nome, joana, mutableListOf(joao, julia))

    // pais de Amanda
    var clarice = Familiar("Clarice")
    var ricardo = Familiar("Ricardo")

    // pais de Carlos
    var helena = Familiar("Helena")
    var pedro = Familiar("Pedro")

    // pais de José
    var juliana = Familiar("Juliana")
    var renato = Familiar("Renato")

    // pais de Fernanda
    var ana = Familiar("Ana")
    var rafael = Familiar("Rafael")


    arvore.inserir(amanda.nome, amanda, mutableListOf(clarice, ricardo))
    arvore.inserir(carlos.nome, carlos, mutableListOf(helena, pedro))
    arvore.inserir(jose.nome, jose, mutableListOf(juliana, renato))
    arvore.inserir(fernanda.nome, fernanda, mutableListOf(ana, rafael))
    var testeNo = arvore.buscarNo(arvore.raiz, "Joana")
    println("Joana é irmã de Maria: " + arvore.ehIrmao(arvore.raiz, testeNo))

    cli.exibirMenu()
}

