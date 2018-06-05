package demo.apt.contentview;

import android.app.Activity;

import java.util.LinkedHashMap;
import java.util.Map;

import demo.apt.aptlib.ProxyInfo;

/**
 * <p>类说明</p>
 *
 * @author liuzhaohong 2018/6/5 12:42
 * @version V1.0
 * @name ContentView
 */

public class ContentView {
    private static final Map<Class<?>,AbstractInjector > INJECTORS=new LinkedHashMap<>();

    public static void init(Activity activity){
        AbstractInjector abstractInjector=getInjector(activity);
        abstractInjector.inject(activity);
    }

    private static AbstractInjector getInjector(Activity activity) {
        Class<?> clazz=activity.getClass();
        AbstractInjector injector=INJECTORS.get(clazz);
        if (injector==null){
            try {
                Class injectClazz=Class.forName(clazz.getName()+"$"+ ProxyInfo.PROXY);
                injector= (AbstractInjector) injectClazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return injector;
    }


}
