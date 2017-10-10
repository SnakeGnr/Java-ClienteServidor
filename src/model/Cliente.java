/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author SnakeGnr
 */
//implementar o tcp/ip
public class Cliente {

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando cliente...");
        System.out.println("Iniciando conexão com o Servidor...");

        //Aqui o cliente e servidor serão executados na mesma maquina, 
        //então podemos utilizar o endereço Ip
        //o proprio localhost (127.0.0.1)
        Socket cliente = new Socket("172.16.0.202", 12345);

        System.out.println("Conexão estabelecida.");

        //Por meio do socket podemos enviar dados para o servidor e também receber dados do servidor
        //O InputStream do cliente esta conectado ao OutPutStream do servidor e Input do server conectado ao output do cliente
        InputStream input = cliente.getInputStream();
        OutputStream output = cliente.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Digite uma mensagem para o servidor: ");
            String mensagemC = scanner.nextLine();

            out.println(mensagemC);

            if ("FIM".equals(mensagemC)) {
                break;
            }
            System.out.println("aguardando resposta do servidor...");
            String mensagemS = in.readLine();
            System.out.println("Mensagem recebida do servidor: "+ mensagemS);
            
        }

        System.out.println("Encerrando conexão...");
        in.close();
        out.close();
        cliente.close();
        System.out.println("Conexão Encerrada.");
    }
}
