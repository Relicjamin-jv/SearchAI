import java.util.ArrayList;

public class DFS {
    ArrayList<Main.Node> graph;
    public boolean found;


    //Constructor
    DFS(ArrayList<Main.Node> graph){
        this.graph = graph; //copying the same node reference
    }

    public String runDFS(){
        for(int i = 0; i < graph.size(); i++){
            graph.get(i).setVisited(false); //sets all the nodes to not visited
        }
        for(int i = 0; i < graph.size(); i++) {
            String returnVal = DFS(graph.get(0));
            return returnVal;
        }
        return "";
    }

    public String DFS(Main.Node node){
        Main.Node currNode = node;
        node.setVisited(true);
        //if at goal return the path
        if(currNode.cityName.equals("Bu")){ //if destination
            found = true;
            ArrayList<Main.Node> path = new ArrayList<>();
            while(currNode != null){
                path.add(0, currNode);
                currNode = currNode.parent;
            }
            String returnVal = DFSPath(path);
            System.out.println(returnVal);
            return returnVal;
        }

        ArrayList<Main.Node> children = currNode.expand();
        for(Main.Node child: children){
            if(child.visited == false){
                DFS(child);
            }
        }
        if(found != true) {
            return "The path was not found\n";
        }else{
            return "";
        }
    }

    public String DFSPath(ArrayList<Main.Node> path){
        StringBuilder sb = new StringBuilder();
        int totalPathCost = 0;
        for(int i = 0; i < path.size(); i++){
            sb.append(path.get(i).cityName);
            if(i != path.size() - 1) {
                sb.append("->");
            }
            totalPathCost += path.get(i).cost;
        }
        sb.append("\nTotal Path Cost for DFS: ");
        sb.append(totalPathCost);
        return sb.toString();
    }

}
