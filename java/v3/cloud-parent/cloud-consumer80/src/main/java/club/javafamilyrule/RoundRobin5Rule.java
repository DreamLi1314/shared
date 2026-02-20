package club.javafamilyrule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

/**
* @Description: 自定义 IRule, 每个节点轮询服务五次切换到下一个节点.
* @Warning:
* @Author: Jack Li
* @Package: cloud-consumer80 - RoundRobin5Rule
* @Date: May 30, 2020 10:54:09 AM
* @Version: 1.0.0
* @TimeComplexity: Required[O(n)] ---- Current[O(n^2)]
* @ExecuteResult: Success!
* @Status: Accepted
 */
public class RoundRobin5Rule extends AbstractLoadBalancerRule {

    private AtomicInteger nextServerCyclicCounter;
    private AtomicInteger currentProcessCount;

    private static Logger log = LoggerFactory.getLogger(RoundRobinRule.class);

    public RoundRobin5Rule() {
        nextServerCyclicCounter = new AtomicInteger(0);
        currentProcessCount = new AtomicInteger(0);
    }

    public RoundRobin5Rule(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int upCount = reachableServers.size();
            int serverCount = allServers.size();
            
            System.out.println("===serverCount==" + serverCount
            		+ "==upCount==" + upCount);
            

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            int nextServerIndex = 0;
            
            for (;;) {
            	int processCount = currentProcessCount.get();
                
                if(processCount >= 4) {
                	if(currentProcessCount.compareAndSet(processCount, 0)) {
                		nextServerIndex = incrementAndGetModulo(serverCount);
                		break;
                	}
                }
                else {
                	if(currentProcessCount.compareAndSet(processCount, (processCount + 1) % 5)) {
                		nextServerIndex = nextServerCyclicCounter.get();
                		break;
                	}
                }
                
                System.out.println("===processCount==" + processCount
                		+ "====nextServerIndex===" + nextServerIndex);
            }
            
            System.out.println("\n\n\n\n===nextServerIndex==" + nextServerIndex
            		+ "=====currentProcessCount===" + currentProcessCount.get());
            
            server = allServers.get(nextServerIndex);

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     *
     * @param modulo The modulo to bound the value of the counter.
     * @return The next value.
     */
    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

	@Override
	public void initWithNiwsConfig(IClientConfig arg0) {
		// TODO Auto-generated method stub
		
	}
}
