# SmartGrid
Smart grid is a simulation of a smart power grid. Each component of the smart grid is an intelligent agent looking to maximize its profit while minimizing it's expenses. Through experimentation we hope to find local and global and local convergence on agent buy and sell prices.

# Agents

## Consumer
The consumer agent's base consumption is based on the hour of the day. There is also a random variance, increasing and decreasing the amount of consumption at that time. The consumer agent can only buy power and as such is only concerned with minimizing it's expense. The agent also is required to purchase up to the amount it consumed in this instance of time, and than stop.

## Solar Generator
The solar generator does not buy power but produces it depending on the time of day. The solar generator either produces power or it does not. The existing model starts generating power at time nine and stops at time 16. However there is a random variable of +- 1, so it can start generating an hour before or after each of those times. The solar generator must sell all of its available power, because any that is not sold is lost at the end of the turn. The power generation per turn is a fixed number, but also has a random variability, so generation can be slightly higher or lower each turn. The generator agents are only concerned with maximizing their profit each turn.

## Wind Generator
The wind generator is almost exactly the same as the solar generator. The only difference between the two is the time of generation. The current model has the wind generating the same base power as solar, at every time solar is not generating power. We expect to change this model at a later point in time to include more data point than start and end generation times at uniform power.
 
## Grid Storage
The grid storage is the most advanced of the agents. It can buy, sell, and keep power each turn. This means it has to determine how much it saves each turn.  The grid storage has a maximum capacity it is allowed to hold. Currently it aims to keep its capacity towards the median. It has two threshold values If it exceeds the max threshold of 4/5 it will only sell this round. If it goes below the min threshold of 1/5 capacity it will only buy this round. If it is between the thresholds it will attempt to both buy and sell up to 10% and 90% capacity ideally  resulting in a net remaining power of 50%. If time permits we will add additional complexity to this model.  Any power the agent keeps, decays in the next round according to a decay function. This loss in power will affect the quantity and pricing when the heuristics are fully implemented. 

## Main Grid*
All of the agents explained so far have limited buying and selling power. The main grid has unlimited, but is not strictly speaking an agent. It has a fixed buy and sell price. And has an unlimited quantity to buy and sell. It is included in the system to stabilize it. Any overabundance of power generation, or deficit can be handled by the main grid. However the buy price is extremely low, and the sell price is really high, so agents would be better off buying from another agent.  We expect the price convergence to fall between the buy and sell prices of the main grid.

# Auction Process
The auction system rewards buyers who set their buy price high, and on the same token reward sellers with low sell prices.  After all agents have decided on their prices for this round, the buyers are sorted, by their buy price (highest to lowest). It than obtains a list of all the potential sellers connected to the buyer. They are than sorted by sell price (lowest to highest). The buyer will attempt to buy the full amount it requires. If the seller can satisfy the requirement it will, and the next buyer will go. If the seller can not fully satisfy the requirement it will give the buyer all of its resources and the next seller will continue the process with the same buyer. All agents are connected to the main grid, so whenever the main grid comes up in the ordering the remaining agents will not sell/buy their power. We are considering making the main grid a failsafe that can only sell after all other agents have, but we feel this would remove the convergence bounds on the system. All agents follow this process (highest buyer to lowest seller matching) until all have been satisfied than the next turn begins.

#Processing turns (Coming Soon)
