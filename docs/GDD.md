# HUMAN'T — documento maestro de diseño

## Convenciones y alcance

**CONFIRMADO**: requisito; **PROPUESTA**: diseño ajustable; **PENDIENTE**: decisión abierta. Todas las cifras económicas, objetivos, multiplicadores y duraciones son provisionales. Este documento describe la visión completa, no funciones ya implementadas. Los requisitos oficiales de PAMN tendrán prioridad; falta recibirlos.

## Identidad y concepto

CONFIRMADO: nombre HUMAN'T; técnico HUMANT, paquetes `humant`. Android, Kotlin, MVVM con UI/Domain/Data; Compose elegido para esta base. Clicker incremental con personalización, minijuegos, bosses, eventos y prestigio. Phill es un extraterrestre que llega a la Tierra y quiere convertirse en humano funcional: aprende conductas, adquiere habilidades y ropa, absorbe cerebros para automatizar aprendizaje. Identificación, empleo, Hacienda y alquiler se convierten en combates épicos. Humor adulto, macarra, sarcástico, absurdo e irreverente. Pixel art, sin estética infantil ni apariencia de aplicación financiera con botones.

CONFIRMADO: escalabilidad de bosses/fases/mecánicas, eventos, objetos/ropa/accesorios, habilidades, automatizaciones, etapas, ramas/nodos, logros y estados/animaciones. Separar modelos/estado, motores, definiciones, persistencia, ViewModels, UI y recursos. IDs estables, nunca relaciones por nombre visible o posición. Contenido nuevo con mecánicas existentes debe requerir principalmente datos, recursos y diálogos; mecánica nueva admite implementación registrada sin reescribir las demás. Solución sencilla universitaria, un módulo inicial.

## PH y economía

CONFIRMADO: pulsar Phill genera Puntos de Humanidad. Saldo disponible aumenta al obtener PH y disminuye al comprar. Históricos de partida cuentan todo lo obtenido desde último prestigio, nunca disminuyen por compras; desbloqueos usan históricos. Ejemplo: obtener 1.000 y gastar 750 deja 250 disponibles y 1.000 históricos. Cálculo centralizado, independiente de UI; no saldos flotantes.

Base técnica: BigInteger exacto, serializado decimal, sin desbordamiento de Long. Producción fraccionaria futura requiere acumulador entero/residuo persistido; porcentajes/precios con racionales y redondeo explícito. PENDIENTE formato abreviado y límites operativos de tamaño para cifras extremas.

### Habilidades

CONFIRMADO: cada habilidad única se compra una vez por partida, aumenta PH/clic. Catálogo PROPUESTO:

|Habilidad|Precio PH|Bonus PH/clic|
|---|---:|---:|
|Aprender a saludar|25|1|
|Sonrisa de psicópata|125|3|
|Hablar del tiempo|450|10|
|Mentir en el currículum|1.800|25|
|Fingir que trabajas|7.000|75|
|Entender el IRPF|22.000|200|
|Mandar audios de 8 minutos|85.000|600|
|Convertirse en cuñado|250.000|1.500|

PROPUESTA: mejora repetible «Ensayo de humanidad», precio inicial 25, +1 PH/clic por nivel, crecimiento 1,65. PENDIENTE máximo, precio y redondeo. No implementar ilimitada sin estudiar impacto: prestigio debe ser necesario en determinados muros.

### Automatizaciones

CONFIRMADO: cerebros generan PH/s solo en primer plano activo, incluso durante combates/penalizaciones. Cierre o segundo plano pausa. Sin producción offline ni dependencia de recomposiciones. Varias unidades por tipo. Catálogo PROPUESTO:

|Automatización|Precio base|PH/s|
|---|---:|---:|
|Cerebro de becario|300|1|
|Camarero explotado|1.500|4|
|Oficinista muerto por dentro|6.500|18|
|Departamento de RR. HH.|30.000|75|
|Comunidad de vecinos|110.000|240|
|Granja de influencers|350.000|750|

PROPUESTA: siguiente precio = ceil(base × 1,25^unidades), implementable exactamente como ceil(base × 5^n / 4^n). PENDIENTE interacción con descuentos/redondeo.

