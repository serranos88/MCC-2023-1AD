package mlp;

import java.util.Stack;

public class Funcion {
    private String identificador;
    private String tipo;
    private String parametros;
    private String codigo;
    private String retorno;
    private String tipoRetorno;
    private Stack<String> pilaLineasCodigo;

    public Funcion(String identificador, String tipo, String parametros, String codigo, String retorno) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.parametros = parametros;
        this.codigo = codigo;
        this.retorno = retorno;
    }

    public Funcion(String identificador, String tipo, String parametros, String codigo, String retorno, String tipoRetorno) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.parametros = parametros;
        this.codigo = codigo;
        this.retorno = retorno;
        this.tipoRetorno = tipoRetorno;
    }

    public void run() {
        String[] lineas = codigo.split("\n");
        pilaLineasCodigo = new Stack<>();
        for (int i = lineas.length - 1; i >= 0; i--) {
            pilaLineasCodigo.push(lineas[i]);
        }
    }

    public String getRetorno() {
        return retorno;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public String getParametros() {
        return parametros;
    }

    public String getCodigo() {
        return codigo;
    }

    public Stack<String> getPilaLineasCodigo() {
        return pilaLineasCodigo;
    }

    public String toString() {
        return "Funcion{" + "identificador=" + identificador + ", tipo=" + tipo + ", parametros=" + parametros + ", codigo=" + codigo + ", retorno=" + retorno + ", tipoRetorno=" + tipoRetorno + ", pilaLineasCodigo=" + pilaLineasCodigo + '}';
    }
}
