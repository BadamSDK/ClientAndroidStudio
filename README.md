### SDK 获取
目录 `studioRes` 下的文件为 Android Studio 接入 BadamSDK 所需的 `.arr` 包以及相关的一些资源文件，若需参考，请阅读 `studioDemo` 中的相关代码示例
### Android Studio 接入BadamSdk Demo
Android Studio 有两种方式导入BadamSdk(下称SDK):
1. 以 .aar 包的方式作为 `com.android.application` 的一个依赖包接入
2. 将 SDK 作为一个 module, 以 `com.android.library` 的方式接入。

下面将详尽地说明这两种接入方式的具体过程

### 1. 以 .aar 包的方式接入
.aar 包方式接入请参考 `studioDemo` 种的使用方式, 在自己项目中使用时，请阅读 [参考文档](https://sdk-doc.badambiz.com/chapter1-started/step2.1.html)中 2.1.2.1，2.1.2.2节的说明。在这里，我们在目录 `studioRes` 目录中为你准备了接入SDK所需要的文件:

文件/夹名| 说明
--- | ---
`assets`| sdk中需要的资源文件,你可选择性地添加到项目的 assets 目录中, 添加后用户打开个人中心时体验会好很多，当然，不添加到话会减少一些包体。最后，添加时请不要修改文件名字
`AndroidManifest.xml`|SDK 所需要的清单，请将里面的所有 用户权限，Activity，`<user-library>`相关的所有内容都 copy 到你项目的应用清单中
`Pay2Sdk.aar`|SDK的具体库内容
`proguard-rules.txt`|混淆规则，如果你的项目开启代码混淆，请将这里的所有混淆规则添加到你的混淆文件中

### 2. 以 module 的方式引入
如果你有过 `.aar`包建立 library module 的经验, 那你解压 `studioRes/Pay2Sdk.aar`文件根据你的需要建立一个 library module 然后在 `setting.gradle` 中 `include` 进你的项目中就OK了,

如果你不了解 aar 建立 module 的过程，那你将 `studioModule`目录整个文件复制到你的 应用下, 进行一些简单的修改，然后 include 进你的项目中就可以了。

<B>总之，以 module 的方式引入，不管你是自己解压还是复制现有项目，都比 aar 方式引入的要复杂很多，望君慎重选择引用方式</B>

如果你复制 studioModule 目录作为你的一个 module， 记得修改 `studioModule/build.gradle` 中相应的配置，使之与你的项目相符



 
