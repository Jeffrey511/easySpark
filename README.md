# easySpark
1.It have a great number of demo.
spark study for myself.
sparkstreaming, sparkML and spark graph demos and  some of  sample from internet implement，


2.spark graphx 查找二跳节点的实现。
使用pergal接口实现的寻找二跳节点非常的消耗性能。
本文查找二跳节步骤如下：


 /** 步骤一：找邻居
      * 1. 每个顶点，将自己的id，发送给自己所有的邻居
      * 2. 每个顶点，将收到的所有邻居id，合并为一个List
      * 3. 对新List进行排序，并和原来的图进行关联，附到顶点之上
  * 步骤二：1. 遍历所有的Triplet，对2个好友的有序好友List进行扫描匹配，数出共同好友数，并将其更新到edge之上
  */

