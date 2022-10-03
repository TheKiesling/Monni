# Monni
El proyecto consiste en una aplicación destinada a la gestión de finanzas. Este tipo de aplicaciones son importantes ya que les facilitan a los usuarios el manejo de tareas como el seguimiento de gastos, facturas y presupuestos. También suelen facilitar la toma de decisiones financieras y a la administración adecuada del dinero. ___Monni___ es una aplicación que busca sumarse a aquellas que les permiten a las personas tener conciencia de gastos para fomentar la adquisición de buenos hábitos financieros. Al igual que muchas otras, es simple de utilizar. Sin embargo, ___Monni___ trae consigo algunos nuevos aspectos que la hacen más cómoda e innovadora. Dentro de estos aspectos cabe destacar la categorización de los gastos, una opción que le brinda al usuario la posibilidad de colocar un límite para cada categoría, tips de ahorro, recordatorio de pagos pendientes, gráfico de gastos para visualizar el porcentaje de cada categoría, un historial de gastos por categoría y finalmente un espacio para que el usuario coloque notas cada vez que ingresa un nuevo gasto.

## Servicios
Únicamente se implementarán dos servicios para el desarollo de la aplicación.
- [The Credential Management API](https://web.dev/security-credential-management/) se utilizará para recordar y autentificar a los usuarios. También servirá para hacer un login automático y para permitir logearse rápida y sencillamente.
- Para los _tips_ de ahorro se tendrá en una base de datos pequeñas frases o recomendaciones que fomenten el hábito de ahorrar. Dichas frases y/o recomendaciones serán de nuestra autoría y se seleccionarán de manera aleatoria cada cierto tiempo para ser presentadas al usuario en el apartado de _Ahorros_ de la aplicación. 

## Librerías

| Librería     | Versión    | Descripción                                               |
|------------  | -------------  |  -------------                                        |
| [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) | 2.5.1 | Navegación entre los fragments de la aplicación bajo el principio de "One Activity, Multiple Fragments". |
| [Coil](https://coil-kt.github.io/coil/) | 2.2.0 | Importar imágenes externas en la aplicación. |
| [Retrofit](https://square.github.io/retrofit/) | 2.9.0 | Acceder a servicios externos por medio de internet. |
| [GSON](https://github.com/google/gson/blob/master/UserGuide.md) | 2.6.4 | Conversión de objetos JSON a Kotlin. |
| [OkHttp](https://futurestud.io/tutorials/retrofit-2-log-requests-and-responses) | 4.9.3 | Permitir el acceso mediante Retrofit. |
| [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=es-419) | 1.0.0 | Almacenamiento de preferencias de usuario (Ej. Cambio de paleta de colores en gráficos). |
| [Coroutines](https://developer.android.com/kotlin/coroutines?hl=es-419&gclid=CjwKCAjw7eSZBhB8EiwA60kCWwrUNhtfAPT9YFdGpHYCzslJjl9MMANZbVu2RFohfVNMfL4KrgC2XRoCMq4QAvD_BwE&gclsrc=aw.ds) | 1.6.4 | Permitir que el almacenamiento local sea asíncrono. |
| [Room](https://developer.android.com/training/data-storage/room) | 2.4.3 | Almacenamiento local de la información del usuario usando el nombre del mismo como llave primaria.|

<br>

| Categoría  | Herramienta a utilizar  |
|---|---|
| IDE | <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=Android-Studio&logoColor=white"/> |
| Lenguaje | <img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=for-the-badge&logo=Kotlin&logoColor=white"/> |
| Base de Datos | <img src="https://img.shields.io/badge/SQLite-003B57.svg?style=for-the-badge&logo=SQLite&logoColor=white"/> |
| Frontend | <img src="https://img.shields.io/badge/Coil-000000.svg?style=for-the-badge&logo=Coil&logoColor=white"/> |
