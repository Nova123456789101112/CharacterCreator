<?php
header("Content-Type: application/json; charset=UTF-8");

// Conexión a la base de datos
$host = "localhost";
$usuario = "root";
$contrasena = "";
$base_de_datos = "usuarios_db";
$conn = new mysqli($host, $usuario, $contrasena, $base_de_datos);

$data = json_decode(file_get_contents("php://input"), true);

if ($conn->connect_error) {
  http_response_code(500);
  die(json_encode(array("status" => "error", "message" => "No se pudo conectar a la base de datos")));
}

$sql = $conn->prepare("SELECT * FROM chara WHERE idUsuario = ?");
$sql->bind_param("i", $data["idUsuario"]);
$sql->execute();
$resultado = $sql->get_result()->fetch_all(MYSQLI_ASSOC);

echo json_encode(array("status" => "success", "message" => $resultado));

$sql->close();
$conn->close();
?>