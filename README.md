## FastBee
- 项目结构还有点混乱,希望还能对你带来些帮助.
## 主要目的

- 与第三方库分离,可以自由定制的开发框架
- MVP实践
- 一个能够快速集成的框架.
- 总结一年来学习,开发的感悟.
- App开发中应该注意到的一些问题.

## 介绍
### 使用到的开源库

- Okhttp,Retrofit,Gson.
- ButterKnife.
- RxJava,RxAndroid,Rclifecycle.
- Sqlcipher.
- PermissionsDispatcher.
- Leakcanary.
- and_swipeback.
- ultra-ptr.
- CircleRefreshAndLoadMoreLayout.
- Zxing,ZBar.

### 借鉴改善后的开源库

- okhttp-utils.
- luban.
- emotionkeyboard.
- AndroidUtilCode.
- 等等一些现在暂时无法找到原始地址的库.

### 项目结构

- App(Demo).
- easylinkingadapter(用于简化Listview多Type情景下的Adapter).
- easylinkingimageloader(一个图片加载管理器,为了能够随时随地替换图片框架).
- easylinkingkeyboard(优化过后的键盘控件).
- easylinkingnet(网络框架,稍微改造了一下,后期会全部剔除).
- easylinkingutils(在原来的基础上增加了修改了一些工具类).
- easylinkingview(自定义View).

### 功能介绍
##### Lib

- MVP的使用.
- 简单的欢迎页.
- 登录页,使用MVP能够清晰的展现流程.
- 滑动页,页签页.
- 权限模型.
- 扫码页,图像解码,扫码,按压解码.
- 消息页,主要是键盘控件的使用.
- 朋友圈页(感谢大灯泡).
- 联系人页.
- 缓存配置.
- 夜间模式.
- WebView,Js文件的分离.
- 毛玻璃,主要是V8包渲染使用.
- Retrofit.
- v7包,Design包的简单使用.
- 滑动关闭Activity.

#### App
- base包,基础的Activity,Fragment,Lazy基类,基础的View,Model,PreImpl基类.
- bean包,根据项目基础的泛型数据bean.
- db包,简单封装了下数据库操作,减少多线程下数据库被lock引起的Crash.
- global包,CachePath封装了App使用的文件夹路径,GlobalVar自由切换App运行环境(测试,开发,发布),HttpInterface访问路径配合GlobalVar,UserInfoManager个人信息管理.
- http包,简单的Reftrofit使用封装.
- iamgeloader包,配合easylinkingimageloaderLib,切换图片加载框架.
- ui包,Application,Activity,Fragment.
- utils包,一些按压解码,v8包,RxJava返回处理的工具类.
- view包,一些很少用的View.

#### build.gradle
- 配置信息提取.
- BuildConfig配置.
- 如何剔除不必要依赖.

#### proguard
- 配置了混淆文件.

### 截图
<a href="screen/1.png"><img src="screen/1.png" width="40%"/></a> 
<a href="screen/2.png"><img src="screen/2.png" width="40%"/></a>
<a href="screen/3.png"><img src="screen/3.png" width="40%"/>


## License
Copyright 2016 Blankj

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.