package mlp;
import java.util.Stack;

import mlp.*;
import mlp.enums.DATA_TIPO;

public class Ejecucion {
    EvaluadorAlgebraico evaluadorAlgebraico;
    Stack<String> pilaLineasCodigo;

    public Ejecucion() {
        this.evaluadorAlgebraico = new EvaluadorAlgebraico();
    }

    public void ejecutarLinea(String linea) {
        if (DATA_TIPO.isDeclaracion(linea)) {
            this.ejecutarDeclaracion(linea);
        } else if (DATA_TIPO.isAsignacion(linea)) {
            this.ejecutarAsignacion(linea);
        } else if (DATA_TIPO.isLeer(linea)) {
                this.ejecutarLeer(linea);
        } else if (DATA_TIPO.isImpresion(linea)) {
            this.ejecutarImpresion(linea);
        } else if (DATA_TIPO.isOperacion(linea)) {
            this.ejecutarOperacion(linea);
        } else if (DATA_TIPO.isCondicion(linea)) {
            this.ejecutarCondicion(linea);
        } else if (DATA_TIPO.isCiclo(linea)) {
            this.ejecutarCiclo(linea);
        } else if (DATA_TIPO.isFin(linea)) {
            this.ejecutarFin(linea);
        } else {
            System.out.println("Error: No se reconoce la linea: " + linea);
        }
    }

    private void ejecutarDeclaracion(String linea) {
        String[] partes = linea.split(" ");
        String tipo = partes[0];
        String identificador = partes[1];
        String valor = partes[3];
        if (tipo.equals(DATA_TIPO.ENTERO.toString())) {
            this.evaluadorAlgebraico.declararVariable(identificador, Integer.parseInt(valor));
        } else if (tipo.equals(DATA_TIPO.REAL.toString())) {
            this.evaluadorAlgebraico.declararVariable(identificador, Double.parseDouble(valor));
        } else if (tipo.equals(DATA_TIPO.TEXTO.toString())) {
            this.evaluadorAlgebraico.declararVariable(identificador, valor);
        } else {
            System.out.println("Error: No se reconoce el tipo: " + tipo);
        }
    }

    private void ejecutarAsignacion(String linea) {
        String[] partes = linea.split(" ");
        String identificador = partes[0];
        String valor = partes[2];
        this.evaluadorAlgebraico.asignarVariable(identificador, valor);
    }

    private void ejecutarImpresion(String linea) {
        String[] partes = linea.split(" ");
        String valor = partes[1];
        this.evaluadorAlgebraico.imprimir(valor);
    }

    private void ejecutarOperacion(String linea) {
        String[] partes = linea.split(" ");
        String identificador = partes[0];
        String operacion = partes[2];
        String valor = partes[4];
        this.evaluadorAlgebraico.operar(identificador, operacion, valor);
    }

    private void ejecutarCondicion(String linea) {
        String[] partes = linea.split(" ");
        String condicion = partes[1];
        String valor1 = partes[2];
        String valor2 = partes[4];
        String operacion = partes[3];
        this.evaluadorAlgebraico.condicionar(condicion, valor1, operacion, valor2);
    }

    private void ejecutarCiclo(String linea) {
        String[] partes = linea.split(" ");
        String condicion = partes[1];
        String valor1 = partes[2];
        String valor2 = partes[4];
        String operacion = partes[3];
        this.evaluadorAlgebraico.ciclar(condicion, valor1, operacion, valor2);
    }

    private void ejecutarFin(String linea) {
        this.evaluadorAlgebraico.fin();
    }

    private void ejecutarLeer(String linea) {
        String[] partes = linea.split(" ");
        String identificador = partes[1];
        this.evaluadorAlgebraico.leer(identificador);
    }


}
