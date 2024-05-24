package motores;

public class MotorHibrido {
//private Motor motorElectrico;
//private Motor motorGasolina;


public MotorHibrido(){
    //this.motorElectrico = new MotorElectricoAdapter();
    //this.motorGasolina = new MotorComun();
}

public String encendiendo(){
    /*if ( !motorElectrico.tieneCarga() ){
        System.out.println("Auto hibrido encendido: " + this.motorGasolina.encender());
    }else{
        System.out.println("Auto hibrido encendido: " + this.motorElectrico.encender());
    }*/

    return "Encendiendo Motor Hibrido";

}

public String acelerando(){
    /*if ( !motorElectrico.tieneCarga() ){
        System.out.println("Auto hibrido acelerando: " + this.motorGasolina.acelerar());
        System.out.println("Auto hibrido acelerando: " + this.motorElectrico.desconectar());
    }else {
        System.out.println("Auto hibrido acelerando: " + this.motorElectrico.acelerar());
    }*/
    return "Acelerando Motor Hibrido";
}

public String apagando(){
    /*if ( !motorElectrico.tieneCarga() ){
        System.out.println("Auto hibrido apagando: " + this.motorGasolina.apagar());
    } else {
        System.out.println("Auto hibrido apagando: " + this.motorElectrico.apagar());
    }*/
    return "Deteniendo Motor Hibrido";
}

}