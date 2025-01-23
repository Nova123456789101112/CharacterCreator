package POJO;

public class chara {

    // Variables de instancia
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private boolean estaEnUnaRelacion;
    private String nacionalidad;
    private String especie;
    private int sexo;
    private String afiliacion;
    private String descripcionBreve;

    // Constructor vacío
    public chara() {
    }

    // Constructor con parámetros
    public chara(String nombre, String apellido, String fechaNacimiento, boolean estaEnUnaRelacion, String nacionalidad, String especie, int sexo, String afiliacion, String descripcionBreve) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estaEnUnaRelacion = estaEnUnaRelacion;
        this.nacionalidad = nacionalidad;
        this.especie = especie;
        this.sexo = sexo;
        this.afiliacion = afiliacion;
        this.descripcionBreve = descripcionBreve;
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

    public boolean isEstaEnUnaRelacion() {
        return estaEnUnaRelacion;
    }

    public void setEstaEnUnaRelacion(boolean estaEnUnaRelacion) {
        this.estaEnUnaRelacion = estaEnUnaRelacion;
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

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }
}
