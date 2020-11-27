<?php

$hostname_localhost ="localhost";
$database_localhost ="bd_inventario";
$username_localhost ="root";
$password_localhost ="";

$json=array();
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="SELECT id, nombre, apellido, correo, usuario, clave, tipo, estado, pregunta, respuesta, fecha_registro FROM tb_usuario order by id DESC";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['usuarios'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
        echo json_encode($json);


