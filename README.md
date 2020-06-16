# LogginProyect
Sistema de loggueo realizado en Java

Para configurar la conexion con la base de datos, ingresamos las credenciales de acceso de mysql a la clase MySQL/Conexion.java
Ingresamos tambien el puerto en el que se aloja la base de datos y el nombre de la base, en mi caso se llama usuarios, creamos en ella la siguiente tabla

CREATE TABLE Usuarios( <br/>
id_Usuario int(10) not null auto_increment, <br/>
Usuario varchar(100) not null, <br/>
Contrasena varchar(100) not null, <br/>
Primary Key(id_Usuario) <br/>
);
