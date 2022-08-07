package datastucture.graph.minspanningtree;

/* Prim算法   没有Kruskal简洁
1 从任意节点出发
2 某个点加入被选中的点后 解锁这个点 找出所有新解锁的边
3 在所有解锁的边中选还未被选择的权值最小的(cur) 看是否成环
4 若cur加入成环  则考察解锁的其他边中最小的边  重复3
4 若cur加入不成环  则要当前边  将该边指向的节点加入选取的点中  重复2
 */
public class Prim {


}
