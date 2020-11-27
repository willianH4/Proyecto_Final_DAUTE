<?php
include('main_class.php');

if (isset($_POST['id']) && ($_POST['correo']) && ($_POST['clave']) && ($_POST['pregunta']) && ($_POST['respuesta'])) {
    # code...
    $a = $_POST['id'];
    $b = $_POST['nombre'];
    $c = $_POST['apellido'];
    $d = $_POST['correo'];
    $e = $_POST['usuario'];
    $f = $_POST['clave'];
    $g = $_POST['tipo'];
    $h = $_POST['estado'];
    $i = $_POST['pregunta'];
    $j = $_POST['respuesta'];
   

    $resultado = Mantenimiento::actualizar_usuario($b, $c, $d, $e, $f, $g, $h, $i, $j, $a);

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
