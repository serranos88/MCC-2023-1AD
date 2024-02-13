package Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Constantes.BloqueSi;

public class Lexer {

    final static HashMap<int[], String> lineasCodigo = new HashMap<int[], String>();
    private int[] numLinea = new int[2];

   public HashMap<int[], String> getLineasCodigo() {
        return lineasCodigo;
    }

   



	//-----------------------------------------------------------------------------------------
	//	Lee el archivo .txt con el codigo fuente
    //  y lo guarda en un ArrayList
    //  ignora lineas \n
	//-----------------------------------------------------------------------------------------
	public static ArrayList<String> obtenerLineasCodigo(String rutaSrc) {
		ArrayList<String> lineasCodigo = new ArrayList<String>();
        
		try(BufferedReader bf = new BufferedReader(new FileReader(rutaSrc))){
			String s;
			while((s = bf.readLine())!=null) {
				if(!s.replace(" ", "").equals(""))
				lineasCodigo.add(s);
				}
			}
		catch(IOException ex) {
			ex.printStackTrace();
		}

		return lineasCodigo;
		
	}

    //-----------------------------------------------------------------------------------------
    //    Tokeniza una linea de codigo
    //    Ejemplo:
    //          tokens1 = [Entero, base]
    //          tokens2 = [Leer, base]
    //          tokens3 = [Real, area]
    //          tokens4 = [area, =, (, base, ), *, altura, /, 2]
    //          tokens5 = [multiplicacion, =, 2, *, 3, +, 6, /, 2]
    //          tokens6 = [ImprimeS, (, El area es: , area, )]
    //          tokens7 = [ImprimeS, (, El resultado de 2 * 3 + 6 / 2, es: , multiplicacion, )]
    //-----------------------------------------------------------------------------------------
      public static String[] tokenizarLinea(String linea) {
        ArrayList<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("'[^']*'|\\(|\\)|\\S+").matcher(linea);
        
        while (matcher.find()) {
            String match = matcher.group();
            if (!match.matches("\\s+")) {
                tokens.add(match.replace("'", ""));
            }
        }
        
        return tokens.toArray(new String[0]);
    }

    //-----------------------------------------------------------------------------------------
    // Identifica bloques de codigo específicos
    // SI (condicion) { ... } SINO { ... }
    //-----------------------------------------------------------------------------------------
    public static String identificarBloques(ArrayList<String> lineasCodigo) {
        boolean enBloque = false;
        String bloque = "";
        for (String linea : lineasCodigo) {
            if (linea.matches(BloqueSi.SI.getEstructura())) {//(linea.matches("\\s*SI\\s*\\(.*\\)\\s*\\{\\s*")) {
                enBloque = true;
                System.out.println("Inicio de bloque SI");
                
                bloque += linea + "\n";
            } else if (linea.matches("\\s*SINO\\s*SI\\s*\\(.*\\)\\s*\\{\\s*")) {
                System.out.println("Inicio de bloque SINO SI");
                
                bloque += linea + "\n";
            } else if (linea.matches("\\s*SINO\\s*\\{\\s*")) {
                System.out.println("Inicio de bloque SINO");
                
                bloque += linea + "\n";
            } else if (linea.matches("\\s*\\}\\s*")) {
                System.out.println("Fin de bloque");
                enBloque = false;
                bloque += linea + "\n";
                
            } else if (enBloque) {
                
                System.out.println("Linea dentro del bloque: " + linea);
                bloque += linea + "\n";
            }
        }
        return bloque;
    }

    //-----------------------------------------------------------------------------------------
    // Identifica bloques de codigo MIENTRAS
    // MIENTRAS (condicion) { ... }
    //-----------------------------------------------------------------------------------------
    public static String identificarBloquesMientras(ArrayList<String> lineasCodigo) {
        boolean enBloque = false;
        String bloque = "";
        for (String linea : lineasCodigo) {
            if (linea.matches("\\s*MIENTRAS\\s*\\(.*\\)\\s*\\{\\s*")) {
                enBloque = true;
                System.out.println("Inicio de bloque MIENTRAS");
                
                bloque += linea + "\n";
            } else if (linea.matches("\\s*\\}\\s*")) {
                System.out.println("Fin de bloque");
                enBloque = false;
                bloque += linea + "\n";
                
            } else if (enBloque) {
                
                System.out.println("Linea dentro del bloque: " + linea);
                bloque += linea + "\n";
            }
        }
        return bloque;
    }
}
