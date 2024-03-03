public class App {
    public static void main(String[] args) throws Exception {
        Input input;
        Output output;
        CircularShifter shifter;
        Sort sort;


        //input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras.txt");
        //input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras2.txt");
        //input  = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras3.txt");
        input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras4.txt");

        

        input.leerTexto();
        shifter = new CircularShifter();
        shifter.setup();

        sort = new Sort();
        sort.alfabeticamente();
        
        output = new Output();
        output.imprimir();



    }
}
