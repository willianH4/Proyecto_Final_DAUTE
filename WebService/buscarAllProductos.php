<?php
//Testeado desde Postman
//Retorna todos los registros de la tabla productos
//usada para llenar el recyclerview y Listview

$hostname_localhost ="localhost";
$database_localhost ="bd_inventario";
$username_localhost ="root";
$password_localhost ="";

$json=array();
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="select id_producto,nom_producto,des_producto,stock,precio,unidad_de_medida,estado_producto,categoria,fecha_entrada from tb_producto";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['productos'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
        echo json_encode($json);
  

/*include("connection_db.php");

    $query = "SELECT id_producto,nom_producto,des_producto,stock,precio,unidad_medida,estado_producto,categoria,fecha_entrada FROM tb_productos";

        try {
            $link=conexion();    
            $comando = $link->prepare($query);
            // Ejecutar sentencia preparada
            $comando->execute();
            
            $productos = array(); 
           
           while ($temp = $comando->fetch(PDO::FETCH_ASSOC)) {
                $temp['id_producto'];
                $temp['nom_producto'];
                $temp['des_producto'];
                $temp['stock'];
                $temp['precio'];
                $temp['unidad_medida'];
                $temp['estado_producto'];
                $temp['categoria'];
                //$temp['fecha_entrada'];
                
                array_push($productos, $temp);
		
    			$datos[] = array_map("utf8_encode", $temp);
      	        header('Content-type: application/json; charset=utf-8');
             }
             
             echo json_encode($datos, JSON_UNESCAPED_UNICODE);
           
        
        } catch (PDOException $e) {
            return false;
        }

*/

?>