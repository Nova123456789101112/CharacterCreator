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

if (!isset($data["username"]) || !isset($data["password"])) {
  http_response_code(400);
  die(json_encode(array("status" => "error", "message" => "Faltan datos requeridos")));
}

$username = $data["username"];
$password = $data["password"];

$sql = $conn->prepare("SELECT * FROM usuarios WHERE username = ?");
$sql->bind_param("s", $username);
$sql->execute();
$resultado = $sql->get_result();

if ($resultado->num_rows != 1) {
  die(json_encode(array("status" => "error", "message" => "Usuario no existe/no unico")));
}

$sql = $conn->prepare("UPDATE `usuarios` SET `password` = ? WHERE `usuarios`.`username` = ?");
$sql->bind_param("ss", $password, $username);
$sql->execute();

echo json_encode(array("status" => "success", "message" => "Cambios guardados correctamente"));

$sql->close();
$conn->close();
?>