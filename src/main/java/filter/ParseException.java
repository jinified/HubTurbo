package filter;

@SuppressWarnings("serial")
public class ParseException extends FilterException {
      public ParseException(String message) {
         super("Parse error: " + message);
      }
 }
