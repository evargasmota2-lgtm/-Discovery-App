package com.example.itszapp.model

/**
 * Modelo central de datos para cada carrera del ITSZ Zongolica.
 * Se usa en HomeScreen, CarreraDetailScreen y QuizScreen.
 */
data class Carrera(
    val id: String,
    val nombre: String,
    val abreviacion: String,
    val emoji: String,
    val colorHex: String,          // Color institucional de la carrera
    val descripcionCorta: String,
    val descripcionLarga: String,
    val objetivo: String,
    val duracion: String,
    val modalidad: String,
    val anioApertura: Int,
    val campoLaboral: List<String>,
    val materiasClave: List<String>,
    val habilidades: List<String>,
    val salarioInicial: String,    // Rango mensual MXN aproximado
    val demandaLaboral: Int        // % para barra visual 0-100
)

/**
 * Fuente única de verdad — todas las carreras del Campus Zongolica.
 * Fuente: zongolica.tecnm.mx / TecNM / Data México 2022
 */
object ITSZData {

    val carreras = listOf(

        Carrera(
            id = "isc",
            nombre = "Ingeniería en Sistemas Computacionales",
            abreviacion = "ISC",
            emoji = "💻",
            colorHex = "#1565C0",   // Azul tech
            descripcionCorta = "Diseña, desarrolla e implementa soluciones tecnológicas de impacto regional y global.",
            descripcionLarga = "Desde el primer semestre desarrollas proyectos reales en la región serrana de Veracruz. " +
                    "Aprenderás a construir apps, sistemas web, redes y proyectos de IA que resuelven problemas concretos " +
                    "de tu comunidad. El ITSZ fue el primero en la región en ofrecer esta carrera — desde 2007.",
            objetivo = "Formar profesionistas capaces de analizar, diseñar, desarrollar e implementar sistemas " +
                    "computacionales que cumplan estándares de calidad, aplicando principios de ingeniería con alto compromiso social.",
            duracion = "9 semestres (4.5 años)",
            modalidad = "Presencial • Modelo Dual (práctica desde sem. 1)",
            anioApertura = 2007,
            campoLaboral = listOf(
                "Desarrollador de Software",
                "Administrador de Redes y Bases de Datos",
                "Analista de Sistemas",
                "Especialista en Ciberseguridad",
                "Consultor TI en empresas y gobierno",
                "Emprendedor tech / Startup"
            ),
            materiasClave = listOf(
                "Fundamentos de Programación",
                "Bases de Datos",
                "Desarrollo Web y Móvil",
                "Redes de Computadoras",
                "Inteligencia Artificial",
                "Ingeniería de Software"
            ),
            habilidades = listOf("Programación", "Lógica", "Trabajo en equipo", "Resolución de problemas", "Inglés técnico"),
            salarioInicial = "\$12,000 – \$22,000 MXN/mes",
            demandaLaboral = 92
        ),

        Carrera(
            id = "ige",
            nombre = "Ingeniería en Gestión Empresarial",
            abreviacion = "IGE",
            emoji = "📊",
            colorHex = "#2E7D32",   // Verde empresarial
            descripcionCorta = "Lidera organizaciones, diseña estrategias de negocio e impulsa la innovación empresarial.",
            descripcionLarga = "La IGE es la carrera con mayor matrícula del ITSZ (1,318 alumnos en 2022). " +
                    "Combinando administración moderna con ingeniería, formarás parte del ecosistema empresarial " +
                    "de la zona de Altas Montañas y más allá. Ideal si te apasionan los negocios, el liderazgo y los proyectos de impacto.",
            objetivo = "Formar integralmente profesionales que contribuyan a la gestión de empresas e innovación de procesos, " +
                    "así como al diseño, implementación y desarrollo de sistemas estratégicos de negocios.",
            duracion = "9 semestres (4.5 años)",
            modalidad = "Presencial • Modelo Dual (práctica desde sem. 1)",
            anioApertura = 2009,
            campoLaboral = listOf(
                "Gerente o Director de empresa",
                "Consultor de negocios",
                "Emprendedor / PYME",
                "Analista financiero",
                "Recursos Humanos",
                "Sector público y gobierno"
            ),
            materiasClave = listOf(
                "Administración",
                "Contabilidad y Finanzas",
                "Mercadotecnia",
                "Gestión de Proyectos",
                "Derecho Empresarial",
                "Innovación y Emprendimiento"
            ),
            habilidades = listOf("Liderazgo", "Comunicación", "Análisis financiero", "Toma de decisiones", "Emprendimiento"),
            salarioInicial = "\$10,000 – \$20,000 MXN/mes",
            demandaLaboral = 85
        ),

        Carrera(
            id = "idc",
            nombre = "Ingeniería en Desarrollo Comunitario",
            abreviacion = "IDC",
            emoji = "🌱",
            colorHex = "#558B2F",   // Verde naturaleza
            descripcionCorta = "Planifica y ejecuta proyectos de desarrollo social respetando la identidad cultural de las comunidades.",
            descripcionLarga = "La carrera fundadora del ITSZ — desde 2002. Esta ingeniería es única en su tipo: " +
                    "combina la planeación técnica con el respeto a la diversidad cultural nahua de la zona serrana. " +
                    "Si quieres transformar tu comunidad con proyectos reales de impacto social, esta es tu carrera.",
            objetivo = "Formar ingenieros en desarrollo comunitario con competencias para la planeación, gestión, " +
                    "ejecución y evaluación de proyectos comunitarios, promoviendo el diálogo intercultural.",
            duracion = "9 semestres (4.5 años)",
            modalidad = "Presencial • Modelo Dual",
            anioApertura = 2002,
            campoLaboral = listOf(
                "Promotor de desarrollo social",
                "Gestor de proyectos comunitarios",
                "ONG y organizaciones civiles",
                "Dependencias de gobierno (SEDESOL, CDI)",
                "Consultor de desarrollo rural",
                "Organismos internacionales (ONU, PNUD)"
            ),
            materiasClave = listOf(
                "Planeación Comunitaria",
                "Gestión de Proyectos Sociales",
                "Interculturalidad",
                "Desarrollo Sustentable",
                "Políticas Públicas",
                "Diagnóstico Comunitario"
            ),
            habilidades = listOf("Empatía", "Gestión de proyectos", "Comunicación intercultural", "Liderazgo social", "Diagnóstico"),
            salarioInicial = "\$9,000 – \$16,000 MXN/mes",
            demandaLaboral = 70
        ),

        Carrera(
            id = "if",
            nombre = "Ingeniería Forestal",
            abreviacion = "IF",
            emoji = "🌲",
            colorHex = "#1B5E20",   // Verde bosque
            descripcionCorta = "Maneja, conserva y aprovecha de forma sustentable los recursos forestales de México.",
            descripcionLarga = "El ITSZ fue el primer tecnológico en Veracruz en ofrecer esta carrera — desde 2003. " +
                    "La zona de Altas Montañas de Veracruz es uno de los ecosistemas más ricos del país, " +
                    "lo que hace de esta carrera una opción estratégica con alta relevancia regional y nacional.",
            objetivo = "Formar ingenieros forestales capaces de planear, gestionar y ejecutar proyectos de " +
                    "manejo forestal sustentable, conservación de la biodiversidad y restauración de ecosistemas.",
            duracion = "9 semestres (4.5 años)",
            modalidad = "Presencial • Modelo Dual",
            anioApertura = 2003,
            campoLaboral = listOf(
                "CONAFOR (Comisión Nacional Forestal)",
                "SEMARNAT",
                "Empresas forestales y aserraderos",
                "Consultoría ambiental",
                "Viveros y reforestación",
                "Investigación y docencia"
            ),
            materiasClave = listOf(
                "Dasonomía",
                "Silvicultura",
                "Inventarios Forestales",
                "SIG y Teledetección",
                "Política Forestal",
                "Restauración Ecológica"
            ),
            habilidades = listOf("Campo y laboratorio", "SIG/GPS", "Análisis ambiental", "Trabajo en equipo", "Gestión forestal"),
            salarioInicial = "\$9,500 – \$18,000 MXN/mes",
            demandaLaboral = 74
        ),

        Carrera(
            id = "iias",
            nombre = "Ingeniería en Innovación Agrícola Sustentable",
            abreviacion = "IIAS",
            emoji = "🌾",
            colorHex = "#F57F17",   // Ámbar campo
            descripcionCorta = "Aplica tecnología e innovación para transformar la producción agrícola de la región.",
            descripcionLarga = "Una carrera diseñada para la realidad de Veracruz y las zonas rurales de México. " +
                    "Aprenderás a combinar técnicas agronómicas modernas con tecnología e innovación para " +
                    "mejorar la productividad del campo respetando el entorno. Desde 2011 en el ITSZ.",
            objetivo = "Formar profesionistas analíticos y críticos, comprometidos socialmente y con sólida cultura " +
                    "científico-tecnológica, que planeen el desarrollo regional en el contexto de la innovación agrícola sustentable.",
            duracion = "9 semestres (4.5 años)",
            modalidad = "Presencial • Modelo Dual",
            anioApertura = 2011,
            campoLaboral = listOf(
                "SAGARPA / SADER",
                "Empresas agroindustriales",
                "Consultor agrícola independiente",
                "Proyectos de agricultura de precisión",
                "Organismos de desarrollo rural",
                "Investigación agropecuaria"
            ),
            materiasClave = listOf(
                "Agronomía General",
                "Nutrición Vegetal",
                "Agricultura de Precisión",
                "Biotecnología Agrícola",
                "Gestión Agroempresarial",
                "Sistemas de Riego"
            ),
            habilidades = listOf("Trabajo de campo", "Innovación tecnológica", "Análisis de suelos", "Gestión de proyectos", "Sustentabilidad"),
            salarioInicial = "\$9,000 – \$17,000 MXN/mes",
            demandaLaboral = 78
        )
    )

    // ── Datos institucionales del ITSZ ──
    const val NOMBRE_COMPLETO = "Instituto Tecnológico Superior de Zongolica"
    const val ABREVIACION = "ITSZ"
    const val PERTENECE_A = "Tecnológico Nacional de México (TecNM)"
    const val UBICACION = "Km. 4 Carretera a La Compañía S/N, Tepetitlanapa, Zongolica, Ver. C.P. 95005"
    const val TELEFONO = "278 688 0820"
    const val EMAIL_DIRECCION = "dir_dzongolica@tecnm.mx"
    const val SITIO_WEB = "https://zongolica.tecnm.mx"
    const val ANIO_FUNDACION = 2002
    const val MATRICULA_TOTAL = "~2,555 estudiantes (2022)"
    const val REGION = "Altas Montañas, Veracruz"
    const val MODELO_EDUCATIVO = "Educación Dual — práctica profesional desde el 1er semestre"

    // Coordenadas del campus principal
    const val LAT = 18.6636
    const val LNG = -97.0003
}