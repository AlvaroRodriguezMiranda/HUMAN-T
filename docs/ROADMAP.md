# Roadmap

1. **Arquitectura/documentación:** base creada; revisar diseño y obtener requisitos oficiales PAMN. Un módulo, capas y Home provisional.
2. **Motor PH:** mínimo +1, saldo/histórico e enteros exactos creado. Siguiente: efectos/porcentajes/redondeo, orígenes manual/automático/evento y formateo.
3. **Tienda/automatizaciones:** pendientes catálogos validados, compras, precios racionales, producción con residuos y loop activo independiente de UI. Depende de guardado transaccional y lifecycle; equilibrar repetible antes de habilitarla.
4. **Guardado:** mínimo versionado/atómico creado. Ampliar antes de sistemas complejos: Run/Permanent, migraciones, concurrencia, fallos IO, checkpoints y pruebas Android.
5. **Inventario/equipamiento:** propiedad independiente de apariencia, bonus por compra, ranuras extensibles y capas. Depende de tienda/guardado.
6. **Eventos:** scheduler exclusivo, desafíos sin PH, penalización manual y persistencia; depende de tiempo/lifecycle probado. Aprobar política de abandono y duraciones.
7. **Bosses:** implementar primero uno completo con handlers, PH manuales iniciales, fases ocultas, evolución y cooldown activo; después reutilizar para el resto. Resolver abandono y permanencia de descubrimientos.
8. **Prestigio:** Run/Permanent, fórmula exacta reemplazable, reinicio atómico/idempotente, confirmación, grafo y árbol interactivo. Equilibrar muro final y PP; aprobar composición porcentual.
9. **UI definitiva:** navegación y pantallas modulares, adaptación/fuentes/accesibilidad, Phill central. Prototipos acompañan cada fase previa.
10. **Gráficos/animaciones/logros/equilibrado:** incorporar concepto aprobado, sprites por capas, aprobar lista de logros/platino y motor reactivo, secretos protegidos; probar progresión/prestigio/rendimiento y dispositivos.

No hay fechas comprometidas. Cada fase requiere pruebas pertinentes, assembleDebug y lint; interacción/lifecycle instrumentados al disponer de dispositivo. Un modelo no significa mecánica implementada.
