package filter;

@SuppressWarnings("serial")
public class SemanticException extends FilterException {
    public SemanticException(String message) {
        super("Semantic error: " + message);
    }

}
