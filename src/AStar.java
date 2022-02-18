import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar {
    PriorityQueue<Main.Node> open; //the cells to be evaluated
    ArrayList<Main.Node> closed; //the cells that are already evaled;
    ArrayList<Main.Node> graph; //a shallow copy of the graph nodes

    AStar(ArrayList<Main.Node> graph) {
        this.graph = graph;
        open = new PriorityQueue<Main.Node>((Main.Node n1, Main.Node n2) -> { //how to enter the nodes in the queue
            if (n1.fScore < n2.fScore) {
                return -1;
            } else {
                return 1;
            }
        }); //uses the comparator to turn this into a min priority queue
        closed = new ArrayList<>();
    }

    public String runAstar() {
        StringBuilder sb = new StringBuilder();
        //Put the start node in the open queue
        open.add(graph.get(0)); //gets the first node of the graph or the "start" node
        while (!open.isEmpty()) {
            //pop the lowest value from the open queue
            Main.Node currNode = open.poll();
            closed.add(currNode);
            currNode.visited = true;
            //check to see if the node we are at is the goalNode
            if (currNode.cityName.equals("Bu")) {
                int totalCost = 0;
                ArrayList<Main.Node> path = new ArrayList<>();
                while (currNode != null) {
//                    sb.append(currNode.cityName);
//                    sb.append("->");
                    path.add(0, currNode);
                    totalCost += currNode.cost;
                    currNode = currNode.parent;
                }
                sb.append(aStarPath(path));
                sb.append("\n");
                sb.append(totalCost);
                return sb.toString();
            }

            //generate the children and set there cost depending on the parent
            ArrayList<Main.Node> children = currNode.expandAStar();
            for (Main.Node child : children) {
                if (closed.contains(child)) { //if it is in the closed loop
                    continue; //jumps to the next iteration
                }

                //calculate the new fscore
                int tempFscore = 0;
                int tempGScore = 0;
                tempGScore = child.parent.gScore + child.gScore;
                tempFscore = tempGScore + child.hScore;

                if (open.contains(child)) {
                    if (child.fScore < tempFscore) {
                        continue;   //if the new score is not as good go to the next interation
                    }
                }

                //set the Fscore and Gscore, were using the same reference but it should be fine, were not make a new node each time
                child.fScore = tempFscore;
                open.add(child);
            }
        }

        return sb.toString();
    }

    public String aStarPath(ArrayList<Main.Node> path){
        StringBuilder sb = new StringBuilder();
        for(Main.Node pathNode: path){
            sb.append(pathNode.cityName);
            if(!pathNode.cityName.equals("Bu")){
                sb.append("->");
            }
        }
        return sb.toString();
    }


}
