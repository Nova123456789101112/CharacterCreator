<?php
header("Content-Type: application/json; charset=UTF-8");

// Conexión a la base de datos
$host = "localhost";
$usuario = "root";
$contrasena = "";
$base_de_datos = "usuarios_db";
$conn = new mysqli($host, $usuario, $contrasena, $base_de_datos);

if ($conn->connect_error) {
  http_response_code(500);
  die(json_encode(array("status" => "error", "message" => "No se pudo conectar a la base de datos")));
}

$data = json_decode(file_get_contents("php://input"), true);

$sql = $conn->prepare("DELETE FROM chara WHERE `chara`.`id` = ?");
$sql->bind_param("i", $data["id"]);
$sql->execute();

echo json_encode(array("status" => "success", "message" => "Personaje eliminado exitosamente"));

$sql->close();
$conn->close();
?>