## Evolución

CONFIRMADO: evolución por etapa separada de accesorios comprados/equipados. Permanente en partida, reinicia al prestigiar. Requisitos configurables por datos. Etapas PROPUESTAS:

|Etapa|Requisito|Descripción|
|---|---|---|
|1 Bicho espacial|Inicial|Recién llegado, desconoce costumbres|
|2 Humano sospechoso|1.000 históricos + boss 1|Parece humano, sigue siendo sospechoso|
|3 Ciudadano de mierda|10.000 históricos + boss 2|Trabajo e integración|
|4 Humano certificado|100.000 históricos + boss 3|Se considera humano oficialmente|

Boss final propuesto a 500.000 históricos, posterior a etapa 4.

## Objetos e inventario

CONFIRMADO: objetos cuestan PH, permanecen en inventario durante partida, equipables/desequipables. Equipar modifica apariencia, pero bonus de todos los comprados permanece aunque se desequipen. Comprar otro del mismo tipo no destruye anterior. Categorías/ranuras ampliables e IDs de sprite independientes de economía; composición por capas. Categorías PROPUESTAS: cabeza/cara, torso, piernas, accesorios. PENDIENTE compatibilidades de ranura y política de recompra; no asumir apilado del mismo objeto.

|Objeto propuesto|Coste PH|Bonus PH/clic|
|---|---:|---:|
|Bigote del bazar|75|1|
|Camiseta con inglés mal escrito|200|3|
|Gafas de chulo|650|6|
|Corbata de divorciado|2.000|14|
|Chándal de gimnasio sin gimnasio|6.000|40|
|Móvil con pantalla rota|18.000|95|
|Rolex de criptobro|45.000|220|
|Traje de empresario arruinado|120.000|500|
|Riñonera de especulador|350.000|1.250|

## Bosses: reglas confirmadas

1. Acceso a determinados bosses por históricos; avanzar etapa requiere victoria correspondiente.
2. Todos empiezan por generar una cantidad concreta de PH manuales contrarreloj.
3. Solo PH de clic manual durante esa prueba cuentan al objetivo. Automatizaciones siguen sumando saldo/histórico, pero no objetivo.
4. PH manuales en boss sí suman saldo e históricos normales.
5. Fases posteriores ocultas hasta superar anteriores. Recordar fases descubiertas; catálogo interno puede contener todas, pero UI/descripciones no las revelan.
6. Fases posteriores admiten preguntas, decisiones, clasificación, contrarreloj y ataques especiales; combinar arcade, estrategia y reflejos, no solo velocidad de clic.
7. Fallar nunca resta PH/compras/inventario, nunca bloquea generación manual ni automática. Única consecuencia: cooldown de reintento de ese boss.
8. Cooldown mide tiempo activo y persiste restante al salir. Soportar distinto número de fases y mecánicas reutilizables.

PROPUESTA: conservar descubrimientos incluso tras prestigio. PENDIENTE aprobar esa permanencia, objetivos manuales/tiempos, cooldowns, respuestas, diálogos y diseño final de fases. Las fases 2+ siguientes son documentación interna, nunca texto para jugadores que no las descubrieron.

### Funcionario de extranjería

PROPUESTA: requisito 1.000 históricos. Conseguir documento de identidad humano. Fase 1 PH manuales contrarreloj; después documentación correcta, preguntas absurdas sobre humanidad y demostrar comportamientos humanos. Ataque «Falta una fotocopia». Victoria: etapa 2 y DNI como recuerdo.

### Directora de Recursos Humanos

PROPUESTA: 10.000 históricos. Primer trabajo. Fase 1 PH manuales contrarreloj; después entrevista con trampas, respuestas bajo presión y negociación salarial. Ataque «Aquí somos una familia». Victoria: etapa 3 y contrato de trabajo.

### Inspector de Hacienda

PROPUESTA: 100.000 históricos. Demostrar cumplimiento fiscal. Fase 1 PH manuales contrarreloj; después clasificar ingresos/gastos, gestionar documentos y auditoría de decisiones rápidas. Ataque «Documentación adicional». Victoria: etapa 4 y certificado oficial.

