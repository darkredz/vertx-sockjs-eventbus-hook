package com.darkredz.vertx.sockjs;

import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.impl.VertxInternal;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import com.darkredz.vertx.sockjs.EventBusBridge;
import org.vertx.java.core.sockjs.impl.DefaultSockJSServer;

/**
 * SockJSHookServer is based on the default vert.x SockJS server which accepts a class as hook to handle eventbus bridge events
 * such as handling socket close, address register/unregister, handling sending and publishing of messages.
 * 
 * @author <a href="http://darkredz.com/">Leng Sheng Hong</a>
 * @version 1.0
 * @since 1.0
 */
public class SockJSHookServer extends DefaultSockJSServer {
  protected EventBusBridgeHook hook;
  private final VertxInternal vertx;

  /**
   * Construtor, pass in vertx object from your Verticle class
   * @param vertx
   * @param httpServer
   * @since 1.0
   */
  public SockJSHookServer(VertxInternal vertx, HttpServer httpServer) {
	super(vertx, httpServer);
    this.vertx = vertx;
  }
  
  /**
   * Setup sockjs hook class
   * @param hook The hook instance to handle sockjs eventbusbridge events
   * @since 1.0
   */
  public void setupHook(EventBusBridgeHook hook) {
	this.hook = hook;
  }
  
  @Override
  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }

  @Override
  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted,
                     long authTimeout) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted, authTimeout);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }
  
  @Override
  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted,
                     long authTimeout, String authAddress) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted, authTimeout, authAddress);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }
  
}
