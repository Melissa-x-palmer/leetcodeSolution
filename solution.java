package com.virtual.world.api;


import java.util.*;

class deleteThisLater {


    public boolean isInequalitiesValid(List<String> inequalities){

        GraphNode graphNode = generateGraph(inequalities);
        boolean doesGraphHaveCycle = doesGraphHaveCycle(graphNode);

        return !doesGraphHaveCycle;

    }

    private boolean doesGraphHaveCycle(GraphNode graphNode){

        List<Node> nodesInGraph = graphNode.getNodesInGraph();
        for (Node node: nodesInGraph){

            if (isComponentCyclic(node)){
                return true;
            }

        }
        return false;
    }

    private boolean isComponentCyclic(Node node){

        Queue<Node> queue =new LinkedList<>();
        queue.add(node);

        HashSet<Node> seenNodes = new HashSet<>();

        while (!queue.isEmpty()){
            Node poppedNode = queue.poll();
            if (seenNodes.contains(poppedNode)){
                return true;
            } else{
                seenNodes.add(poppedNode);
                for (Node neigh: poppedNode.getNeighbors()){
                    queue.add(neigh);
                }
            }
        }

        return false;
    }

    private GraphNode generateGraph(List<String> inequalities){


        GraphNode graphNode = new GraphNode();

        // put every char into a node and add it to the graph
        HashSet<Character> lettersInGraph = generateLetters(inequalities);
        List<Node> nodes = new ArrayList<Node>();
        for (Character character: lettersInGraph){
            Node node = new Node(character, new ArrayList<Node>());
            nodes.add(node);
        }

        graphNode.setNodesInGraph(nodes);

        // account for neighbors
        for (String ineq: inequalities){

            Character firstLetter = ineq.charAt(0);
            Character inequalitySymbol = ineq.charAt(1);
            Character secondLetter = ineq.charAt(2);

            for (Node node: graphNode.getNodesInGraph()){
                if (node.getLetter()==firstLetter){

                    for (Node neighborFound: graphNode.getNodesInGraph()){

                        if (neighborFound.getLetter()==secondLetter){


                            if (inequalitySymbol.equals("<")){
                                node.getNeighbors().add(neighborFound);
                            } else{
                                neighborFound.getNeighbors().add(node);
                            }
                        }
                    }
                }
            }

        }

        return graphNode;
    }

    private HashSet<Character> generateLetters(List<String> inequalities){
        HashSet<Character> seenLetters = new HashSet<>();
        for (String ineq: inequalities){

            Character firstLetter = ineq.charAt(0);
            Character inequalitySymbol = ineq.charAt(1);
            Character secondLetter = ineq.charAt(2);

            if (!seenLetters.contains(firstLetter)){
                seenLetters.add(firstLetter);
            }
            if (!seenLetters.contains(secondLetter)){
                seenLetters.add(secondLetter);
            }


        }
        return seenLetters;
    }



    public static void main(String[] args) {

        deleteThisLater deleteThisLater = new deleteThisLater();

        // TEST 1 -> should be false
        List<String> inequalities1 = new ArrayList<>();
        inequalities1.add("a>b");
        inequalities1.add("b<a");
        boolean valid1 = deleteThisLater.isInequalitiesValid(inequalities1);
        System.out.println(valid1);

        // TST 2 -> should be true
        List<String> inequalities2 = new ArrayList<>();
        inequalities2.add("a>b");
        inequalities2.add("b>c");
        inequalities2.add("c>d");
        boolean valid2 = deleteThisLater.isInequalitiesValid(inequalities2);
        System.out.println(valid2);


        //TEST 3 -> should be false
        List<String> inequalities3 = new ArrayList<>();
        inequalities3.add("a>b");
        inequalities3.add("b>c");
        inequalities3.add("c>a");
        boolean valid3 = deleteThisLater.isInequalitiesValid(inequalities3);
        System.out.println(valid3);

    }


}


class Node{
    char letter;
    List<Node> neighbors;

    public Node(char letter, List<Node> neighbors) {
        this.letter = letter;
        this.neighbors = neighbors;
    }


    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }
}

class GraphNode{

    List<Node> nodesInGraph;

    public List<Node> getNodesInGraph() {
        return nodesInGraph;
    }

    public void setNodesInGraph(List<Node> nodesInGraph) {
        this.nodesInGraph = nodesInGraph;
    }

}
