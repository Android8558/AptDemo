package demo.apt.aptlib;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * <p>处理器</p>
 *
 * @author liuzhaohong 2018/6/4 16:11
 * @version V1.0
 * @name ContentProcessor
 */

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ContentProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet types = new LinkedHashSet();
        types.add(ContentViewId.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement element : set) {
            messager.printMessage(Diagnostic.Kind.WARNING,
                    "Set<? extends TypeElement> set =" + element.getSimpleName());
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "process() start execute...");
        messager.printMessage(Diagnostic.Kind.NOTE, "所有的类：");
        Set<? extends Element> elements = roundEnvironment.getRootElements();
        for (Element element : elements) {
            TypeElement classElement = (TypeElement) element;
            messager.printMessage(Diagnostic.Kind.NOTE, classElement.getQualifiedName().toString());
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "需要处理的类：");
        Map<String, ProxyInfo> proxyMap = new HashMap<>();
        Set<? extends Element> genElements = roundEnvironment.getElementsAnnotatedWith(ContentViewId.class);
        for (Element element : genElements) {
            TypeElement classElement = (TypeElement) element;
            String fullClassName = classElement.getQualifiedName().toString();

            PackageElement packageElement= processingEnv.getElementUtils().getPackageOf(element);
            String packName=packageElement.getQualifiedName().toString();

            int layoutId = element.getAnnotation(ContentViewId.class).id();
            String title = element.getAnnotation(ContentViewId.class).title();
            String string = "id=" + layoutId + "   title=" + title;
            messager.printMessage(Diagnostic.Kind.NOTE, fullClassName + "    " + string);

            ProxyInfo proxyInfo=proxyMap.get(fullClassName);
            if (proxyInfo==null){
                proxyInfo=new ProxyInfo(packName,classElement.getSimpleName().toString());
                proxyInfo.setLayoutId(layoutId);
                proxyInfo.setTitle(title);
                proxyMap.put(fullClassName,proxyInfo);
            }

            try {
                JavaFileObject jfo = filer.createSourceFile(proxyInfo.getProxyClassFullName(), classElement);
                Writer writer = jfo.openWriter();
                writer.flush();
                writer.append(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

}
