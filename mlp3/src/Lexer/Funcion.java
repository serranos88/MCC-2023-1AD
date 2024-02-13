package Lexer;

import java.util.HashMap;
import java.util.Stack;


public class Funcion {
    private String identificador;
    private String retornoTipo;
    private String parametros;
    private String codigo;
    private Stack<String> pilaEjecucion;
    private String retornoValor;
    private HashMap<String, String> memoriaVariablesInt;
    private HashMap<String, String> memoriaVariablesDouble;
    private HashMap<String, String> memoriaVariablesString;

    public Funcion(String identificador, String retornoTipo, String parametros, String codigo) {
        this.identificador = identificador;
        this.retornoTipo = retornoTipo;
        this.parametros = parametros;
        this.codigo = codigo;
        this.pilaEjecucion = new Stack<String>();
        this.retornoValor = "";
        this.memoriaVariablesInt = new HashMap<String, String>();
        this.memoriaVariablesDouble = new HashMap<String, String>();
        this.memoriaVariablesString = new HashMap<String, String>();
    }

    public void main(){
        String[] lineasCodigo = codigo.split("\n");
        for(String linea : lineasCodigo){
            pilaEjecucion.push(linea);
        }
        while(!pilaEjecucion.isEmpty()){
            String linea = pilaEjecucion.pop();
            String[] palabras = linea.split(" ");
            switch(palabras[0]){
                case "Imprime":
                    String[] datos = linea.substring(linea.indexOf("(")+1, linea.indexOf(")")).split(",");
                    for(String dato : datos){
                        if(dato.contains("'")){
                            System.out.print(dato.replace("'", ""));
                        }else{
                            System.out.print(memoriaVariablesInt.get(dato));
                        }
                    }
                    break;
                case "ImprimeS":
                    String[] datos2 = linea.substring(linea.indexOf("(")+1, linea.indexOf(")")).split(",");
                    for(String dato : datos2){
                        if(dato.contains("'")){
                            System.out.print(dato.replace("'", ""));
                        }else{
                            System.out.print(memoriaVariablesInt.get(dato));
                        }
                    }
                    System.out.println();
                    break;
                case "Leer":
                    String variable = linea.split(" ")[1];
                    System.out.print("> "+variable+": ");
                    memoriaVariablesInt.put(variable, "0");
                    break;
                case "Entero":
                    String variable2 = linea.split(" ")[1];
                    memoriaVariablesInt.put(variable2, "0");
                    break;
                case "Real":
                    String variable3 = linea.split(" ")[1];
                    memoriaVariablesDouble.put(variable3, "0");
                    break;
                case "Si":
                    String condicion = linea.split(" ")[1];
                    String[] lineasCodigo2 = linea.substring(linea.indexOf("{")+1, linea.indexOf("}")).split("\n");
                    if(memoriaVariablesInt.containsKey(condicion)){
                        if(memoriaVariablesInt.get(condicion).equals("0")){
                            for(String linea2 : lineasCodigo2){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }else if(memoriaVariablesDouble.containsKey(condicion)){
                        if(memoriaVariablesDouble.get(condicion).equals("0")){
                            for(String linea2 : lineasCodigo2){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }else if(memoriaVariablesString.containsKey(condicion)){
                        if(memoriaVariablesString.get(condicion).equals("")){
                            for(String linea2 : lineasCodigo2){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }
                    break;
                case "Mientras":
                    String condicion2 = linea.split(" ")[1];
                    String[] lineasCodigo3 = linea.substring(linea.indexOf("{")+1, linea.indexOf("}")).split("\n");
                    if(memoriaVariablesInt.containsKey(condicion2)){
                        if(memoriaVariablesInt.get(condicion2).equals("0")){
                            for(String linea2 : lineasCodigo3){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }else if(memoriaVariablesDouble.containsKey(condicion2)){
                        if(memoriaVariablesDouble.get(condicion2).equals("0")){
                            for(String linea2 : lineasCodigo3){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }else if(memoriaVariablesString.containsKey(condicion2)){
                        if(memoriaVariablesString.get(condicion2).equals("")){
                            for(String linea2 : lineasCodigo3){
                                pilaEjecucion.push(linea2);
                            }
                        }
                    }
                    break;
                case "Retorna":
                    String valor2 = linea.split(" ")[1];
                    if(memoriaVariablesInt.containsKey(valor2)){
                        retornoValor = memoriaVariablesInt.get(valor2);
                    }else if(memoriaVariablesDouble.containsKey(valor2)){
                        retornoValor = memoriaVariablesDouble.get(valor2);
                    }else if(memoriaVariablesString.containsKey(valor2)){
                        retornoValor = memoriaVariablesString.get(valor2);
                    }else{
                        retornoValor = valor2;
                    }
                    break;
                default:
                    String variable6 = linea.split(" ")[0];
                    for(int i = 2; i<linea.split(" ").length; i++){
                        if(esNumero(linea.split(" ")[i])){
                            pilaEjecucion.push(linea.split(" ")[i]);
                        }else{
                            pilaEjecucion.push(linea.split(" ")[i]);
                        }
                    }
                    break;

    }

    public String getIdentificador() {
        return identificador;
    }

    public String getRetornoTipo() {
        return retornoTipo;
    }

    public String getParametros() {
        return parametros;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getRetornoValor() {
        return retornoValor;
    }

    public HashMap<String, String> getMemoriaVariablesInt() {
        return memoriaVariablesInt;
    }

    public HashMap<String, String> getMemoriaVariablesDouble() {
        return memoriaVariablesDouble;
    }

    public HashMap<String, String> getMemoriaVariablesString() {
        return memoriaVariablesString;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }





}
