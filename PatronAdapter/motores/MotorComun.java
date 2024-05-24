package motores;

public class MotorComun implements Motor {
    
   
    public String encender() {
        return "Encendiendo Motor Comun";
    }

   
    public String acelerar() {
        return "Acelerando Motor Comun";
    }

   
    public String apagar() {
        return "Apagando Motor Comun";
    }
}