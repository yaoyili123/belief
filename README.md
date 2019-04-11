# belief
App developing course work，View层界面架构为多Activity多Fragment，采用MVP模式分离页面逻辑和业务逻辑，降低了耦合性

# 包结构

/data &nbsp;&nbsp;&nbsp;&nbsp;//Model层组件包，主要放数据模型类、DB接口、Api接口，这些接口统一由DataManager做为一个proxy提供Model层的对外接口  
/di   &nbsp;&nbsp;&nbsp;&nbsp;         //依赖注入，主要有两个scope：ApplicationContext和PerAcitivity，针对全局和单个活动  
/ui   &nbsp;&nbsp;&nbsp;&nbsp;         //存放Presenter和View层（活动和碎片）组件，分模块放在不同的子包里面  
/utils  &nbsp;&nbsp;&nbsp;&nbsp;        //实用工具包  
MvpApp.java  &nbsp;&nbsp;&nbsp;&nbsp;  //Android应用的全局Context，存放对于整个App lifecycle可见的状态，它在启动APP时会第一个被创建  


# 使用的第三方库
1. Dagger2，依赖注入库  
2. Rxjava，ReactiveX JVM实现，提供了异步流式处理，主要的作用是简化异步事件处理的代码，用于Model层里面的Http Client以及数据库查询逻辑（对数据进行流式处理）、
以及Presenter层中在Callback（回调函数）中对UI线程中的UI进行更新（通过调用View层方法的方式）  
3. ButterKnife，简化获取控件对象以及绑定UI事件的代码  
4. GreenDao，用于简化SQlite DAO代码  
5. fragmentation，用于更简单的管理Fragments的库  
