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

$sql = $conn->prepare("INSERT INTO `chara` (`nombre`, `apellido`, `fechaNacimiento`, `estaEnRelacion`, `especie`, `nacionalidad`, `afiliacion`, `descripcion`, `sexo`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
$sql->bind_param("sssissssi", $data["nombre"], $data["apellido"], $data["fechaNacimiento"], $data["estaEnRelacion"], $data["especie"], $data["nacionalidad"], $data["afiliacion"], $data["descripcion"], $data["sexo"]);
$sql->execute();


echo json_encode(array("status" => "success", "message" => "Creacion de personaje exitoso"));

$sql->close();
$conn->close();
?>