import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestMappingTest {

    @Test
    public void emptyWordKeyTest() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("", "test");
        System.out.println(testMap.get(""));
    }
}