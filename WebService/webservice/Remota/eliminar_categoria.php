<?php
include('main_class.php');

if (isset($_POST["id_categoria"])){
 	$id_categoria = $_POST['id_categoria'];
	$resultado = Mantenimiento::eliminar_Categoria($id_categoria);
	
	if ($resultado==1) {
        header('Content-type: application/json; charset=utf-8');
        $json_string = json_encode(array("estado" => 1,"mensaje" => "Eliminado correctamente."));
        echo $json_string;
        
       // $json_string = json_encode(array('estado' => '1','mensaje' => 'Actualizaci杌妌 aplicada correctamente.'));
        //echo json_encode($json_string, JSON_UNESCAPED_UNICODE);
		
    } else {
        header('Content-type: application/json; charset=utf-8');
        $json_string = json_encode(array("estado" => 2,"mensaje" => "No hay informacion que eliminar."));
		echo $json_string;
    }
}

?>