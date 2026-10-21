# HUMAN'T — reglas operativas

Proyecto universitario Android PAMN protagonizado por Phill. Nombre oficial HUMAN'T, técnico HUMANT; paquetes `humant`. Nunca HUMANT'T. Los requisitos oficiales del profesor prevalecen; todavía no se han recibido.

- Leer README y docs/GDD.md, ARCHITECTURE.md, ART_GUIDE.md y ROADMAP.md. Actualizar documentación junto al código, distinguiendo CONFIRMADO, PROPUESTA y PENDIENTE.
- Un módulo :app, MVVM: UI observa ViewModels, Domain contiene reglas puras, Data persistencia/contenido, Core tiempo compartido. No reglas económicas en Compose ni MainActivity.
- Inspeccionar configuración antes de modificarla; no actualizar versiones por rutina. Extender mediante IDs estables y definiciones, nunca nombres visibles ni índices. Evitar clases gigantes, archivos vacíos y abstracciones innecesarias.
- Sin aprobación, no cambiar: saldo PH gastable e históricos no decrecientes por compras; bonus de objetos comprados independiente de equipamiento; automatización solo activa, incluso en bosses/penalizaciones; ninguna producción offline.
- Todos los bosses empiezan con PH manuales contrarreloj; automáticos no cuentan al objetivo; manuales sí suman saldo/histórico. Ocultar fases no descubiertas. Derrota solo implica cooldown activo, nunca pérdidas ni bloqueo de clics.
- Eventos automáticos separados de bosses: clics del desafío sin PH, éxito sin premios PH/multiplicadores. Fallo puede bloquear solo generación manual. Penalización/cooldown guardan restante y solo consumen tiempo activo.
- Prestigio reinicia partida, conserva permanentes; acreditar una vez mediante transacción. Confirmación explícita mostrando pérdidas/conservación. Árbol ampliable con ramas, prerrequisitos y compras únicas.
- Respetar diseño aprobado de Phill y pixel art adulto e irreverente; placeholders hasta recibir recursos. No convertir propuestas de economía en decisiones definitivas.
- Kotlin legible, enteros exactos, transacciones serializadas, errores explícitos, validación de contenido/versiones. Probar reglas implementadas y ejecutar assembleDebug y comprobaciones pertinentes; informar lo no ejecutado.
- No borrar existentes, no force push, no commits/push sin orden. Excluir locales, compilación, claves y logs; conservar fuentes, documentación, recursos, catálogos y Wrapper.
