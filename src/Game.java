import java.io.IOException;

public class Game {

	// 
	static ClientTCP clientTCP;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String ipSrv	= args[0];
		int portSrv 	= Integer.valueOf(args[1]);
		//int portClient 	= Integer.valueOf(args[2]);
		
		clientTCP = new ClientTCP();
		clientTCP.connect(ipSrv,portSrv);
		clientTCP.execute();
/*		for (int i =0;i < 10;i++) {
			clientTCP.execute();
		}*/
		
		//clientTCP.close();
	}
}
