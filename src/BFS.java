import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


//Arad to Bucharest
public class BFS {
    ArrayList<Main.Node> graph; //index 0 should contain the starting node
    StringBuilder sb= new StringBuilder();
    BFS(ArrayList<Main.Node> graph){
        this.graph = new ArrayList<>(graph); //copys the array list
    }

    public String runBFS(){
        Queue<Main.Node> q = new LinkedList<>();
        boolean foundState = false;
        Main.Node goalNode = null;
        q.add(graph.get(0)); //grabs the arad node

        while(!graph.isEmpty() && !foundState){ //run until found the end state or graph isn't empty
            Main.Node node = q.remove(); //Dequeue to look at and examine
            if(node.cityName.equals("Bu")){
                foundState = true;
                goalNode = node;
            }else{
                //expand
                for(Main.Node child: node.expand()){
                    q.add(child);
                }
            }
        }
        if(foundState){
            int totalPathCost = 0;
            System.out.println("Printing out path\n");
            ArrayList<Main.Node> path = new ArrayList<>();
            Main.Node current = goalNode;
            while(current!=null){
                path.add(0, current); //adds it to the first index and pushes the rest down
//                for (int i = 0; i < current.neighbors.size(); i++){ //search for path cost    //THIS WAY THROWS SILLY NULL POINTER ERROR
//                    if(current.neighbors.get(i).cityName.equals(current.parent.cityName)){    //AND I COULDN'T FIGURE OUT WHAT IT WAS
//                        totalPathCost += current.neighbors.get(i).distance;                   //VERY SAD! SO INSTEAD I JUST MADE COST MEMBER VAR IN NODE
//                        System.out.println(totalPathCost);                                    //TO HOLD THE WEIGHT CALC
//                    }
//                }
                totalPathCost += current.cost;
                current = current.parent;
            }

            for(Main.Node n:path){
                sb.append(n.cityName);
                if(n != goalNode) {
                    sb.append("->");
                }
            }
            sb.append("\ntotal path cost: ");
            sb.append(totalPathCost);
            return sb.toString();
        }

        return "Did not find a path\n";
    }

}
