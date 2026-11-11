<p align="center">
  <img src="docs/screenshot.jpeg" alt="Tabla de Tiempos" width="860">
</p>

<h1 align="center">Tabla de Tiempos</h1>

<p align="center">
  Registra, compara y comparte los tiempos de vuelta de tus pilotos.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.5.6-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/H2-embedded-blue?style=flat-square" alt="H2">
  <img src="https://img.shields.io/badge/Bootstrap-5-7952B3?style=flat-square&logo=bootstrap&logoColor=white" alt="Bootstrap">
</p>

<p align="center">
  <a href="https://github.com/adrianllorian/TablaTiempos/releases/latest/download/tabla-tiempos.jar">
    <img src="https://img.shields.io/badge/Descargar_JAR-2ea44f?style=for-the-badge&logo=java&logoColor=white" alt="Descargar JAR">
  </a>
</p>

<p align="center">
  <strong>Sin base de datos externa · Se abre solo en el navegador · Listo para usar</strong>
</p>

---

## Características

- **Primer arranque**: crea el administrador y, si quieres, el **título**, el
  **logo** y la **foto de fondo** de la aplicación.
- **Ajustes**: el administrador puede cambiar el título, el logo y el fondo
  cuando quiera.
- **Administrador**: crea, edita y borra circuitos; inscribe, edita y borra
  pilotos; puede editar y borrar cualquier tiempo.
- **Pilotos**: inician sesión, registran y editan sus tiempos. No pueden borrar.
- Tabla ordenada con la diferencia respecto al primer puesto.
- **Filtros automáticos**: al cambiar piloto, coche o circuito la tabla se
  actualiza sola.
- Foto opcional asociada a cada tiempo.
- **H2 embebido**: la base de datos se crea sola, sin instalar MySQL.
- Diseño responsive con Bootstrap.

## Descargar y ejecutar

1. Descarga `tabla-tiempos.jar` desde **Releases**.
2. Necesitas **Java 17 o superior** (<https://adoptium.net>).
3. Haz doble clic en el JAR, o ejecútalo desde una consola:

   ```bash
   java -jar tabla-tiempos.jar
   ```

4. El navegador se abre solo. Si no se abre, entra en <http://localhost:8080>.

> La aplicación crea una carpeta **`data/` junto al JAR**, da igual desde qué
> carpeta lo ejecutes. Ahí van la base de datos y las fotos. Usa una carpeta
> donde puedas escribir, no `Program Files`.

En Windows también puedes usar `start.bat` y en Linux/macOS `start.sh`.

### Configuración inicial

La primera vez verás la pantalla de **Configuración inicial**:

- Usuario y contraseña del administrador.
- Título de la aplicación (opcional).
- Logo y fondo (opcionales).

Después, entra con el administrador y prepara la aplicación:

- **Circuitos** → crear, editar y borrar circuitos.
- **Piloto** → inscribir pilotos.
- **Ajustes** → cambiar título, logo o fondo.

Cada piloto inicia sesión con su cuenta y registra tiempos con **Tiempo**.

### ¿Y si quiero empezar de cero?

Con la aplicación parada, borra la carpeta **`data/`** que está junto al JAR.
En el siguiente arranque volverá a pedir crear el administrador.

## Roles

| Acción | Administrador | Piloto |
|---|---|---|
| Crear, editar y borrar circuitos | ✅ | ❌ |
| Inscribir, editar y borrar pilotos | ✅ | ❌ |
| Registrar tiempos | ✅ | ✅ |
| Editar sus propios tiempos | ✅ | ✅ |
| Editar tiempos de otros | ✅ | ❌ |
| Borrar tiempos | ✅ | ❌ |
| Consultar y filtrar tiempos | ✅ | ✅ |
| Cambiar su propia contraseña | ✅ | ✅ |
| Cambiar título, logo y fondo | ✅ | ❌ |

> Un circuito o un piloto **no se pueden borrar** si tienen tiempos
> registrados. Tampoco se puede borrar una cuenta de administrador.

## Primeros pasos recomendados

1. Crear el administrador y elegir el título/logo/fondo en el primer arranque.
2. Crear los circuitos desde **Circuitos**.
3. Inscribir a los pilotos desde **Piloto**.
4. Cada piloto entra con su usuario y añade sus tiempos.
5. Selecciona un circuito en **Filtros** y la tabla se actualiza sola.

## Datos y copias de seguridad

La aplicación crea una carpeta **`data/` junto al JAR** y guarda ahí todo lo
que necesita:

- Base de datos: `data/tabla-tiempo.mv.db`
- Fotos de los tiempos: `data/uploads/`
- Logo y fondo: `data/uploads/settings/`

Se crea sola en el primer arranque, da igual desde qué carpeta ejecutes el
JAR: siempre usa el `data/` que está a su lado.

- **Para mover la aplicación sin perder nada**: mueve el JAR **junto con su
  carpeta `data/`**.
- **Si mueves solo el JAR**: empieza de cero, como si formatearas. La carpeta
  `data/` vieja se queda donde estaba.
- **Copia de seguridad**: con la aplicación parada, copia la carpeta `data/`
  completa.
- **Reset**: con la aplicación parada, borra la carpeta `data/`.
- **Ubicación**: usa una carpeta donde puedas escribir (Escritorio,
  Documentos, un USB...). En `Program Files` no se puede crear `data/`.

## Configuración

| Variable | Descripción | Por defecto |
|---|---|---|
| `SERVER_PORT` | Puerto HTTP | `8080` |
| `ABRIR_NAVEGADOR` | Abrir el navegador al arrancar | `true` |
| `JWT_SECRET` | Clave para firmar los tokens (opcional) | se genera sola |
| `MAX_FILE_SIZE` | Tamaño máximo de foto | `10MB` |

> No hay que configurar nada: cada instalación genera su propia clave JWT y la
> guarda en la base de datos. Solo si quieres fijarla, define `JWT_SECRET`.

> El logo y el fondo admiten imágenes de hasta **2 MB**.

Ejemplo en Windows:

```bat
set SERVER_PORT=9090
java -jar tabla-tiempos.jar
```

En Linux/macOS:

```bash
SERVER_PORT=9090 java -jar tabla-tiempos.jar
```

## Compilar desde el código

Requisitos: **JDK 17+** y el Maven Wrapper incluido.

```bash
./mvnw clean package
```

En Windows:

```bat
mvnw.cmd clean package
```

El JAR se genera en `target/tabla-tiempos.jar`.

## Publicar una release

Las releases se publican a mano, para adjuntar siempre el fichero que se ha
probado:

1. Compila el JAR (apartado anterior).
2. En GitHub, entra en **Releases → Draft a new release**.
3. Crea el tag de la versión (por ejemplo `v1.4.0`) y adjunta
   `tabla-tiempos.jar`.
4. Publica la release.

El workflow `build.yml` solo comprueba que el proyecto compila en cada push y
pull request; no publica nada.

## Tecnologías

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 2.5.6 |
| Seguridad | Spring Security + JWT + BCrypt |
| Persistencia | Spring Data JPA + H2 |
| Vistas | Thymeleaf |
| Frontend | Bootstrap 5 + jQuery |

## Seguridad

- Pensada para usarse en local o en una red de confianza.
- Si la vas a exponer a Internet, ponla detrás de un proxy con HTTPS y cambia
  la contraseña del administrador.
- Las contraseñas se guardan cifradas con BCrypt.

## Licencia

MIT. Consulta [LICENSE](LICENSE).
