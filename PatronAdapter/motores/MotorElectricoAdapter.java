package motores;

public class MotorElectricoAdapter implements Motor {
    private MotorElectrico motorElectrico;

    public MotorElectricoAdapter() {
        this.motorElectrico = new MotorElectrico();
    }

  
    public String encender() {
        return this.motorElectrico.conectar() + " y " + this.motorElectrico.activar();

    }

   
    public String acelerar() {
        return this.motorElectrico.aumentarVelocidad();
    }

    
    public String apagar() {
        return this.motorElectrico.detener();
    }
}