import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Client basique TCP
 * 
 */
public class ClientTCP
{

	//Creation de la socket
	Socket socket;
	DatagramPacket dpE;
	DatagramPacket dpR;
	InetSocketAddress adrDest;

	byte[] buf;
	int posBuf = 0;
	int posBufFillTo = 0;
	InputStream is;
	OutputStream os;
	
								
	/**
	 * Le client cree une socket, envoie un message au serveur
	 * et attend la reponse 
	 * 
	 */
	public void connect(String IPSrv, int PortSrc)  throws IOException
	{
		//
		System.out.println("Demarrage du client ...");
		
		// Connexion au serveur 
		adrDest = new InetSocketAddress(IPSrv, PortSrc);
		socket = new Socket();
		socket.connect(adrDest);	
	}

		/**
		 * Le client cree une socket, envoie un message au serveur
		 * et attend la reponse 
		 * 
		 */
	public void execute()  throws IOException
	{
		// Attente de la reponse
		
		
		
		is = socket.getInputStream();
		os = socket.getOutputStream();

		boolean done = false;
		
		while (!done)
		{
			int a = getOperande('+');
			int b = getOperande('=');
			int c = a+b;
			os.write(c + ';');
		}

/*		System.out.println(is);
		//int lenBufR = is.read(bufR);
		//int lenBufR = is.readInputStream()
		StringBuffer message = readInputStream(2048,is);
		//String message = new String(bufR, 0 , lenBufR );
		System.out.println("Reponse recue = "+message);
			
		int iSNb1 = 0; int iENb1 = 0;
		int iSNb2 = 0; int iENb2 = 0;
		int A; int B;
		int resultat = 0;
		boolean flag  = false;
		StringBuffer envoi = new StringBuffer();
		int offset = 0;

		for (int i= 0; i < message.length();i++) {
			switch (message.charAt(i))
			{
				case '+':
					iENb1 = i;
					iSNb2 = i + 1;
					break;
				case '=':
					iENb2 = i;
					break;
				case '?':
					flag = true;
				default:
					break;					
			}
			if (flag) {
				A = Integer.valueOf(message.substring(offset, iENb1 + offset));
				B = Integer.valueOf(message.substring(iSNb2 + offset, iENb2 + offset));
				resultat = A + B;
				envoi.append(resultat);
				envoi.append(';');
				System.out.println(envoi);
				flag = false;
				offset = i;
			}
			
		}
		
		// Envoi de la requete
		byte[] bufE = new String(envoi).getBytes();
		OutputStream os = socket.getOutputStream();
		os.write(bufE);
		System.out.println("Message envoye" + envoi);	*/		
	}
	
	private int getOperande(char c) throws IOException
	{
		String str = "";
		boolean done = false;
		
		while (!done)
		{
			StringBuffer s = getNext();
			
			if (s.equals(c))
			{
				return new Integer(str);
			}
			str = str +s;
		}
		return 0;
	}
	
	private StringBuffer getNext() throws IOException
	{
		int nbCarValide = posBufFillTo - posBuf;
		
		System.out.println(posBuf);
		
		if (nbCarValide == 0)
		{
			if (posBuf == 1024)
			{
				posBuf = 0;
				posBufFillTo = 0;
			}
			System.out.println(posBuf);
			int nbRead;
			nbRead = is.read(buf, posBufFillTo, 1024 - posBuf);
			posBufFillTo = +nbRead;			
		}
		
		byte C = buf[posBuf];
		posBuf++;
		return new StringBuffer(C);
	}
	
	
	public void close() throws IOException
	{
		// Fermeture de la socket
		socket.close();
		System.out.println("Arret du client .");
	}
		

	
	/**
	 * Methode utilitaire permettant de lire au minimum nbByte octets dans le fux is
	 * 
	 * A noter : si la methode read retourne plus de caracteres que nbByte, 
	 * alors les caracteres lus en plus sont ajoutes dans la reponse 
	 */
	private StringBuffer readInputStream(int nbByte, InputStream is) throws IOException
	{
		StringBuffer buf = new StringBuffer();

		// Nombre de caracteres reellement lus au total
		int nbByteRead=0;
		
		int nb;
		byte[] bufR = new byte[1024];
		
		while(nbByteRead<nbByte)
		{
			nb = is.read(bufR);
			System.out.println(nb);
			if (nb==-1)
			{
				throw new IOException("Fin du stream atteinte avant d'avoir lu "+nbByte+" octets");
			}
			nbByteRead = nbByteRead+nb;
			System.out.println(nbByteRead);
			buf.append(new String(bufR,0,nb));
		}
		return buf;
		
	}

}
