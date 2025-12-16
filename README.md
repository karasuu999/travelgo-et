# TravelGo 📱✈️

Proyecto Semestral – Examen Transversal
Asignatura: DSY1105 – Desarrollo de Aplicaciones Móviles    
Docente: Roberto Arce    

👥 Integrantes
- Daniel Castro  
- Bruno Ratto  



📌 Descripción de la App

TravelGo es una aplicación móvil Android desarrollada en **Kotlin**, orientada a la gestión y visualización de información relacionada con viajes y usuarios.
La aplicación integra consumo de servicios REST, persistencia local, gestión de estado reactiva y uso de recursos nativos del dispositivo, siguiendo la arquitectura **MVVM (Model – View – ViewModel)**.

⚙️ Funcionalidades Principales

- Visualización de información de usuario desde una **API REST**.
- Gestión de perfil de usuario.
- Navegación entre pantallas mediante **Navigation Compose**.
- Manejo de estados de carga, éxito y error en la interfaz.
- Consumo de servicios externos mediante **Retrofit**.
- Uso de **cámara** y **galería** del dispositivo.
- Persistencia local de información mediante **DataStore**.
- Interfaz moderna basada en **Material Design 3**.

🌐 Endpoints Utilizados
🔹 API REST Externa

Los siguientes endpoints son consumidos desde la aplicación móvil:

- `GET /users` – Obtención de información de usuario.
- `POST /login` – Autenticación de usuario.
- `PUT /users/{id}` – Actualización de datos de usuario.
- `DELETE /users/{id}` – Eliminación de usuario.
Estos endpoints corresponden a una API REST externa, consumida mediante Retrofit y OkHttp.

🔹 Microservicios Propios

Actualmente, el proyecto no incluye microservicios propios desarrollados dentro de este repositorio.  
La integración se realiza mediante consumo de servicios REST externos, lo cual es demostrado durante la ejecución y defensa del proyecto.


▶️ Instrucciones para Ejecutar el Proyecto

🔧 Requisitos Previos

- Android Studio (versión Flamingo o superior).
- JDK 17 o compatible.
- Emulador Android o dispositivo físico.

▶️ Pasos de Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/karasuu999/travelgo-et.git
   
2. Abrir el proyecto en Android Studio.

3. Esperar la sincronización de dependencias con Gradle.

4. Ejecutar la aplicación en un emulador o dispositivo físico.

5. Otorgar los permisos solicitados (cámara y almacenamiento).


📦 APK Firmado y Archivo .jks

El proyecto contempla la generación de un APK firmado para su instalación en dispositivos reales.
El archivo .jks no se incluye en el repositorio por razones de seguridad.
El .jks se genera y almacena de forma local en el equipo del desarrollador.
El APK firmado se genera desde Android Studio utilizando la opción:
Build → Generate Signed Bundle / APK

💻 Código Fuente
📱 Aplicación Móvil

- El código fuente completo de la aplicación móvil Android se encuentra en este repositorio.
- Incluye arquitectura MVVM, consumo de API REST, persistencia local y uso de recursos nativos.

🌐 Microservicios

- Este repositorio no contiene código fuente de microservicios propios.
- La integración se realiza mediante servicios REST externos.

🤝 Trabajo Colaborativo
El desarrollo del proyecto se realizó de forma colaborativa:

- Repositorio GitHub público.
- Uso de control de versiones mediante Git.
- Commits realizados por Daniel Castro.
- Evidencia de participación individual visible en el historial de commits del repositorio.

🛠️ Tecnologías Utilizadas

🎨 Frontend

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- Navigation Compose

🌐 Backend / Datos

- API REST
- Retrofit
- OkHttp

🔧 Herramientas

- GitHub
- Emulador Android Studio

✅ Conclusión

TravelGo es una aplicación móvil Android funcional que integra arquitectura MVVM, diseño moderno con Material Design 3, consumo de servicios REST, persistencia local y uso de recursos nativos del dispositivo.
