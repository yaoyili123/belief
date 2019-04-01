package com.example.belief.di.component;

import com.example.belief.di.PerActivity;
import com.example.belief.di.module.ActivityModule;
//import com.example.belief.ui.login.LoginActivity;

import dagger.Component;

//scope用于指示Dragger的ActivityModule生成的bean实例，每一个activity都有一组
/*TODO: 搞清楚Component中的scope和dependencies的含义
    我的疑惑：
        1.scoped binding的含义？
        2.scoped component的含义？一个component有多个scope有什么用？
        3.为啥定义了scope注解，什么都不用写？

    首先，@Provides和含有@Inject的类都可以使用@Singleton标记限制其instance的个数，
    @Singleton是一种scope注解，因此这些bean本身就是scoped，根据CDI标准，每一个bean要么是scoped（只能有一个），
    要么是unscoped

    Dagger可以将scoped component implemation和图中的scoped instance联系起来，因此component使用scope注解的
    含义就是：表明它们想和哪一个scope中的instance联系起来，可以声明多个scope，声明之后，component就可以在类中
    定义任何关于unscope binding和that binding的provision方法

    因此所谓的scoped binding就是指component声明的scope(scope与哪一个component绑定)，但从文档上来看，
    binding应该还是指component中定义的那些方法

    PerActivity的局部单例的具体行为依赖于ActivityComponent的client code，ActivityComponent的实例和
    MainActivity是一致的，因此PerActivity就提供了MainActivity域的单例，因为ActivityModule本身就依赖一个activity
    而这个activity的生命周期

    DataManager是singlton，因此不受影响
    */

/*该component无法和@Singleton的instance建立联系，但是它通过dependencies来建立binding，
/使得这些instance可以跨越多个scope，此时ApplicationComponent中的instance都可以跨越PerActivity scope

dependencies含义，使得每一个ApplicationComponent中的provision method成为一个provider，无视scope
*/
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    /*TODO:这里有一个缺陷：
    injected Type(MainActivity)需要让injector了解其具体类型，而不是通过接口，这违反了DI的原则
    查看官方文档进行优化
     */
//    void inject(LoginActivity mainActivity);

}