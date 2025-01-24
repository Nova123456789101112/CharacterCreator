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
  echo json_encode(array("status" => "error", "message" => "Faltan datos requeridos"));
  exit;
}

$username = $data["username"];
$password = $data["password"];

$sql = $conn->prepare("SELECT * FROM usuarios WHERE username = ?");
$sql->bind_param("s", $username);
$sql->execute();
$resultado = $sql->get_result();

if ($resultado->num_rows > 0) {
  die(json_encode(array("status" => "error", "message" => "Usuario ya registrado")));
}

$sql = $conn->prepare("INSERT INTO usuarios(username,password) VALUES(?,?)");
$sql->bind_param("ss", $username,$password);
$sql->execute();

echo json_encode(array("status" => "success", "message" => "Creacion de usuario exitoso"));

$sql->close();
$conn->close();
?>