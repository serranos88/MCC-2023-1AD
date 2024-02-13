package mlp;

public class Variable {
    private String identificador;
    private String tipo;
    private String valor;
    private int valorInt;
    private float valorFloat;
    private String valorString;
    private boolean valorBool;


    public Variable(String identificador, String tipo, String valor) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.valor = valor;
        if (tipo.equals("entero")) {
            this.valorInt = Integer.parseInt(valor);
        } else if (tipo.equals("flotante")) {
            this.valorFloat = Float.parseFloat(valor);
        } else if (tipo.equals("cadena")) {
            this.valorString = valor;
        } else if (tipo.equals("bool")) {
            this.valorBool = Boolean.parseBoolean(valor);
        }
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        if (this.tipo.equals("entero")) {
            this.valor = valor;
            this.valorInt = Integer.parseInt(valor);
        } else if (this.tipo.equals("flotante")) {
            this.valor = valor;
            this.valorFloat = Float.parseFloat(valor);
        } else if (this.tipo.equals("cadena")) {
            this.valor = valor;
            this.valorString = valor;
        } else if (this.tipo.equals("bool")) {
            this.valor = valor;
            this.valorBool = Boolean.parseBoolean(valor);
        }
    }

    public int getValorInt() {
        return valorInt;
    }

    public float getValorFloat() {
        return valorFloat;
    }
    

    public String getValorString() {
        return valorString;
    }

    public boolean getValorBool() {
        return valorBool;
    }

   




}