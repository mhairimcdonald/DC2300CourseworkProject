package warehouse.model.path;
import java.util.List;

/**
 * The AStarNode class, along with the AStarSearch class, implements a generic A* search algorithm
 * @author mhmcdona
 *
 */
public abstract class AStarNode implements Comparable {

  AStarNode pathParent;
  float costFromStart;
  float estimatedCostToGoal;


  public float getCost() {
    return costFromStart + estimatedCostToGoal;
  }


  public int compareTo(Object other) {
    float thisValue = this.getCost();
    float otherValue = ((AStarNode)other).getCost();

    float v = thisValue - otherValue;
    return (v>0)?1:(v<0)?-1:0; // sign function
  }
  
  /**
   * Gets the cost between this node and the specified adjacent (neighbour/child) node
   * @param node
   * @return float representing cost
   */
  public abstract float getCost(AStarNode node);

  
  /**
   * Gets the estimated cost between this node and the specified node. The edtimated cost should never exceed the true cost,
   * @param node
   * @return float representing cost
   */
  public abstract float getEstimatedCost(AStarNode node);

  
  /**
   * Gets the children (neighbours/adjacent nodes) of this node
   * @return List of neighbouring nodes
   */
  public abstract List getNeighbors();
}  