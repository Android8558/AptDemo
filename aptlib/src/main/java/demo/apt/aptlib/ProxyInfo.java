package demo.apt.aptlib;

/**
 * <p>����һ������������Ϣ</p>
 *
 * @author liuzhaohong 2018/6/5 12:46
 * @version V1.0
 * @name ProxyInfo
 */

public class ProxyInfo {
    //�������׺��
    public static final  String PROXY="PROXY";

    private String packName;
    private String tarClassName;
    private  String proxyClassName;

    private int layoutId;
    private String title;

    public ProxyInfo(String packName, String tarClassName) {
        this.packName = packName;
        this.tarClassName = tarClassName;
        this.proxyClassName=tarClassName+"$"+PROXY;
    }

    String getProxyClassFullName(){
        return packName+"."+proxyClassName;
    }

     ProxyInfo setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProxyInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    String generateJavaCode(){
        //���ɷ�����
        return "// Generated code from ContentViewId Apt. Do not modify!\n" +
                "package " + packName + ";\n\n" +
                "import android.app.Activity;\n" +
                "import demo.apt.contentview.AbstractInjector;\n" +
                "import android.app.ActionBar;\n" +
                "\n" +
                "public class " + proxyClassName + " implements AbstractInjector{\n" +
                "@Override\n" +
                "public void inject(Activity activity) {\n" +
                "activity.setContentView(" + layoutId + ");\n" +
                " ActionBar actionBar=activity.getActionBar();\n" + "        if (actionBar!=null){\n" + "            actionBar.setTitle(\"" + title + "\");\n" + "        }\n}" +
                "};\n";
    }
}
