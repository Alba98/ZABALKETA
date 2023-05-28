# ZABALKETA
Desarrollar una aplicación de smartphone (de sistema Android) para el monitoreo de los
sistemas de captación de agua de niebla existentes en los Valles Cruceños de Bolivia y en
la zona andina de Curahuasi en Perú

## Usuarios

Al abrir la aplicacion se inicia la ventana de inicio de sesion. 
Los usuarios se registran desde la base de datos.


| Nombre | Clave  | Permisos | Region       |
|--------|--------|----------|--------------|
| alba   | 12345  | Usuario  | VELADERO     |
| maider | 12345  | Usuario  | SIVINGALITO  |
| andrea | 12345  | Usuario  | PICUTA       |
| miren  | 12345  | Usuario  | CHOQUEMARCA  |


## Modo de uso

Una vez dentro el appBar mostrara dos iconos, el primero sera para crera una nueva entrada. Mientras que el segundo sirve para cerrar sesion. 

Las cards con los datos tienen en la parte superior la fecha y la zona de la que son, junto con el usuarios. Dejamo de muestra un reumen, mediante unos iconos. Estos iconos se activan dependiendo de si ese dia ha habido niebla, ha llovido o ha habido un corte de luz. Por ultimo se ha habido alguna incidencia ese dia se muestra al final de la card.

Mediante el boton flotante tambien se puede realizar una insercion a la base de datos. 

El formulario de insercion de datos incluye una fecha que es obligatoria y una region, dicha region se obtiene de los datos del usuario. El resto de campos son opcionales. 

Si en la base de datos ya exite una entrada con la fecha, esta se actualizara.

## Sketch

Diseño en figma
https://www.figma.com/file/A5RAd321GaEpnnnyhvRMoH/Zabalketa?type=design&node-id=1-2&t=yK109YDOZfsxU3ED-0

Prototipo en figma
https://www.figma.com/proto/A5RAd321GaEpnnnyhvRMoH/Zabalketa?type=design&node-id=1-2&scaling=scale-down&page-id=0%3A1&starting-point-node-id=1%3A2

Sketch de la BBDD 
- Imagen en el repositorio

## Problemas

Cuando se incia por primera primerisima vez la app a veces crashea, pero no siempre. Pero apartir de entonces no hay mayor problema.