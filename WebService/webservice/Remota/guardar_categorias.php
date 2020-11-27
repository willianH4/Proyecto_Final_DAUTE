<?php
include('main_class.php');

if (isset($_POST['id']) && ($_POST['nombre'])) {
    # code...
    $id_categoria = $_POST['id'];
    $nom_categoria = $_POST['nombre'];
    $estado_categoria = $_POST['estado'];

    $resultado = Mantenimiento::guardar_categoria($id_categoria, $nom_categoria, $estado_categoria);

    if ($resultado==1) {
        # code...
        header('Content-type: application/json; charset=utf-8');
        $json_string = json_encode(array("estado" => 1, "mensaje" => "Registro guardador correctamente."));
        echo $json_string;
    }else{
        header('Content-type: application/json; charset=utf-8');
        $json_string = json_encode(array("estado" => 2, "mensaje" => "Error. No se puede guardar..."));
        echo $json_string;
    }
}
?>
