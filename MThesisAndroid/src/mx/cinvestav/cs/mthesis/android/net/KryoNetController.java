package mx.cinvestav.cs.mthesis.android.net;

import java.io.IOException;

import android.util.Log;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

public class KryoNetController implements INetController {

	private static final String LOG_TAG = "KryoNetController";
	
	private static final KryoNetController INSTANCE = new KryoNetController();
	
	private KryoNetController() {
		client = new Client();
	}
	
	public static KryoNetController getInstance() {
		return INSTANCE;
	}
	
	private Client client;
	
	@Override
	public void connect() throws IOException {
		client.start();
		Network.registerEndPoint(client);
		
		client.addListener(new Listener() {
			
			@Override
			public void connected(Connection conn) {
				Log.d(LOG_TAG, "client connected");
			}
			
			@Override
			public void received(Connection conn, Object obj) {
				Log.d(LOG_TAG, "received a message from server");
			}
			
			@Override
			public void disconnected(Connection conn) {
				Log.d(LOG_TAG, "client disconnected");
			}
		});
		
		client.connect(TIMEOUT, SERVER_HOST, Network.TCP_PORT, Network.UDP_PORT);
	}

	@Override
	public void disconnect() {
		client.stop();
		client.close();
	}

	@Override
	public boolean isConnected() {
		return client.isConnected();
	}

	private static class Network {
		
		public static final int TCP_PORT = 5000;
		public static final int UDP_PORT = 6000;
		
		public static void registerEndPoint(EndPoint endPoint) {
			Kryo kryo = endPoint.getKryo();
			//kryo.register(Something.class);
		}
	}
}
