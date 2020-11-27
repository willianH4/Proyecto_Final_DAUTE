<?php
include('main_class.php');

if (isset($_POST['id_prod']) && ($_POST['nom_prod']) && ($_POST['des_prod']) && ($_POST['stock'])) {
    # code...
    $a = $_POST['id_prod'];
    $b = $_POST['nom_prod'];
    $c = $_POST['des_prod'];
    $d = $_POST['stock'];
    $e = $_POST['precio'];
    $f = $_POST['uni_medida'];
    $g = $_POST['estado_prod'];
    $h = $_POST['categoria'];

    $resultado = Mantenimiento::guardar_producto($a, $b, $c, $d, $e, $f, $g, $h);

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

}else{
    header('Content-type: application/json; charset=utf-8');
    $json_string = json_encode(array("estado" => 3, "mensaje" => "No se ha enviado toda la informacion"));
    echo $json_string; 
}



?>