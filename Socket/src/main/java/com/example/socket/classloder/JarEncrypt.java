package com.example.socket.classloder;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarEncrypt {

    private JarInputStream jis = null;

    public JarEncrypt(InputStream is) throws IOException {
        jis = new JarInputStream(is);
    }

    public JarEncrypt(File file) throws IOException {
        this(new FileInputStream(file));
    }

    public JarEncrypt(String path) throws IOException {
        this(new File(path));
    }

    /**
     * 通过指定的路径输出加密后的jar
     *
     * @param path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String path) throws FileNotFoundException, IOException {
        write(new FileOutputStream(path));
    }

    /**
     * 通过指定的文件输出加密后的jar
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(File file) throws FileNotFoundException, IOException {
        write(new FileOutputStream(file));
    }

    /**
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        Manifest manifest = jis.getManifest();
        JarOutputStream jos;
        if (manifest == null) {
            jos = new JarOutputStream(os);
        } else {
            jos = new JarOutputStream(os, manifest);
        }

        JarEntry jarEntry;
        while ((jarEntry = jis.getNextJarEntry()) != null) {
            jos.putNextEntry(jarEntry);
            byte[] bytes = getBytes(jis);
            if (jarEntry.getName().endsWith(".class")) {
                byte[] enBytes = Base64.getEncoder().encode(bytes);
                jos.write(enBytes, 0, enBytes.length);
            } else {
                jos.write(bytes, 0, bytes.length);
            }
            jos.flush();
        }
        jos.close();
        jis.close();
    }

    /**
     * @param jis
     * @return
     * @throws IOException
     */
    private byte[] getBytes(JarInputStream jis) throws IOException {
        int len = 0;
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = jis.read(bytes, 0, bytes.length)) != -1) {
            baos.write(bytes, 0, len);
        }
        return baos.toByteArray();
    }
}
