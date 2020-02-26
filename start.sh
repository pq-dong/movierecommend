#!/bin/sh
#预设java参数，自定义java参数用容器启动时配置环境变量JAVA_OPTS来实现，相同参数后者会覆盖前者
JAVA_DEFAULT_OPTS="
     -javaagent:/skywalking-agent/skywalking-agent.jar
     -Dskywalking.collector.backend_service=${skywalking_service}
     -Dskywalking.agent.service_name=${HOSTNAME%-*-*}
     -Xms1g
     -Xmx3g
     -XX:+UseG1GC
     -XX:MaxGCPauseMillis=100
     -XX:MetaspaceSize=128m
     -XX:MaxMetaspaceSize=256m
     -XX:+PrintTenuringDistribution
     -XX:+PrintGCDetails
     -XX:+PrintHeapAtGC
     -XX:+PrintGCDateStamps
     -XX:+PrintGCTimeStamps
     -Xloggc:gc-%t.log
     -Duser.timezone=GMT+08"

#启动服务
exec java $JAVA_DEFAULT_OPTS $JAVA_OPTS -jar app.jar $JAVA_ARGS