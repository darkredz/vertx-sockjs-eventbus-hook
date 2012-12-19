package com.darkredz.vertx.sockjs;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSSocket;

/**
 * In order to use SockJSHookServer, your hook class have to implement this interface
 * @author <a href="http://darkredz.com/">Leng Sheng Hong</a>
 * @version 1.0
 * @since 1.0
 *
 */
public interface EventBusBridgeHook {

	  /**
	   * The socket has been closed
	   * @param sock The socket
	   * @since 1.0
	   */
	  void handleSocketClosed(SockJSSocket sock);

	  /**
	   * Client is sending or publishing on the socket
	   * @param sock The sock
	   * @param send if true it's a send else it's a publish
	   * @param msg The message
	   * @param address The address the message is being sent/published to
	   * @return true To allow the send/publish to occur, false otherwise
	   * @since 1.0
	   */
	  boolean handleSendOrPub(SockJSSocket sock, boolean send, JsonObject msg, String address);

	  /**
	   * Client is registering a handler
	   * @param sock The socket
	   * @param address The address
	   * @return true to let the registration occur, false otherwise
	   * @since 1.0
	   */
	  boolean handleRegister(SockJSSocket sock, String address);

	  /**
	   * Client is unregistering a handler
	   * @param sock The socket
	   * @param address The address
	   * @since 1.0
	   */
	  boolean handleUnregister(SockJSSocket sock, String address);
}