### El Casero

PROPUESTA: 500.000 históricos, boss final. Tras aprender a ser humano necesita vivienda. Fase 1 gran prueba de PH manuales contrarreloj; después visita, nóminas/avales, negociación con otros inquilinos y firma con cláusulas absurdas. Ataque «Hay otro interesado». Victoria: final narrativo. Muro de dificultad que anime a prestigiar, equilibrio pendiente. Fases posteriores siempre ocultas hasta descubrimiento.

## Eventos aleatorios

CONFIRMADO: distintos de bosses, aparecen automáticamente sin aceptación voluntaria para mantener atención. Principalmente número de clics dentro de tiempo; sus clics cumplen objetivo pero no generan PH. Éxito nunca otorga PH directos ni multiplicadores. Futuras preguntas/decisiones deben respetarlo. No activar durante boss, otro evento o penalización; no acumular simultáneos.

PROPUESTA: cada 3–6 minutos activos. Ataque de ansiedad social: 20 clics/10 segundos, fallo penaliza clic manual. Inspección de humanidad: 25 clics/12 segundos, fallo penalización temporal. PENDIENTE catálogo final, distribución y tiempos. PROPUESTA: salir de aplicación durante evento implica fallo y penalización al regresar; persistir marcador pendiente para resolver una vez incluso tras muerte abrupta. Requiere aprobación antes de habilitarlo. PENDIENTE política al abandonar combate.

## Penalizaciones y tiempo

CONFIRMADO: fallo de ciertos eventos bloquea temporalmente generación manual, conserva PH/inventario, automatizaciones siguen, tienda/menús accesibles y contador visible. PROPUESTA: 60 segundos.

CONFIRMADO: solo consumir tiempo activo en primer plano; guardar restante, nunca solo hora real de fin. Ejemplo: 60 menos 20 jugando deja 40; cerrar dos horas y regresar conserva 40. Igual para cooldowns. Reloj reutilizable fuera de UI, pruebas con reloj simulado; distinguir tiempo real, activo, combate y restantes.

Política técnica conservadora: baseline monotónico nuevo al reanudar, checkpoints de restante, pausa al salir; tras muerte abrupta recuperar último checkpoint. Puede repetirse una fracción activa no confirmada, nunca descontar offline. Reloj actual probado pero aún no conectado a lifecycle ni producción inexistente.

## Prestigio

CONFIRMADO: Phill abandona identidad y recomienza conservando conocimientos. Reinicia saldo, históricos de partida, habilidades normales, ropa/accesorios, automatizaciones, etapa, bosses derrotados en partida, eventos/combates temporales. Conserva PP disponibles, nodos permanentes, logros, historial de prestigios y descubrimientos aprobados.

PROPUESTA: disponible desde etapa 4. Fórmula PP = floor(sqrt(históricos / 25.000)); 100.000→2, 400.000→4, 625.000→5, 900.000→6. No equilibrada; cálculo reemplazable sin reescribir árbol/guardado, raíz entera exacta. Recompensa añadida una sola vez, transacción conjunta con reinicio, sin duplicación por repetición/carga. Antes de confirmar UI explicará pérdidas y conservación. PENDIENTE política exacta de penalización al prestigiar e identificación idempotente de operación.

### Árbol permanente

CONFIRMADO: no lista plana; ramas, bifurcaciones, costes, prerrequisitos, desbloqueo progresivo, conexiones y representación interactiva ampliables. Nodo único, prerequisitos cumplidos, saldo PP nunca negativo, sobrantes conservados, inversión en varias ramas permitida, carga no desbloquea por accidente. Validar grafo sin ciclos.

