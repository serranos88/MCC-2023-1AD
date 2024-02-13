package mlp;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Lexer {
    private String codigo;
    private Stack<String> pilaLineasCodigo;
    private HashMap<String, Variable> variables;
    private HashMap<String, Funcion> funciones;
    private Stack<String> llavesFuncion = new Stack<String>();
	private Stack<String> llavesDemas = new Stack<String>();
    private String[] lineas;
    private int lineaActual;
    private String[] tokens;
    private int tokenActual;
    private String token;
    private String tipoToken;
    private String valorToken;
    private String[] tipos = {"int", "float", "char", "string", "bool"};
    private String[] operadores = {"+", "-", "*", "/", "%", "++", "--", "&&", "||", "!", "==", "!=", "<", ">", "<=", ">="};
    private String[] palabrasReservadas = {"if", "else", "while", "for", "do", "switch", "case", "break", "continue", "return", "int", "float", "char", "string", "bool", "true", "false", "void", "main"};
    private String[] delimitadores = {"(", ")", "{", "}", "[", "]", ",", ";", ":"};
    private String[] asignaciones = {"=", "+=", "-=", "*=", "/=", "%="};
    private String[] tiposRetorno = {"int", "float", "char", "string", "bool", "void"};
    private String[] tiposParametros = {"int", "float", "char", "string", "bool"};
    private String[] tiposValores = {"int", "float", "char", "string", "bool", "true", "false"};
    private String[] tiposValoresSinBool = {"int", "float", "char", "string", "true", "false"};
    private String[] tiposValoresSinString = {"int", "float", "char", "true", "false"};
    private String[] tiposValoresSinChar = {"int", "float", "string", "true", "false"};
    private String[] tiposValoresSinFloat = {"int", "char", "string", "true", "false"};
    private String[] tiposValoresSinInt = {"float", "char", "string", "true", "false"};
    private String[] tiposValoresSinIntFloat = {"char", "string", "true", "false"};
    private String[] tiposValoresSinIntFloatChar = {"string", "true", "false"};
    private final String Ruta = "C:\\Users\\serra\\Desktop\\MCC-2023-1AD\\mlp\\src\\mlp\\code1.txt";

    public Lexer(String codigo) {
        this.codigo = codigo;
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
        this.lineaActual = 0;
        this.tokenActual = 0;
        this.token = "";
        this.tipoToken = "";
        this.valorToken = "";
        this.lineas = codigo.split("\n");
        this.pilaLineasCodigo = new Stack<>();
        for (int i = lineas.length - 1; i >= 0; i--) {
            pilaLineasCodigo.push(lineas[i]);
        }

        
    }

    public ArrayList<String> leerTexto() {//Lista de funciones y sus datos
		ArrayList<String> informacion = new ArrayList<String>();
		try(BufferedReader bf = new BufferedReader(new FileReader(Ruta))){//Lee los datos
			String s;
			while((s = bf.readLine())!=null) {//Hasta que no haya linea para leer
				if(!s.replace(" ", "").equals(""))
				informacion.add(s);//Se anade la linea a la
				}
			}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		//Se regresa el ArrayList en Array
		return informacion;
		
	}

    public void run() {
        while (!pilaLineasCodigo.empty()) {
            String linea = pilaLineasCodigo.pop();
            tokens = linea.split(" ");
            tokenActual = tokens.length - 1;
            while (tokenActual >= 0) {
                token = tokens[tokenActual];
                if (token.equals("")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("}")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("{")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(")")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("(")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("]")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("[")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(";")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(",")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(":")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("++")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("--")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("&&")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("||")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("!")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("==")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("!=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("<")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(">")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("<=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals(">=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("+=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("-=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("*=")) {
                    tokenActual--;
                    continue;
                }
                if (token.equals("/=")) {
                    tokenActual--;
                    continue;
                }

                if (token.equals("int") || token.equals("float") || token.equals("char") || token.equals("string") || token.equals("bool")) {
                    tipoToken = token;
                    tokenActual--;
                    token = tokens[tokenActual];
                    if (token.equals("main")) {
                        tokenActual--;
                        token = tokens[tokenActual];
                        if (token.equals("(")) {
                            tokenActual--;
                            token = tokens[tokenActual];
                            if (token.equals(")")) {
                                tokenActual--;
                                token = tokens[tokenActual];
                                if (token.equals("{")) {
                                    tokenActual--;
                                    token = tokens[tokenActual];
                                    if (token.equals("}")) {
                                        tokenActual--;
                                        token = tokens[tokenActual];
                                        if (token.equals("}")) {
                                            tokenActual--;
                                            token = tokens[tokenActual];
                                            if (token.equals("}")) {
                                                tokenActual--;
                                                token = tokens[tokenActual];
                                                if (token.equals("}")) {
                                                    tokenActual--;
                                                    token = tokens[tokenActual];
                                                    if (token.equals("}")) {
                                                        tokenActual--;
                                                        token = tokens[tokenActual];
                                                        if (token.equals("}")) {
                                                            tokenActual--;
                                                            token = tokens[tokenActual];
                                                            if (token.equals("}")) {
                                                                tokenActual--;
                                                                token = tokens[tokenActual];
                                                                if (token.equals("}")) {
                                                                    tokenActual--;
                                                                    token = tokens[tokenActual];
                                                                    if (token.equals("}")) {
                                                                        tokenActual--;
                                                                        token = tokens[tokenActual];
                                                                        if (token.equals("}")) {
                                                                            tokenActual--;
                                                                            token = tokens[tokenActual];
                                                                            if (token.equals("}")) {
                                                                                tokenActual--;
                                                                                token = tokens[tokenActual];
                                                                                if (token.equals("}")) {
                                                                                    tokenActual--;
                                                                                    token = tokens[tokenActual];
                                                                                    
}}}}} }}}}


