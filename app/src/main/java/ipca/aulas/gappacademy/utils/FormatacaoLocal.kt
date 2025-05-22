package ipca.aulas.gappacademy.utils

object FarmatacaoLocal {
    private val locationMap = mapOf(
        "vilaverde" to "Vila Verde",
        "famalicao" to "Famalicão",
        "braga" to "Braga",
        "barcelos" to "Barcelos",
        "guimaraes" to "Guimarães",
        "esposende" to "Esposende"

    )

    object formatarLocal {
        fun formatLocation(rawLocation: String?): String {
            if (rawLocation.isNullOrEmpty()) return "Local não informado"

            val baseLocation = rawLocation.substringBefore('_')
            return locationMap[baseLocation] ?: baseLocation.replaceFirstChar { it.uppercase() }
        }
    }
}