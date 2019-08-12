package org.apache.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.security.UserGroupInformation;

@ProtocolInfo(protocolName = "Server", protocolVersion = 1)
public interface MyServer {
   String test(Emp emp,Dep dep);

}

class OKServer implements MyServer{

  @Override public String test(Emp emp,Dep dep) {
    System.out.println("Hi");
    return "server:"+" Name:"+emp.getName()+" Lname :"+emp.getMname()+" DName"+dep.name;
  }

  public static void main(String[] args) throws IOException {
     OKServer okServer=new OKServer();
    Configuration conf = new Configuration();
    RPC.Server server=new RPC.Builder(conf).setBindAddress("127.0.0.1")
          .setProtocol(MyServer.class)
          .setInstance(okServer).setPort(4000).build();
    server.start();

  }

  public MyServer getClient() throws IOException {
    Configuration conf=new Configuration();
    //RPC.setProtocolEngine(conf,MyServer.class, ProtobufRpcEngine.class);
    SocketFactory defaultSocketFactory = NetUtils.getDefaultSocketFactory(conf);
    MyServer proxy = RPC.getProxy(MyServer.class, RPC.getProtocolVersion(MyServer.class),
        new InetSocketAddress("127.0.0.1", 4000), conf,defaultSocketFactory);
    RPC.getProxy(MyServer.class,RPC.getProtocolVersion(MyServer.class),new InetSocketAddress("127.0.0.1", 4000),
        UserGroupInformation.getLoginUser(),conf,defaultSocketFactory,1000);
    return proxy;

  }


}

