package ru.sonicxd2.socket.server;

import ru.sonicxd2.socket.between.SocketWrapper;
import ru.sonicxd2.socket.server.utils.TriConsumer;
import ru.sonicxd2.socket.server.utils.TriFunction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    int port;
    TriConsumer<byte[], byte[], byte[]> handler;
    TriFunction<byte[], byte[], byte[], byte[]> answerGenerator;
    ServerSocket serverSocket;


    public SocketServer(int port, TriConsumer<byte[], byte[], byte[]> handler,
                        TriFunction<byte[], byte[], byte[], byte[]> answer) {
        this.port = port;
        this.handler = handler;
        this.answerGenerator = answer;
    }

    /**
     * LOCK THREAD
     * @throws IOException
     */
    public void bind() throws IOException {
        serverSocket = new ServerSocket(port);
        while (!Thread.interrupted()) {
            Socket socket = serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            handle(new SocketWrapper(socket, dataInputStream, dataOutputStream));
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(SocketWrapper socketWrapper) {
        try {
            byte[] data = socketWrapper.readByteArray();
            byte[] publicKey = socketWrapper.readByteArray();
            byte[] sign = socketWrapper.readByteArray();
            handler.accept(data, publicKey, sign);
            socketWrapper.writeByteArray(answerGenerator.generate(data, publicKey, sign));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
