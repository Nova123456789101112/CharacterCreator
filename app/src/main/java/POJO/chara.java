package POJO;

public class chara {

    // Variables de instancia
    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private int estaEnRelacion;
    private String especie;
    private String nacionalidad;
    private String afiliacion;
    private String descripcion;
    private int sexo;

    // Constructor vacío
    public chara() {
    }

    // Constructor con parámetros
    public chara(String nombre, String apellido, String fechaNacimiento, int estaEnRelacion, String especie, String nacionalidad, String afiliacion, String descripcion, int sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estaEnRelacion = estaEnRelacion;
        this.nacionalidad = nacionalidad;
        this.especie = especie;
        this.sexo = sexo;
        this.afiliacion = afiliacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int isEstaEnUnaRelacion() {
        return estaEnRelacion;
    }

    public void setEstaEnUnaRelacion(int estaEnUnaRelacion) {
        this.estaEnRelacion = estaEnUnaRelacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public void setAfiliacion(String afiliacion) {
        this.afiliacion = afiliacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
