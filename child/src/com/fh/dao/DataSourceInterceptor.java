package com.fh.dao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceInterceptor {

    @Pointcut("execution(public * com.fh.service.system.tools.StudentManageService.*(..))")
    public void dataSourceMaster(){
        
    };
    
    @Before("dataSourceMaster()")
    public void before(JoinPoint jp){
        DataSourceTypeManager.set(DataSources.SLAVE);
    }
    
    @After("dataSourceMaster()")
    public void after(JoinPoint jp){
        DataSourceTypeManager.reset();
    }
    //...
    /*这里我们定义了一个 Aspect 类，我们使用 @Before 来在符合
     *  @Pointcut("execution(public * net.aazj.service..*.selectByPrimaryKey(..))") 中的方法被调用之前，
     *  调用 DataSourceTypeManager.set(DataSources.SLAVE) 设置了 key 的类型为 DataSources.MASTER，
     *  所以 dataSource 会根据key=DataSources.MASTER 选择 dataSourceSlave 这个dataSource。
     *  所以该方法对于的sql语句会在slave数据库上执行.
     *  我们可以不断的扩充 DataSourceInterceptor这个 Aspect，在中进行各种各样的定义，
     *  来为某个service的某个方法指定合适的数据源对应的dataSource。
     *  这样我们就可以使用 Spring AOP 的强大功能来，十分灵活进行配置了。
     */
}