|Rama|Nodo propuesto|PP|Beneficio|Requiere|
|---|---|---:|---|---|
|Clic|Dedo mutante|1|+1 PH/clic|—|
|Clic|Reflejos alienígenas|2|+15 % manual|Dedo mutante|
|Clic|Mano de Dios|4|+30 % manual adicional|Reflejos alienígenas|
|Automatización|Cerebro reciclado|1|Inicio con becario|—|
|Automatización|Sinapsis colectiva|2|+15 % automático|Cerebro reciclado|
|Automatización|Colmena neuronal|4|+30 % automático adicional|Sinapsis colectiva|
|Social|Sangre fría|1|+2 segundos eventos|—|
|Social|Mentiroso profesional|2|+3 segundos bosses|Sangre fría|
|Social|Inmune al papeleo|4|+5 segundos adicionales bosses|Mentiroso profesional|
|Economía|Dinero encontrado|1|Inicio con 50 PH|—|
|Economía|Cupón galáctico|2|8 % descuento compras|Dinero encontrado|
|Economía|Plan de acogida|4|Inicio con 500 PH adicionales|Cupón galáctico|

Todos costes y multiplicadores provisionales. PENDIENTE composición aditiva/multiplicativa, redondeo, si PH iniciales cuentan al histórico y cuándo aplicar beneficios de inicio adquiridos durante partida. Facilitar estrategias diferentes: potencia manual o facilidad en fases posteriores.

## Logros y platino

CONFIRMADO: permanentes, motor reactivo a eventos del juego, sin comprobaciones dispersas en MainActivity; soportar ocultos. Recompensar bosses, etapas, objetos, eventos, secretos, prestigios, ramas, hitos PH y terminar juego. Platino = completar 100 %. PROPUESTA nombres: Mi primera crisis, Currículum de mierda, Hacienda somos todos, Terapia de choque, Humano certificado. PENDIENTE lista definitiva y criterio exacto de 100 %, no implementar sin aprobación.

## Arte y sprites

CONFIRMADO: concepto de Phill aprobado, no inventar otro. Verde pálido, cabeza grande, dos antenas pequeñas, ojos negros enormes caídos/expresivos, cansancio y resaca, delgado/encorvado. Camisa blanca arrugada, corbata roja mal puesta, pantalones oscuros anchos, zapatillas gastadas, cigarrillo. Guarro, canalla, sarcástico y exagerado. Original pendiente de entrega: placeholder sustituible.

Capas: cuerpo base, evolución, torso, piernas, cabeza, accesorios adicionales, expresiones/efectos. IDs de sprite independientes de economía; pixel art sin suavizados. PENDIENTE dimensiones, anclajes, paleta y recursos finales; ver ART_GUIDE.

## UI adaptable

CONFIRMADO: teléfonos principalmente verticales; Compose adaptable a tamaño/densidad/aspecto, zonas seguras, botones amplios, texto legible y scroll; sin pantalla rígida. Phill centro visual, menús no lo ocultan permanentemente.

Pantallas modulares previstas: Home (Phill, saldo, PH/clic, PH/s, etapa, accesos); Tienda (habilidades/automatizaciones/objetos); Inventario (propiedad/equipo); Bosses (disponible, fases descubiertas, combate, cooldown, resultado); Eventos (desafío/progreso/penalización); Prestigio (reinicio/árbol/PP/nodos); Logros (normales/secretos protegidos/platino). No todas funcionales en primera iteración.

## Guardado y calidad

CONFIRMADO: versión de esquema; guardar saldo/histórico, habilidades, automatizaciones/cantidades, inventario/equipo, etapa, bosses derrotados/fases/cooldowns, penalización, PP/nodos/historial y logros. Migraciones y nuevos IDs sin romper partidas. Transacciones de compra/prestigio sin duplicados/negativos. No Room por defecto; DataStore o alternativa justificada. Corte actual: archivo atómico versionado de dos contadores, no persistencia de sistemas aún inexistentes.

Código legible, responsabilidades separadas, Kotlin idiomático, validación de catálogos/errores y pruebas. Incorporar progresivamente pruebas de compras suficientes/insuficientes, históricos, bonus, automatización/precio, boss/cooldown, evento/penalización, pausa, prestigio/permanentes/prerrequisitos y guardado/carga. Git: no borrar existentes, commits/push solo expresos, no force push; excluir build/cachés/locales/temporales/APK/AAB/claves/logs, preservar fuentes/docs/AGENTS/catálogos/recursos/Wrapper/Gradle.
