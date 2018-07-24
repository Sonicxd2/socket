package ru.sonicxd2.socket.between;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketWrapper {

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public SocketWrapper(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;

        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    private boolean isClosed() {
        return socket.isClosed();
    }

    public void writeByteArray(byte[] bytes) throws IOException {
        if(isClosed()) throw new IOException("Socket already closed");
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
    }

    public byte[] readByteArray() throws IOException {
        if(isClosed()) throw new IOException("Socket already closed");
        int length = dataInputStream.readInt();
        byte[] bytes = new byte[length];
        if(length != dataInputStream.read(bytes)) throw new IOException("Problems with data.");
        return bytes;
    }

    public void closeConnect() {
        try {
            socket.close();
        } catch (IOException e) {
            //ignore
            e.printStackTrace();
        }
    }
}
