①类微信一翻到底
webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
②整个HTML被压缩显示在屏幕上
webview.getSettings().setUseWideViewPort(true);
webview.getSettings().setLoadWithOverviewMode(true);
③html
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">