package com.example.socket.classloder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sun.applet.AppletClassLoader;

public class ClassLoaderTest {

    public static void main(String[] args) throws IOException {
		/*URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
		System.out.println("##################");
		// System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println("##################");
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent(); // 获得父类加载器的引用
		}
		System.out.println(loader);*/
        System.out.println("##################");

		/*try {
			String rootUrl = "http://localhost:8080/yaoweb/bin";
			NetworkClassLoader networkClassLoader = new NetworkClassLoader(rootUrl);
			String classname = "com.yao.data.structure.MergeSort";
			Class<?> clazz = networkClassLoader.loadClass(classname);
			System.out.println(clazz);
			System.out.println(clazz.getClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        JarEncrypt jarEncrypt = new JarEncrypt("Socket/libs/test.jar");
        jarEncrypt.write("Socket/libs/encode_test.jar");

        JarDecodeClassLoader loader = new JarDecodeClassLoader("Socket/libs/encode_test.jar");
        try {
            Class<?> clazz = loader.loadClass("com.itsm.xkitsm.piwen.hehe");
            Method method = clazz.getMethod("gethehe");
            method.invoke(null);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }



    }
    //test for stash

}
