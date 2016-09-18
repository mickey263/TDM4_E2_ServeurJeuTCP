import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.Socket;

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
			System.out.println("toto");
		byte[] bufR = new byte[2048];
		InputStream is = socket.getInputStream();
		System.out.println(is);
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
		System.out.println("Message envoye" + envoi);			
	}
	
	public void close() throws IOException
	{
		// Fermeture de la socket
		socket.close();
		System.out.println("Arret du client .");
	}
		
		
		
		
		
/*		//
		System.out.println("Demarrage du client ...");

		// Creation et envoi du message
		InetSocketAddress adrDest = new InetSocketAddress(IPSrv, PortSrc);
		byte[] bufE = new String("JOUER").getBytes();
		dpE = new DatagramPacket(bufE, bufE.length, adrDest);
		socket = new Socket();
		socket.send(dpE);
		System.out.println("Message envoy�");
		
		// Attente de la reponse 
		byte[] bufR = new byte[2048];
		dpR = new DatagramPacket(bufR, bufR.length);
		socket.receive(dpR);
		String message = new String(bufR, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message);
		
		int iSQid = 0; int iEQid = 0;
		int iSNb1 = 0; int iENb1 = 0;
		int iSNb2 = 0; int iENb2 = 0; 
		
		for (int i= 0; i < message.length();i++) {
			switch (message.charAt(i))
			{
				case 'Q':
					iSQid = i+1;
					break;
				case ':':
					iEQid = i;
					iSNb1 = i+1;
					break;
				case '+':
					iENb1 = i;
					iSNb2 = i+1;
					break;
				case '=':
					iENb2 = i;
					break;
				default:
					break;					
			}
		}
		
		System.out.println("id Question "+ message.substring(iSQid, iEQid));
		System.out.println("Valeur A "+ message.substring(iSNb1, iENb1));
		System.out.println("Valeur B "+ message.substring(iSNb2, iENb2));
		
		String QID = message.substring(iSQid, iEQid);
		int A = Integer.valueOf(message.substring(iSNb1, iENb1));
		int B = Integer.valueOf(message.substring(iSNb2, iENb2));
		int resultat = A+B;*/
		
		/*// Creation et envoi du message
		byte[] bufE2 = new String("R" + QID + ':' + resultat).getBytes();
		dpE = new DatagramPacket(bufE2, bufE2.length, adrDest);
		socket = new DatagramSocket();
		socket.send(dpE);
		System.out.println("Message envoyé " + "R" + QID + ':' + resultat);
	
		// Attente de la reponse 
		byte[] bufR2 = new byte[2048];
		dpR = new DatagramPacket(bufR2, bufR2.length);
		socket.receive(dpR);
		String message1 = new String(bufR2, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message1);
		
		// Creation et envoi du message
		byte[] bufE3 = new String("SCORE").getBytes();
		dpE = new DatagramPacket(bufE3, bufE3.length, adrDest);
		socket = new DatagramSocket();
		socket.send(dpE);
		System.out.println("Message envoyé");	
		
		// Attente de la reponse 
		byte[] bufR3 = new byte[2048];
		dpR = new DatagramPacket(bufR3, bufR3.length);
		socket.receive(dpR);
		String message2 = new String(bufR3, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message2);
		
		// Fermeture de la socket
		socket.close();
		System.out.println("Arret du client .");	*/	
	
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
