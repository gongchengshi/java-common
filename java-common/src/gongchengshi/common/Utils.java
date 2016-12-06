package sel.common;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> getExceptionMessageChain(Throwable throwable) {
        List<String> result = new ArrayList<String>();
        while (throwable != null) {
            result.add(throwable.getClass().getName() + " : " + throwable.getMessage());
            throwable = throwable.getCause();
        }
        return result;
    }
}
