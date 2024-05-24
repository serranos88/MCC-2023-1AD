package motores;

public class MotorHibridoAdapter implements Motor {
    
    private MotorHibrido motorHibrido;
    
    public MotorHibridoAdapter(){
        this.motorHibrido = new MotorHibrido();
    }
    
    
    public String encender() {
        return motorHibrido.encendiendo();
    }

  
    public String acelerar() {
        return motorHibrido.acelerando();
    }

    
    public String apagar() {
        return motorHibrido.apagando();
    }
}