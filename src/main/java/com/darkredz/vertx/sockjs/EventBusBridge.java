package com.darkredz.vertx.sockjs;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSSocket;
import com.darkredz.vertx.sockjs.EventBusBridgeHook;

/**
 * EventBusBridge class which accept a hook instance to handle event bus bridge events.
 * @author <a href="http://darkredz.com/">Leng Sheng Hong</a>
 * @version 1.0
 * @since 1.0
 */
public class EventBusBridge extends org.vertx.java.core.sockjs.EventBusBridge {
  protected EventBusBridgeHook hook;
  
  public EventBusBridge(Vertx vertx, JsonArray inboundPermitted, JsonArray outboundPermitted) {
	super(vertx, inboundPermitted, outboundPermitted);
  }

  public EventBusBridge(Vertx vertx, JsonArray inboundPermitted, JsonArray outboundPermitted,
                        long authTimeout) {
	super(vertx, inboundPermitted, outboundPermitted, authTimeout);
  }

  public EventBusBridge(Vertx vertx, JsonArray inboundPermitted, JsonArray outboundPermitted,
                        long authTimeout,
                        String authAddress) {
	super(vertx, inboundPermitted, outboundPermitted, authTimeout, authAddress);  
  }
  
  // Hook
  // ==============================
  /**
   * Set hook object
   * @param hook
   * @since 1.0
   */
  public void setHook(EventBusBridgeHook hook) {
    this.hook = hook;
  }
  
  /**
   * Get hook object
   * @return
   * @since 1.0
   */
  public EventBusBridgeHook getHook() {
    return hook;
  }
  // Override these to get hooks into the bridge events
  // ==================================================

  /**
   * The socket has been closed
   * @param sock The socket
   * @since 1.0
   */
  protected void handleSocketClosed(SockJSSocket sock) {
    if(hook != null) {
      hook.handleSocketClosed(sock);
    }
  }

  /**
   * Client is sending or publishing on the socket
   * @param sock The sock
   * @param send if true it's a send else it's a publish
   * @param msg The message
   * @param address The address the message is being sent/published to
   * @return true To allow the send/publish to occur, false otherwise
   * @since 1.0
   */
  protected boolean handleSendOrPub(SockJSSocket sock, boolean send, JsonObject msg, String address) {
	if(hook != null) {
       return hook.handleSendOrPub(sock, send, msg, address);    		   
	}
    return true;
  }

  /**
   * Client is registering a handler
   * @param sock The socket
   * @param address The address
   * @return true to let the registration occur, false otherwise
   * @since 1.0
   */
  protected boolean handleRegister(SockJSSocket sock, String address) {
    if(hook != null) {
       return hook.handleRegister(sock, address);    		   
	}
    return true;
  }

  /**
   * Client is unregistering a handler
   * @param sock The socket
   * @param address The address
   * @since 1.0
   */
  protected boolean handleUnregister(SockJSSocket sock, String address) {
	if(hook != null) {
	   return hook.handleUnregister(sock, address);    		   		
	}
    return true;
  }
	  
}
