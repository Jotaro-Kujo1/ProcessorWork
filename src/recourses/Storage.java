package recourses;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private Map<String, Long> myLongMap = new HashMap<String, Long>();
    private Map <String, Double> myDoubleMap = new HashMap <String, Double> ();

    public Map<String, Long> getMyLongMap() {
        return myLongMap;
    }

    public void setMyLongMap(Map<String, Long> myLongMap) {
        this.myLongMap = myLongMap;
    }

    public Map<String, Double> getMyDoubleMap() {
        return myDoubleMap;
    }

    public void setMyDoubleMap(Map<String, Double> myDoubleMap) {
        this.myDoubleMap = myDoubleMap;
    }

    public void treatment(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get")
                    && Modifier.isPublic(method.getModifiers())) {
                Object value;
                //double load = 0;
                try {
                    value = method.invoke(operatingSystemMXBean);
                    //load = (double) method.invoke(operatingSystemMXBean);
                    if(value instanceof Long) {
                        myLongMap.put(method.getName(), (Long) value);
                    }else{
                        myDoubleMap.put(method.getName(), (Double) value);
                    }
                } catch (Exception e) {
                    value = e;
                } // try
                System.out.println(method.getName() + " = " + value);

            }
        }
    }
}
