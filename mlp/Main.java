package mlp;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Variable x = new Variable("x", "entero", 5);
        definirVariable(x);
        String expresion = "y = x + 2";
        double resultado = evaluarExpresion(expresion);

        Variable y = new Variable("y", "entero", resultado);
        definirVariable(y);
        System.out.println("El resultado es: " + resultado);

        // Imprimir el valor de las variables en el HashMap
        System.out.println("Valores de las variables:");
        for (Variable variable : variables.values()) {
            System.out.println(variable.getIdentificador() + " = " + variable.getValor());
        }
    }
}

//git config --global user.name "serranos88"
//git config --global user.email "antonio.gs@culiacan.tecnm.mx"
//git remote set-url origin https://github.com/serranos88/MCC-2023-1AD
