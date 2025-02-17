fun main() {
    var arvore = ArvoreGenealogica()
    var cli = CLI(arvore)


//    var vagner = Familiar("Vagner")
    var vanessa = Familiar("Vanessa")

    // pais de Vanessa e Vagner
    var valdivio = Familiar("Valdivio")
    var mariaAparecida = Familiar("Maria Aparecida")

    // pais de Maria Aparecida
    var joventina = Familiar("Joventina")
    var geraldo = Familiar("Geraldo")

    // pais de Valdivio
    var eunice = Familiar("Eunice")
    var arnobio = Familiar("Arnobio")

    arvore.inserir(vanessa, mutableListOf(valdivio, mariaAparecida))
//    arvore.inserir(vagner, mutableListOf(valdivio, mariaAparecida))
    arvore.inserir(valdivio, mutableListOf(eunice, arnobio))
    arvore.inserir(mariaAparecida, mutableListOf(joventina, geraldo))

    // pais de Joventina
    var clarice = Familiar("Clarice")
    var pedro = Familiar("Pedro")

    // pais de Geraldo
    var ana = Familiar("Ana")
    var jose = Familiar("José")

    // pais de Eunice
    var julia = Familiar("Julia")
    var joao = Familiar("João")

    // pais de Arnobio
    var helena = Familiar("Helena")
    var carlos = Familiar("Carlos")

    arvore.inserir(joventina, mutableListOf(clarice, pedro))
    arvore.inserir(geraldo, mutableListOf(ana, jose))
    arvore.inserir(eunice, mutableListOf(julia, joao))
    arvore.inserir(arnobio, mutableListOf(helena, carlos))

    cli.exibirMenu()
}

