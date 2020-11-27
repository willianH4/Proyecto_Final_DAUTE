<?php
include 'connection_db1.php';

$query = "SELECT id, nombre, apellido, correo, usuario, clave, tipo, estado, pregunta, respuesta, fecha_registro FROM tb_usuario order by id DESC";
try {
    //code...
    $link=conexion();
    $comando= $link->prepare($query);
    //Ejecutar Sentencia preparada
    $comando->execute();
    $categorias = array();
    while ($temp = $comando->fetch(PDO::FETCH_ASSOC)) {
        # code...
        $temp['id'];
        $temp['nombre'];
        $temp['apellido'];
        $temp['correo'];
        $temp['usuario'];
        $temp['clave'];
        $temp['tipo'];
        $temp['estado'];
        $temp['pregunta'];
        $temp['respuesta'];
        $temp['fecha_registro'];

        array_push($categorias, $temp);

        $datos[] = array_map("utf8_encode", $temp);
        header('Content-type: application/json; cahrset=utf-8');
    }
    echo json_encode($datos, JSON_UNESCAPED_UNICODE);
} catch (\Throwable $th) {
    //throw $th;
    return false;
}
