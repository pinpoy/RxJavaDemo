package com.example.socket.classloder;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarDecodeClassLoader extends ClassLoader {

    private JarInputStream jis;
    private Map<String, ByteBuffer> entryMap;

    public JarDecodeClassLoader(String path) throws IOException {
        jis = new JarInputStream(new FileInputStream(path));
        entryMap = new HashMap<>();
        JarEntry jarEntry;
        while ((jarEntry = jis.getNextJarEntry()) != null) {
            String name = jarEntry.getName();
            byte[] bytes = getBytes(jis);
            if (name.endsWith(".class")) {
                byte[] decodeBytes = Base64.getDecoder().decode(bytes);

                ByteBuffer byteBuffer = ByteBuffer.wrap(decodeBytes);
                entryMap.put(name, byteBuffer);
            } else {
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                entryMap.put(name, byteBuffer);
            }
        }
        jis.close();

    }

    /**
     * 从jar输入流中读取信息
     *
     * @param jis
     * @return
     * @throws IOException
     */
    private byte[] getBytes(JarInputStream jis) throws IOException {
        int len;
        byte[] bytes = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        while ((len = jis.read(bytes, 0, bytes.length)) != -1) {
            baos.write(bytes, 0, len);
        }
        return baos.toByteArray();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = name.replace(".", "/").concat(".class");
        ByteBuffer byteBuffer = entryMap.get(className);
        if (byteBuffer == null) {
            return super.findClass(name);
        } else {
            byte[] bytes = byteBuffer.array();
            return defineClass(name, bytes, 0, bytes.length);
        }
    }

    /**
     * 关闭Decoder
     *
     * @throws IOException
     */
    public void close() throws IOException {
        Iterator<ByteBuffer> iterator = entryMap.values().iterator();
        while (iterator.hasNext()) {
            ByteBuffer buffer = iterator.next();
            buffer.clear(); //清空ByteBuffer对象缓存
        }
        entryMap.clear(); //清空HashMap
    }

}
