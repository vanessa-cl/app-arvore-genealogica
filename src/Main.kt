fun main() {
    var arvore = ArvoreGenealogica<Familiar>()
    var cli = CLI(arvore)

    // dados para testes da árvore
    // raiz
    var maria = Familiar("Maria")

//     pais de Maria
    var joao = Familiar("João")
    var julia = Familiar("Júlia")

//     pais de João
    var amanda = Familiar("Amanda")
    var carlos = Familiar("Carlos")

//     pais de Julia
    var jose = Familiar("José")
    var fernanda = Familiar("Fernanda")

//     inserindo raiz e pais da raiz
    arvore.inserir(maria.nome, maria, mutableListOf(joao, julia))
    arvore.inserir(joao.nome, joao, mutableListOf(amanda, carlos))

    arvore.inserir(julia.nome, julia, mutableListOf(jose, fernanda))

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

    cli.exibirMenu()
}

