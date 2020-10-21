package com.atguigu.springcloud.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyselfRule {

    /**
     * 负载均衡算法：rest接口的第几次请求数%服务器集群数量=实际调用的集群机器下标。每次重启服务，rest接口计数从0开始。
     *
     * ▪️RandomRule
     * 该策略实现了从服务实例清单中随机选择一个服务实例的功能。具体的选择逻辑在一个while(server==null)循环之内，而根据选择逻辑的实现，
     * 正常情况下每次选择都应该选出一个服务实例，如果出现死循环获取不到服务实例时，则很有可能存在并发的Bug。
     * ▪️RoundRobinRule
     * 该策略实现了按照线性轮询的方式依次选择每个服务实例的功能。其详细结构与RandomRule非常类似。除了循环条件不同外，
     * 就是从可用列表中获取所谓的逻辑不同。从循环条件中，我们可以看到增加了一个count计数变量，该变量会在每次循环之后累加，也就是说，
     * 如果一直选择不到server超过10次，那么就会结束尝试，并打印一个警告信息No available alive servers after 10tries from load balancer
     * ▪️RetryRule
     * 该策略实现了一个具备重试机制的实例选择功能。在其内部还定义了一个IRule对象，默认使用了RoudRobinRule实例。
     * 而在choose方法中则实现了对内部定义的策略进行反复尝试的策略，若期间能够选择到具体的服务实例就返回，若选择不到就根据设置结束时间为
     * 阀值(maxRetryMillis参数定义的值+choose方法开始执行的时间戳)，当超过该阀值就返回null。
     * ▪️WeightedResponseTimeRule
     * 该策略是对RoundRobinRule的扩展，增加了根据实例等运行情况来计算权重，并根据权重来挑选实例，以达到更优的分配效果，它的实现主要有三个核心内容：
     * 定时任务、权重计算、例选择
     * ▪️ClientConfigEnabledRoundRobinRule
     * 该策略较为特殊，我们一般不直接使用它，因为它本身并没有实现什么特殊的处理逻辑，正如源码所示，在它的内部定义了一个RoundRobinRule策略，
     * 而choose函数的实现也正是使用了RoundRobinRule的线性轮询机制，所以它实现的功能实际上与RoundRobinRule相同，那么定义它有什么特殊的用处呢？
     * 虽然我们不会直接使用该策略，但是通过继承该策略，默认的choose就实现了线性轮询机制，在子类中做一些高级策略时通常有可能会存在一些无法实施的情况，
     * 那么就可以用父类的实现作为备选。
     * ▪️BestAvailableRule
     * 该策略继承自ClientConfigEnabledRoundRobinRule，在实现中它注入了负载均衡器的统计对象LoadBalancerStats，同时在具体的choose算法中
     * 利用LoadBalancerStats保存的实例统计信息来选择满足要求的实例。从如下源码中我们可以看到，它通过遍历负载均衡器中维护的所有服务实例，
     * 会过滤掉故障的实例，并找出并发请求数最小的一个，所以该策略的特性是可选出最空闲的实例。
     * 同时，由于该算法的核心依据是统计对象LoadBalancerStats，当其为空的时候，该策略是无法执行的。所以从源码中我们可以看到，当loadBalancerStats为空的时候，
     * 它会采用父类的线性轮询策略，正如我们在介绍ClientConfigEnabledRoundRobinRule时那样，它的子类在无法满足实现高级策略的时候，可以使用线性轮询策略的特性。
     * ▪️PredicateBasedRule
     * 这是一个抽象策略，它也继承了ClientConfigEnabledRoundRobinRule，从其命名中可以猜出这是一个基于Predicate实现的策略，Predicate是Google Guava Collections工具对集合进行过滤掉条件接口。
     * Google Guava Collections是一个对Java Collections Framework增强和扩展的开源项目。虽然Java Collections Framework已经能够满足我们大多数抢空下使用集合的要求，
     * 但是当遇到一些特殊情况时，我们的代码会比较冗长且容易出错。Google Guava Collections可以帮助我们让集合操作代码更为简短精炼并大大增强代码的可读性。
     * ▪️AvailabilityFilteringRule
     * 该策略继承自上面介绍的抽象策略PredicateBasedRule，所以它也继承了“先过滤清单，再轮询选择”的的基本处理逻辑，其中过滤条件使用了AvailabilityPredicate。
     * 简单地说，该策略通过线性抽样的方式直接尝试寻找可用且较空闲的实例来使用，优化了父类每次都要遍历所有实例的开销。
     * ▪️ZoneAvoidanceRule
     * 该策略我们在介绍负载均衡器ZoneAwareLoadBalancer时已经提到过，它也是PredicateBasedRule的具体实现类，在之前的介绍中主要针对ZoneAvoidanceRule中用于
     * 选择Zone区域策略的一些静态函数，比如createSnapshot(LoadBalancerStats lbStats)、getAvailableZones(Map snapshot, double triggeringLoad,double triggeringBlackoutPercentage)。
     * 在这里我们将详细看看ZoneAvoidanceRule作为服务实例过滤条件的实现原理。从下面ZoneAvoidanceRule的源码片段中可以看到，它使用了CompositePredicate来进行服务实例清单的过滤。
     * 这是一个组合过来条件，在其构造函数中，它以ZoneAvoidanceRule为主过滤条件，AvailabilityPredicate为次过滤条件初始化了组合过滤条件的实例。
     */
    @Bean
    public IRule myRule() {
        //随机
        return new RandomRule();
    }

}
