package org.apache.test;

public class SerDe {

  public static void main(String[] args) {
    SerDe serDe=new SerDe();
    Node node = serDe.prepareNode();
    String serilize = serDe.serilize(node);
    System.out.println(serilize);

  }

  private String serilize(Node node) {

    if(node==null){
      return  "";
    }
    else {
      return serilize(node.left)+ " "+serilize(node.right)+" "+node.toString();
    }
  }

  public Node prepareNode(){
    Node left=new Node("FilterL",null,null);
    Node right=new Node("FilterR",null,null);
    Node scanLeft=new Node("ScanLeft",left,null);
    Node scanRight=new Node("ScanRight",right,null);
    Node scan=new Node("Join",scanLeft,scanRight);
    Node join=new Node("Full",scan,null);

    return join;
  }


  class Node{
    Node left;
    Node right;
    String name;

    Node(String name,Node left,Node right){
      this.name=name;
      this.left=left;
      this.right=right;
    }

    @Override public String toString() {
      return name;
    }
  }
}
