package motores;


public class MotorElectrico{
    private int carga;

    public MotorElectrico() {
        this.carga = 100;
    }


    public String conectar() {
        return "Conectando";
    }

    public String activar() {
        carga -= 50;
        return "Activando Motor Electrico";
    }

    public String aumentarVelocidad() {
        carga -= 50;
        return "Aumentando Velocidad Motor Electrico";
    }

    public String detener() {
        return "Deteniendo Motor Electrico";
    }

    public String desconectar() {
        carga += 50;
        return "Desconectando Motor Electrico";
    }

    public boolean tieneCarga() {
        if (carga <= 0) {
            return false;
        }else {
            return true;
        }
        
    }
}