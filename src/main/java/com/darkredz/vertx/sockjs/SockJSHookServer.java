package com.darkredz.vertx.sockjs;

import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.impl.VertxInternal;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import com.darkredz.vertx.sockjs.EventBusBridge;
import org.vertx.java.core.sockjs.impl.DefaultSockJSServer;

public class SockJSHookServer extends DefaultSockJSServer {
  protected EventBusBridgeHook hook;
  private final VertxInternal vertx;

  public SockJSHookServer(VertxInternal vertx, HttpServer httpServer) {
	super(vertx, httpServer);
    this.vertx = vertx;
  }
  
  public void setupHook(EventBusBridgeHook hook) {
	this.hook = hook;
  }
  
  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }

  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted,
                     long authTimeout) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted, authTimeout);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }

  public void bridge(JsonObject sjsConfig, JsonArray inboundPermitted, JsonArray outboundPermitted,
                     long authTimeout, String authAddress) {
	EventBusBridge busBridge = new EventBusBridge(vertx, inboundPermitted, outboundPermitted, authTimeout, authAddress);
	if(hook != null){
		busBridge.setHook(hook);
	}
    installApp(sjsConfig, busBridge);
  }
  
}
