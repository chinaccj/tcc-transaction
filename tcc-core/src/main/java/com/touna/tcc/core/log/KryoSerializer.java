package com.touna.tcc.core.log;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.Input;
import com.touna.tcc.core.TccContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

/**
 * Created by chenchaojian on 17/5/31.
 */
public class KryoSerializer implements Serializer {
    private int maxCapacity;
    //spared max capacity
    private final int maxCapacitySpared = 2048;

    public KryoSerializer() {
    }

    @Override
    public byte[] serialize(Object object) {
        if (maxCapacity == 0) maxCapacity = maxCapacitySpared;

        Output output = new Output(maxCapacity);
        try {
            Kryo kryo = new Kryo();
            kryo.writeObject(output, object);

            return output.toBytes();
        } finally {
            output.clear();
        }
    }

    @Override
    public byte[] serialize(Object object, int capacity) {
        Output output = new Output(capacity);
        try {
            Kryo kryo = new Kryo();
            kryo.writeObject(output, object);

            return output.toBytes();
        } finally {
            output.clear();
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class type) {
        Kryo kryo = new Kryo();
        Input input = new Input(bytes);
        return kryo.readObject(input, type);
    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public static void main(String args[]) throws Exception {
//        testWrite();
//        testRead();

        testWrite1();

    }


    private static void testWrite1()throws Exception{
        TccContext context = new TccContext();
        context.setAttachment("name", "jack");

        String str = "lucy";
        Object[] paramValues = new Object[2];
        paramValues[0] = context;
        paramValues[1] = str;

        Serializer serializer = new KryoSerializer();
        byte[] bytes = serializer.serialize(new ObjectWrapper(paramValues));
        String strParamValues = new String(bytes, "UTF-8");

        ObjectWrapper objectWrapper = (ObjectWrapper)serializer.deserialize(strParamValues.getBytes(Charset.forName("UTF-8")), ObjectWrapper.class);
        Object[] objectsDes = objectWrapper.toObjectArray();
        TccContext contextDes = (TccContext)objectsDes[0];
        System.out.println(contextDes.getAttachment("name"));

    }
    private static void testWrite() throws Exception {
        Kryo kryo = new Kryo();



        Output output = new Output(new FileOutputStream("/tmp/file.bin"));

        TccContext context = new TccContext();
        context.setAttachment("name", "jack");

        String str = "lucy";
        Object[] paramValues = new Object[2];
        paramValues[0] = context;
        paramValues[1] = str;



        ObjectWrapper objectWrapper = new ObjectWrapper(paramValues);


        kryo.writeObject(output, objectWrapper);
        output.close();
    }

    private static void testRead() throws Exception {
        Kryo kryo = new Kryo();
        Input input = new Input(new FileInputStream("/tmp/file.bin"));
        ObjectWrapper objectWrapper = kryo.readObject(input, ObjectWrapper.class);
        input.close();

        Object[] objects = objectWrapper.toObjectArray();
        TccContext context = (TccContext)objects[0];
        String name = (String)objects[1];

        System.out.println("attachment " + context.getAttachment("name"));
        System.out.println("name " + name);


    }
}