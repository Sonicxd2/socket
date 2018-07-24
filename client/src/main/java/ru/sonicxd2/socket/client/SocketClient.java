package ru.sonicxd2.socket.client;

import ru.sonicxd2.socket.between.SocketWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    private String ip;
    private int port;

    private SocketWrapper socketWrapper;

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Lock thread;(
     * @throws IOException
     */
    public void connect() throws IOException {
        Socket socket = new Socket(ip, port);
        socket.setSoTimeout(5 * 60 * 1000);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        this.socketWrapper = new SocketWrapper(socket, dataInputStream, dataOutputStream);
    }

    public byte[] sendDataAndGetAnswer(byte[] data, byte[] publicKey, byte[] sign) throws IOException {
        this.socketWrapper.writeByteArray(data);
        this.socketWrapper.writeByteArray(publicKey);
        this.socketWrapper.writeByteArray(sign);
        return this.socketWrapper.readByteArray();
    }

    public void closeConnect() {
        socketWrapper.closeConnect();
    }
}
