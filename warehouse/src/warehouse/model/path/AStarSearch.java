package warehouse.model.path;
import java.util.*;

/**
 * The AStarSearch class, along with the AStarNode class, implements a generic A* search algorithm
 * @author mhmcdona
 *
 */
public class AStarSearch {


/**
 * A priority list; objects in the list are ordered by priority determined by the object's Comparable interface
 * @author mhmcdona
 *
 */
  public static class PriorityList extends LinkedList {

    public void add(Comparable object) {
      for (int i=0; i<size(); i++) {
        if (object.compareTo(get(i)) <= 0) {
          add(i, object);
          return;
        }
      }
      addLast(object);
    }
  }

  /**
   * Construct the path, not including the start node
   * @author mhmcdona
   * @param node
   * @return List 
   */
  protected List constructPath(AStarNode node) {
    LinkedList path = new LinkedList();
    while (node.pathParent != null) {
      path.addFirst(node);
      node = node.pathParent;
    }
    return path;
  }

  
  /**
   * Find the path from the start node to the end node.
   * @author mhmcdona
   * @param startNode
   * @param goalNode
   * @return List of AStartNodes, or null if path not found
   */
  public List findPath(AStarNode startNode, AStarNode goalNode) {

    PriorityList openList = new PriorityList();
    LinkedList closedList = new LinkedList();

    startNode.costFromStart = 0;
    startNode.estimatedCostToGoal =
      startNode.getEstimatedCost(goalNode);
    startNode.pathParent = null;
    openList.add(startNode);

    while (!openList.isEmpty()) {
      AStarNode node = (AStarNode)openList.removeFirst();
      if (node == goalNode) {
        // construct the path from start to goal
        return constructPath(goalNode);
      }

      List neighbors = node.getNeighbors();
      for (int i=0; i<neighbors.size(); i++) {
        AStarNode neighborNode =
          (AStarNode)neighbors.get(i);
        boolean isOpen = openList.contains(neighborNode);
        boolean isClosed =
          closedList.contains(neighborNode);
        float costFromStart = node.costFromStart +
          node.getCost(neighborNode);

        // check if the neighbour node has not been traversed or if a shorter path to this neighbour node is found.
        if ((!isOpen && !isClosed) ||
          costFromStart < neighborNode.costFromStart)
        {
          neighborNode.pathParent = node;
          neighborNode.costFromStart = costFromStart;
          neighborNode.estimatedCostToGoal =
            neighborNode.getEstimatedCost(goalNode);
          if (isClosed) {
            closedList.remove(neighborNode);
          }
          if (!isOpen) {
            openList.add(neighborNode);
          }
        }
      }
      closedList.add(node);
    }

    // no path found
    return null;
  }

}