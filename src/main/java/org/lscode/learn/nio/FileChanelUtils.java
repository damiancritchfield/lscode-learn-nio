package org.lscode.learn.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChanelUtils {

    private void transferFile(String source, File des) throws IOException {

        if (!des.exists()){
            des.createNewFile();
        }

        RandomAccessFile read = new RandomAccessFile(source, "rw");
        RandomAccessFile write = new RandomAccessFile(des, "rw");

        FileChannel readChannel = read.getChannel();
        FileChannel writeChannel = write.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);//1M缓冲区

        while (readChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        writeChannel.close();
        readChannel.close();
    }

    private void transferFile2(String sourcePash, String desPath) throws IOException {

        FileChannel readChannel = FileChannel.open(Paths.get(sourcePash), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get(desPath), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        MappedByteBuffer readMappedByteBuffer = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());
        MappedByteBuffer writeMappedByteBuffer = readChannel.map(FileChannel.MapMode.READ_WRITE, 0, readChannel.size());

        byte[] dst = new byte[readMappedByteBuffer.limit()];
        readMappedByteBuffer.get(dst);
        writeMappedByteBuffer.put(dst);

        writeChannel.close();
        readChannel.close();
    }

    private void transferFile3(String sourcePash, String desPath) throws IOException {

        FileChannel readChannel = FileChannel.open(Paths.get(sourcePash), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get(desPath), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        readChannel.transferTo(0, readChannel.size(), writeChannel);

        writeChannel.close();
        readChannel.close();
    }

    private void transferFile4(String sourcePash, String desPath) throws IOException {

        FileChannel readChannel = FileChannel.open(Paths.get(sourcePash), StandardOpenOption.READ);

        ByteBuffer byteBuffer0 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        ByteBuffer[] byteBuffers = {byteBuffer0, byteBuffer1};

        readChannel.read(byteBuffers);

        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }

        System.out.println(new String(byteBuffer0.array(), 0, byteBuffer0.limit()));
        System.out.println(new String(byteBuffer1.array(), 0, byteBuffer1.limit()));

        readChannel.close();
    }

    private void transferFile5(String sourcePash, String desPath) throws IOException {

        FileChannel readChannel = FileChannel.open(Paths.get(sourcePash), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get(desPath), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ByteBuffer byteBuffer0 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        ByteBuffer[] byteBuffers = {byteBuffer0, byteBuffer1};

        readChannel.read(byteBuffers);

        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }

        System.out.println(new String(byteBuffer0.array(), 0, byteBuffer0.limit()));
        System.out.println(new String(byteBuffer1.array(), 0, byteBuffer1.limit()));

        writeChannel.write(byteBuffers);

        writeChannel.close();
        readChannel.close();
    }

}
