<?php
include 'connection_db1.php';

$query = "SELECT id_producto,nom_producto,des_producto,stock,precio,unidad_de_medida,estado_producto,categoria,fecha_entrada FROM tb_producto order by id_producto DESC";
try {
    //code...
    $link=conexion();
    $comando= $link->prepare($query);
    //Ejecutar Sentencia preparada
    $comando->execute();
    $categorias = array();
    while ($temp = $comando->fetch(PDO::FETCH_ASSOC)) {
        # code...
        $temp['id_producto'];
        $temp['nom_producto'];
        $temp['des_producto'];
        $temp['stock'];
        $temp['precio'];
        $temp['unidad_de_medida'];
        $temp['estado_producto'];
        $temp['categoria'];
        $temp['fecha_entrada'];

        array_push($categorias, $temp);

        $datos[] = array_map("utf8_encode", $temp);
        header('Content-type: application/json; cahrset=utf-8');
    }
    echo json_encode($datos, JSON_UNESCAPED_UNICODE);
} catch (\Throwable $th) {
    //throw $th;
    return false;
}
