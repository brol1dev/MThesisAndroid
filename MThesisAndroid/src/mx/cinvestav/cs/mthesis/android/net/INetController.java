package mx.cinvestav.cs.mthesis.android.net;

interface INetController {

	static final int TIMEOUT = 5000;
	static final String SERVER_HOST = "10.200.30.221";
	
	public void connect() throws Exception;
	public void disconnect();
	public boolean isConnected();
}
