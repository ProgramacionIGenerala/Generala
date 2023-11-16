package excepciones;

public class GeneralaException extends RuntimeException {

    public GeneralaException(String mensaje) {
        super(mensaje);
    }

    public class GeneralaException1 extends Exception {
        public GeneralaException1(String message) {
            super(message);
        }

    }
}