<?php
$usu_usuario=$_POST['usuario'];
$usu_password=$_POST['password'];

$hostname='localhost';
$database='id15318977_db_inventario';
$username='id15318977_cristianore98';
$password='Ore98$_hola$';

$conexion=new mysqli($hostname,$username,$password,$database);


if($conexion->connect_errno){
    echo "El sitio web está experimentado problemas";
}


//$usu_usuario="cristian@gmail.com";
//$usu_password="123";

$sentencia=$conexion->prepare("SELECT * FROM tb_usuario WHERE correo=? AND clave=?");
$sentencia->bind_param('ss',$usu_usuario,$usu_password);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$conexion->close();



        
        
    