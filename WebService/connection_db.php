<?php
function conexion(){
	$conn = null;
	$host = 'localhost';
	$db = 'bd_inventario';
	$user = 'root';
	$pwd = '';
	
try{
	$conn = new PDO('mysql:host='.$host.'; dbname='.$db,$user,$pwd,array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES \'UTF8\''));  //Me pase a esta conexión por problemas de acentos, letra ñ y otros.
    //echo "Conexion hecha...";
}catch(PDOException $e){
	echo "<center><h2 style='color:green'>Error al tratar de conectar a la BD.";
	echo " consulte al soporte Técnico</h2></center>";
	exit();
}
	return $conn;
}

//conexion();

?>