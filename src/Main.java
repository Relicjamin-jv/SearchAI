import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class Main {
    public static ArrayList<Node> nodes = new ArrayList<>(); //list of all the nodes I have
    public static void main(String[] args) {
        //read in the data from the text files provided for this project
        String graphData = openAndReadFile("src/TextFiles/neighbors.txt");
        Scanner reader = new Scanner(graphData);
        ArrayList<String> inputFromFile = new ArrayList<>();
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            Scanner lineReader = new Scanner(data);
            while (lineReader.hasNext()) {
                inputFromFile.add(lineReader.next());
            }
            ArrayList<Neighbor> neighbors = new ArrayList<>();
            for (int i = 1; i < inputFromFile.size(); i += 2) {
                neighbors.add(new Neighbor(inputFromFile.get(i), Integer.valueOf(inputFromFile.get(i + 1))));
            }
                //have all the neighbors now, i must now make the node
                Node node = new Node(inputFromFile.get(0), neighbors, null, 0);
                neighbors.clear();
                inputFromFile.clear();
                nodes.add(node); //adds the node
            lineReader.close();
        }
        //printing out all the nodes
//        for(Node node : nodes){
//            System.out.println(node.toString());
//        }

        //test the expanding for Node works
//        for(int i = 0; i < nodes.size(); i++) {
//            ArrayList<Node> expandedSet = nodes.get(i).expand();
//            for (Node node : expandedSet) {
//                System.out.println(node.toString());
//                System.out.println("Parent: " + node.parent); //also prints the toString when parent is called kinda annoying but to lazy to figure out
//            }
//        }

        //run the algor. on the nodes
        //BFS: sends in the Graph and the start node
        BFS bfs = new BFS(nodes);
        String returnBFSVal = bfs.runBFS();
        System.out.println(returnBFSVal);
        //print out the different paths with their path cost
        DFS dfs = new DFS(nodes);  //need to send a whole another array of nodes, it's referencing the same nodes array
        String returnDFSVal = dfs.runDFS();
        System.out.println(returnDFSVal);
        //DFS: Send in the graph start node is at index 0


        //closing
        reader.close();
    }

    public static String openAndReadFile(String filePath) {
        try {
            StringBuilder sb = new StringBuilder();
            File file = new File(filePath);
            Scanner scn = new Scanner(file);
            while (scn.hasNextLine()) {
                String nextLine = scn.nextLine();
                sb.append(nextLine + "\n");
            }
            scn.close();
            return sb.toString(); //return the string that was read from the file
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be opened/found");
            e.printStackTrace();
        }
        return "Failed";
    }

    public static class Node {
        public String cityName;
        public ArrayList<Neighbor> neighbors;
        public Node parent;
        public int cost = 0;
        public boolean visited;

        Node(String cityName, ArrayList<Neighbor> neighbors, Node parent, int cost) {
            this.cityName = cityName;
            this.neighbors = new ArrayList<>(neighbors);
            this.parent = parent;
            this.cost = cost;
        }

        //reset func. to reset the nodes between the use of algorithms.

        public void setParent(Node pNode){
            this.parent = pNode;
        }

        public void setCost(int cost){
            this.cost = cost;
        }

        public void setVisited(boolean vistited){
            this.visited = vistited;
        }

        public ArrayList<Node> expand(){
            ArrayList<Node> children = new ArrayList<>();
            //grab the nodes that the neighbors are referencing to
            for(int i = 0; i < this.neighbors.size(); i++){
                Neighbor cityChild = neighbors.get(i); //grabs neighbor data
                for(int j = 0; j < nodes.size(); j++) {
                    if(nodes.get(j).cityName.equals(cityChild.cityName)){
                        if(nodes.get(j).parent == null && this.parent != nodes.get(j)) { //if the parent node hasn't been expanded yet
                            nodes.get(j).setParent(this); //set the parent
                            nodes.get(j).setCost(cityChild.distance);
                            children.add(nodes.get(j)); //returns the nodes that are children of the current node
                        }
                    }
                }
            }
            return children;
        }

        public String toStringPrint(){
            StringBuilder sb = new StringBuilder();
            sb.append(this.cityName + "\n" + "Neighbors:\n");
            for(int i = 0; i < this.neighbors.size(); i++){
                sb.append("\t" + neighbors.get(i).cityName + ":" + neighbors.get(i).distance + "\n");
            }
            return sb.toString();
        }
    }

    public static class Neighbor {
        public String cityName;
        public int distance;

        Neighbor(String cityName, int distance) {
            this.cityName = cityName;
            this.distance = distance;
        }
    }
}
