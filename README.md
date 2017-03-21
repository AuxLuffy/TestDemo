生命周期测试
========
### 以下为运行日志：

##### 第一次点击桌面图标开启app
    03-21 12:01:41.095 6508-6508/com.example.sunzh.testdemo E/TAG: onCreate()
    03-21 12:01:41.095 6508-6508/com.example.sunzh.testdemo E/Activity: onCreate()__textview.height:0, textview.width:0
    03-21 12:01:41.096 6508-6508/com.example.sunzh.testdemo E/TAG: onStart()
    03-21 12:01:41.096 6508-6508/com.example.sunzh.testdemo E/Activity: onStart()__textview.height:0, textview.width:0
    03-21 12:01:41.096 6508-6508/com.example.sunzh.testdemo E/TAG: onResume()
    03-21 12:01:41.096 6508-6508/com.example.sunzh.testdemo E/Activity: onResume()__textview.height:0, textview.width:0
    03-21 12:01:41.104 6508-6508/com.example.sunzh.testdemo E/MyTextView: onMeasure()
    03-21 12:01:41.129 6508-6508/com.example.sunzh.testdemo E/MyTextView: onLayout()
    03-21 12:01:41.131 6508-6508/com.example.sunzh.testdemo E/TAG: onWindowFocusChanged()____hasFocus():true
    03-21 12:01:41.131 6508-6508/com.example.sunzh.testdemo E/Activity: onWindowFocusChanged()__textview.height:51, textview.width:200
    03-21 12:01:41.139 6508-6508/com.example.sunzh.testdemo E/MyTextView: onMeasure()
    03-21 12:01:41.140 6508-6508/com.example.sunzh.testdemo E/MyTextView: onLayout()
    03-21 12:01:41.141 6508-6508/com.example.sunzh.testdemo E/MyTextView: onDraw()
##### 点击home键返回桌面
    03-21 12:01:46.671 6508-6508/com.example.sunzh.testdemo E/TAG: onWindowFocusChanged()____hasFocus():false
    03-21 12:01:46.671 6508-6508/com.example.sunzh.testdemo E/Activity: onWindowFocusChanged()__textview.height:51, textview.width:200
    03-21 12:01:46.686 6508-6508/com.example.sunzh.testdemo E/TAG: onPause()
    03-21 12:01:46.790 6508-6595/com.example.sunzh.testdemo E/Surface: getSlotFromBufferLocked: unknown buffer: 0x7f76a3d800
    03-21 12:01:47.166 6508-6508/com.example.sunzh.testdemo E/TAG: onStop()
##### 再次点击图标重启app
    03-21 12:01:49.765 6508-6508/com.example.sunzh.testdemo E/TAG: onRestart()
    03-21 12:01:49.765 6508-6508/com.example.sunzh.testdemo E/Activity: onRestart()__textview.height:51, textview.width:200
    03-21 12:01:49.767 6508-6508/com.example.sunzh.testdemo E/TAG: onStart()
    03-21 12:01:49.768 6508-6508/com.example.sunzh.testdemo E/Activity: onStart()__textview.height:51, textview.width:200
    03-21 12:01:49.769 6508-6508/com.example.sunzh.testdemo E/TAG: onResume()
    03-21 12:01:49.769 6508-6508/com.example.sunzh.testdemo E/Activity: onResume()__textview.height:51, textview.width:200
    03-21 12:01:49.789 6508-6508/com.example.sunzh.testdemo E/TAG: onWindowFocusChanged()____hasFocus():true
    03-21 12:01:49.789 6508-6508/com.example.sunzh.testdemo E/Activity: onWindowFocusChanged()__textview.height:51, textview.width:200
    03-21 12:01:49.794 6508-6508/com.example.sunzh.testdemo E/MyTextView: onDraw()
##### 点击返回键返回桌面退出应用
    03-21 12:01:52.985 6508-6508/com.example.sunzh.testdemo E/TAG: onWindowFocusChanged()____hasFocus():false
    03-21 12:01:52.985 6508-6508/com.example.sunzh.testdemo E/Activity: onWindowFocusChanged()__textview.height:51, textview.width:200
    03-21 12:01:52.986 6508-6508/com.example.sunzh.testdemo E/TAG: onPause()
    03-21 12:01:53.107 6508-6595/com.example.sunzh.testdemo E/Surface: getSlotFromBufferLocked: unknown buffer: 0x7f76a3d8a0
    03-21 12:01:53.462 6508-6508/com.example.sunzh.testdemo E/TAG: onStop()
    03-21 12:01:53.462 6508-6508/com.example.sunzh.testdemo E/TAG: onDestroy()

## 总结

如果需要获取某个View的宽高，可以在onWindowFocusChanged()处直接获取即可，
这比对某个view设置onGlobalLayoutListener的方式来获取方便了许此。
又比如，对于需要读取本地文件记录来判断是否是第一次打开界面去提示文本图片的，
也可以在此方法中去读取然后再显示在ui上。

调用顺序为Activity.oncreate()→Activity.onResume()→
→TestImageView.onMeasure()→TestImageView.onLayout()→onGlobalLayoutListener()→
→Activity.onWidnowFocusChanged()→.....→
→TextImageView.onDraw()
