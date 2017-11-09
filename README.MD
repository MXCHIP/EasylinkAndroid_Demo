### 新环境步骤

1、先看看能不能install，不能的话打开gradle目录，找到gradle，双击下载
2、配置环境
1）使用命令[cd ~]到home目录下      cd ~
2）接着使用     touch .bash_profile
3）然后   open -e .bash_profile   会以文本的形式打开文件（如果2中不存在的话就新建一个.bash_profile文件）
    export GRADLE_HOME=/Applications/Android\ Studio.app/Contents/gradle/gradle-2.8
    export PATH=${PATH}:${GRADLE_HOME}/bin
    啰嗦这么多也就是把红色的改成你自己滴  记得那个 \     在空格之前加
4）最后用[source .bash_profile]命令使用修改后的
5）操作完成后使用命令[gradle -v]看是否出现版本号    gradle -v
3、gradlew install
4、gradlew bintrayUpload

5、报错javadoc的问题有可能是文件的编码格式不对，改为utf-8就可以了，ide底部可以切换到gbk
6、

lintOptions {
        abortOnError false
    }
