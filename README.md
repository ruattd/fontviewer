# FontViewer
多平台的简单字体预览器

Java 编写，支持所有能用 Swing 框架的平台（因为用的纯 Swing 界面, 理论上所有桌面平台都支持）。

### Java版本要求

测试编译使用的SDK是 `Oracle JDK 8u251` ，完全支持 Java 8+ 。代码理论上兼容 Java 7 ，不过仍推荐使用 Java 8 或以上版本(低版本可能缺少必要的依赖)。

对于 JDK 17+ 的提示：JDK 17 移除了部分 Swing 特性（即 `sun` 包的一些子包），故 Swing 应用系统主题时可能会出错，不建议使用 JDK 17 或以上版本。
