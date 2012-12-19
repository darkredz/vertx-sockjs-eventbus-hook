vertx-sockjs-eventbus-hook
==========================

SockJS EventBusBridge Hook module

## Name

The module name is `com.darkredz.sockjs-eventbus-hook`.

## Configuration

This module does not provide any special configuration, it is based on the default SockJS server in Vert.x. If you want to change the configuration of the SockJS server, please see its own documentation and usage on Vert.x website.
It is an non runnable module.

## Examples

### Verticle

You can use this module in a simple Verticle like with your custom hook class to handle eventbus bridge events:

Hook class
```java
import com.darkredz.vertx.sockjs.EventBusBridgeHook;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.core.sockjs.SockJSSocket;

public class ServerHook implements EventBusBridgeHook {
  private Logger logger;
    
  public ServerHook(final Logger logger) {
    this.logger = logger;
  }
  
  public boolean handleRegister(final SockJSSocket sock, final String address) {
    logger.info(sock + " connected and registered at " + address);
    return true;   //return false to disallow the address channel registration
  }  
  
  public boolean handleSendOrPub(final SockJSSocket sock, final boolean send, final JsonObject msg, final String address) {
    if(send == true) {
      logger.info(sock + " sending message to " + address);
      logger.info("Message sent: " + msg.toString());
    } else {
      logger.info(sock + " publishing message to " + address);
      logger.info("Message sent: " + msg.toString());
    }
    return true;
  }
  
  public void handleSocketClosed(final SockJSSocket sock) {
    logger.info(sock + " disconnected");
  }  
  
  public boolean handleUnregister(final SockJSSocket sock, final String address) {
    logger.info(sock + " unregistered from address " + address);    
    return true;
  }
}
```

Verticle (for client side code look at eventbus bridge example at https://github.com/vert-x/vert.x/tree/master/vertx-examples/src/main/java/eventbusbridge)
```java
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;
import com.darkredz.vertx.sockjs.SockJSHookServer;

public class BridgeServer extends Verticle {

  public void start() throws Exception {
    Logger logger = this.container.getLogger();
    HttpServer server = vertx.createHttpServer();

    // Also serve the static resources. In real life this would probably be done by a CDN
    server.requestHandler(new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        if (req.path.equals("/")) req.response.sendFile("eventbusbridge/index.html"); // Serve the index.html
        if (req.path.endsWith("vertxbus.js")) req.response.sendFile("eventbusbridge/vertxbus.js"); // Serve the js
      }
    });

    JsonArray permitted = new JsonArray();
    permitted.add(new JsonObject()); // Let everything through
    
    SockJSServer sockJSServer = new SockJSHookServer(((VertxInternal) this.vertx), server);
    final ServerHook hook = new ServerHook(logger);
    sockJSServer.setupHook(hook);
    
    sockJSServer.bridge(new JsonObject().putString("prefix", "/eventbus"), permitted, permitted);

    server.listen(8080);
  }
}
```

The code is located in `samples/verticle/BridgeServer.java`.

You can run this module by `vertx run` but before run this simple verticle, you should put some jars to the classpath.

Here is some options you can use.
* simply add `sockjs-eventbus-hook-v1.0.jar` in the `dist` directory to your `VERTX_HOME/libs` directory
* or use `-cp` option when you run the verticle.

Now, you can run the verticle like:
```
samples/verticle> vertx run BridgeServer.java
```
You can also compile the jar along with your app's source code and vertx run the verticle.

If you installed it with `vertx install` you can run the verticle with `-cp` without compiling the module into your app source.
```
samples/verticle> vertx run BridgeServer.java -cp "classes:mods/com.darkredz.sockjs-eventbus-hook-v1.0"
```
