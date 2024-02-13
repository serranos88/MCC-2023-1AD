package Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Analisis.Depurardor;
import Constantes.BloqueSi;

public final class Lexer {

    final static HashMap<int[], String> lineasCodigo = new HashMap<int[], String>();
    

   public HashMap<int[], String> getLineasCodigo() {
        return lineasCodigo;
    }

	//-----------------------------------------------------------------------------------------
	//	Lee el archivo .txt con el codigo fuente
    //  y lo guarda en un ArrayList
    //  ignora lineas \n
    // Agrega el numero de linea al inicio de cada linea
	//-----------------------------------------------------------------------------------------
	public static ArrayList<String> obtenerLineasCodigo(String rutaSrc) {
		ArrayList<String> lineasCodigo = new ArrayList<String>();
        int numLinea = 1;
        
		try(BufferedReader bf = new BufferedReader(new FileReader(rutaSrc))){
			String s;
			while((s = bf.readLine())!=null) {
				if(!s.replace(" ", "").equals(""))
                s = numLinea + " " + s;
                numLinea++;
				lineasCodigo.add(s); // ejemplo: 1 Leer base
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
    //          tokens1 = [1, Entero, base]
    //          tokens2 = [2, Leer, base]
    //          tokens3 = [3, Real, area]
    //          tokens4 = [4, area, =, (, base, ), *, altura, /, 2]
    //          tokens5 = [5, multiplicacion, =, 2, *, 3, +, 6, /, 2]
    //          tokens6 = [6, ImprimeS, (, El area es: , area, )]
    //          tokens7 = [7, ImprimeS, (, El resultado de 2 * 3 + 6 / 2, es: , multiplicacion, )]
    //-----------------------------------------------------------------------------------------
      public static String[] tokenizarLinea(String linea) {
        ArrayList<String> tokens = new ArrayList<>();
        
        //String regex = "\\d+|'[^']*'|\\(|\\)|\\S+";
        String regexSub = "\\d+|'[^']*'|\\(|\\)|\\S+";
        String regex = "(?<declaracion>\\d+\\s(Entero|Real|Cadena)\\s\\w+)|"//"(?<declaracion>\\d+\\s(Entero|Real|Cadena)\\s\\w+)|"
        +"(?<leer>\\d+\\sLeer\\s\\w+)|"
        +"(?<imprimeS>\\d+\\sImprimeS\\s\\(('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+\\))|"
        +"(?<imprime>\\d+\\sImprime\\s\\(('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+\\))|"
        +"(?<asignacion>\\d+\\s\\w+\\s=\\s('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+)";


        //+"(?<operacion>\\d+\\s\\w+\\s=\\s('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+)|"
        //+"(?<si>\\d+\\sSi\\s\\(('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+\\))|"
        //+"(?<sino>\\d+\\sSino)|(?<sinosi>\\d+\\sSinoSi\\s\\(('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+\\))|"
        //+"(?<mientras>\\d+\\sMientras\\s\\(('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+\\))|"
        //+"(?<hacer>\\d+\\sHacer)|(?<fin>\\d+\\sFin)|(?<funcion>\\d+\\sFuncion\\s\\w+\\s\\(\\))|"
        //+"(?<retorno>\\d+\\sRetorna\\s('[^']*'|\\w+|\\d+|\\s|\\+|\\-|\\*|\\/|\\^|\\(|\\)|\\.|\\,)+))";

       
        //String regexFuncion = "(?<funcion>publica)|(?<retornoTipo>void|entero|real|cadena)|(?<retorna>Retorna)";
        //String regexSi = "(?<si>\\d+\\sSi\\s\\(\\w+\\s(>|<|==|>=|<=|!=))|(?<sino>Sino)|(?<sinosi>SinoSi)";
        //String regexMientras = "(?<mientras>Mientras)|(?<hacer>Hacer)|(?<fin>Fin)";
        
        Pattern patron = Pattern.compile(regex);
        Matcher matcher = patron.matcher(linea);
        
        Pattern patronSub = Pattern.compile(regexSub);
        

        while (matcher.find()) {
            String match = matcher.group();
            Depurardor.printInfo(match);
            Matcher matcherSub = patronSub.matcher(match);

            while (matcherSub.find()) {
                String matchSub = matcherSub.group();
                Depurardor.printInfo(matchSub);
                if (!matchSub.matches("\\s+")) {
                    tokens.add(matchSub);
                }
            }

        }

        for (String token : tokens) {
            Depurardor.printEstado(token);
        }

        
        return tokens.toArray(new String[0]); // ejemplo: [Leer, base]
    }

    //-----------------------------------------------------------------------------------------
    // Identifica bloques de codigo específicos
    // SI (condicion) { ... } SINO { ... }
    //-----------------------------------------------------------------------------------------
    public static String identificarBloquesSi(ArrayList<String> lineasCodigo) {
        boolean enBloque = false;
        String bloque = "";
        for (String linea : lineasCodigo) {
            if (linea.matches(BloqueSi.SI.getEstructura())) {
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

    //-----------------------------------------------------------------------------------------
    // Identifica bloques de codigo FUNCION
    // FUNCION nombreFuncion (parametros) { ... }
    //-----------------------------------------------------------------------------------------
    public static String identificarBloquesFuncion(ArrayList<String> lineasCodigo) {
        boolean enBloque = false;
        String bloque = "";
        for (String linea : lineasCodigo) {
            if (linea.matches("\\s*FUNCION\\s*\\w+\\s*\\(.*\\)\\s*\\{\\s*")) {
                enBloque = true;
                System.out.println("Inicio de bloque FUNCION");
                
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
