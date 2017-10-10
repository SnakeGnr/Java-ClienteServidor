package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//Utilizar sempre importações java.net
/**
 * @author SnakeGnr
 */
public class Servidor {

    
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando Servidor...");
        //ao criar o obj ServerSocket, geralmente informamos apenas o n° da porta, 
        //Ele irá assumir o IP da interface padrão
        ServerSocket server = new ServerSocket(12345);

        //Chamamos o método accept() para que o servidor passe a receber conexões dos clientes.
        System.out.println("Aguardando Conexão...");
        Socket cliente = server.accept();
        
        System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
        System.out.println("Conexão Estabelecida");
        
        //O InputStream do CLiente esta conectado ao OUtputStream do servidor. E o InputStream do servidor
        //E o InputStream do servidoresta conectado ao outputStream do cliente
        //Quando um grava o outro lê, e vice-versa
        InputStream input = cliente.getInputStream();
        OutputStream output = cliente.getOutputStream();

        //Input e Output geram bytes... Precisamos de objetos de leitura e gravação mais apropriados,
        //Assim encapsulamos e tranformamos em string com o PrintStream
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);
        
         Scanner scanner = new Scanner(System.in);
        
        //agora podemos ler e gravar mensagens do/para cliente
        while (true) {
             System.out.println("aguardando resposta do cliente...");
            String mensagemC = in.readLine(); //Lê a mensagem
            System.out.println("Mensagem recebida do cliente ("
                    + cliente.getInetAddress().getHostName() + "): " + mensagemC);
            
            if ("FIM".equals(mensagemC)) {
                break;
            }
            System.out.println("Digite uma mensagem para o cliente: ");
            String mensagemS = scanner.next();
            out.println(mensagemS);//envia novamente a mensagem pro cliente
        }
        //Ao abandonar o laçoWhile, deve-se enceerar a conexão com o cliente, liberando todos os recursos alocados
        System.out.println("Encerrando conexão...");
        in.close();
        out.close();
        cliente.close();
        //Esse sistema consegue ateder um unico cliente por vez e as mensagens trocadas são sincronizadas
        //quando o socket cliente grava, o socket server lê;
        //quando o socket server grava, o socket cliente lê;
        server.close();
        System.out.println("Servidor Encerrado!");
    }
}
