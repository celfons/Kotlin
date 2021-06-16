import br.com.itau.pl3.Desenho.Companion.desenhar

enum class Geometria(val dimensoes: String) {
    PLANA("2D"),
    ESPACIAL("3D")
}

interface FormaGeometrica {

    fun calcularPerimetro(): Int

}

sealed class NaoPoligono(): FormaGeometrica { // Classe abstrata que não permite sobrescrita em seus

    fun validaNaoPoligono() = true

}

class `Forma com lados não formados apenas por segmentos de reta`: NaoPoligono() {

    override fun calcularPerimetro(): Int  = TODO("Calculo complexo para implementar")

    fun validaForma() = validaNaoPoligono() //override fun validaNaoPoligono() nao pode ser sobrescrito, somente herdado

}

abstract class Poligono(private val numeroLados: Int? = 3, private val lados: Array<Int>?): FormaGeometrica {

    internal fun somaAnguloExterno() = 360

    internal fun somaAnguloInterno() = (numeroLados!! - 2) * 180

    internal fun validaPoligono() = numeroLados == lados?.size

    override fun calcularPerimetro(): Int {

        var soma = 0

        lados?.forEach { soma += it }

        return soma

    }

    protected fun numeroLados() = numeroLados

    abstract fun calcularArea(): Int

}

open class Quadrilatero(
    a: Int,
    b: Int,
    c: Int,
    d: Int
) : Poligono(4, arrayOf(a, b, c, d)) {

    //override fun somaAnguloExterno() = 180 - metodo com nivel de acesso restrito a sobrescrita

    override fun calcularArea(): Int =
        TODO("A área de um quadrilátero é a soma das área dos triângulos obtidos quando traçamos uma das diagonais.")

}

data class Quadrado(private val lado: Int): Quadrilatero(lado, lado, lado, lado) {

    override fun calcularPerimetro(): Int = lado * numeroLados()!!

    override fun calcularArea() = lado * lado

}

data class Cubo(val lado: Int): Quadrilatero(lado, lado, lado, lado)

class Desenho(var nome: String) {

    companion object { // static methods

        fun desenhar(formasGeometricas: FormaGeometrica): String =
            when(formasGeometricas) {
                 is Quadrado -> Geometria.PLANA.dimensoes
                 is Cubo -> Geometria.ESPACIAL.dimensoes
                 else -> "NAO ENCONTRADO"
            }

    }

}

fun main() {

    val quadrilatero: Quadrilatero = Quadrilatero(3,3,3,3)

    val desenho = Desenho("meu desenho")

    val geometria = desenhar(quadrilatero)

    println("Desenho feito em: $geometria")

    //println("Numero de lados: " + quadrilatero.numeroLados()) - nivel de acesso somente a classe que herda

    val quadrado = Quadrado(2)

    // quadrado.lado = 4 - valor imutavel

    desenho.nome = "nosso desenho"

    println("Novo nome do meu desenho: ${desenho.nome}")

    val ePoligono = quadrado.validaPoligono()

    println("Valida poligono: $ePoligono")

    println("Um quadrado é um quadrilatero? " + (quadrado is Quadrilatero))

    val perimetro = quadrado.calcularPerimetro()

    println("Valor perimetro: $perimetro")

    println("O quadrado tem mesmo perimetro que o quadrilatero? " + (perimetro == quadrilatero.calcularPerimetro()))

    val area = quadrado.calcularArea()

    println("Valor area: $area")

    val somaAnguloExterno = quadrado.somaAnguloExterno()

    println("Soma angulo externo: $somaAnguloExterno")

    val somaAnguloInterno = quadrado.somaAnguloInterno()

    println("Soma angulo interno: $somaAnguloInterno")

}
