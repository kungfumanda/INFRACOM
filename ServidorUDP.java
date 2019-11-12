import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ServidorUDP extends Thread {
    private static ArrayList<BufferedWriter>clientes;           
    private static ServerSocket server; 
    private String nome; 
    private Socket socket;
    private InputStream entrada;
    private InputStreamReader inr;  
    private BufferedReader bfr;

    public ServidorUDP(Socket socket){
        this.socket = socket;
        try { //tenta
            entrada = socket.getInputStream();
            inr = new InputStreamReader(entrada);
            bfr = new BufferedReader(inr);
        } catch (IOException e){ //erro
            e.printStackTrace(); //printa o erro ocorrido
            }
        }

    public void run(){ //ao chegar um novo cliente, aciona-se o metodo e o novo cliente é alocado, e há verificacao de msgs, caso haja, esta será lida e o evento “sentToAll” será acionado para enviar a mensagem para os demais usuários conectados no chat                   
        try{                                  
            String msg;
            OutputStream ou =  this.socket.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw); 
            clientes.add(bfw);
            nome = msg = bfr.readLine();
               
            while(!"Sair".equalsIgnoreCase(msg) && msg != null){
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);                                              
            }    
                                      
        }catch (Exception e) {
            e.printStackTrace();
        }                       
    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException { 
        BufferedWriter bwS;
        for(BufferedWriter bw : clientes){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(nome + " -> " + msg+"\r\n");
                bw.flush(); 
            }        
        }          
    }

    public static void main(String[] args) {
        int port = 8888;
        try {
            // Instancia o ServerSocket ouvindo a porta 8888
            ServerSocket tpmsocket = new ServerSocket(port); //Socket para ouvir na porta
            Socket socket = tmpsocket.accept(); //Esperando o cliente para estabelecer conexao
            while(true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress()); //mostra o endereco de ip do cliente
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
                clientes = new ArrayList<BufferedWriter>();
                saida.flush();
                saida.writeObject();
                saida.close();
                cliente.close();
            }
        }  
        socket.close();
    }
}
