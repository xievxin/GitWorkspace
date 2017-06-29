Part1: html
var config = {
    scheme_IOS: 'chuangke://',
    scheme_Adr: 'launch://ckjr.xinge'
};

var ua = navigator.userAgent.toLowerCase();
var ifr = document.createElement('iframe');
ifr.src = ua.indexOf('os') > 0 ? config.scheme_IOS : config.scheme_Adr;
ifr.style.display = 'none';
document.body.appendChild(ifr);


Part2: AndroidManifest.xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:host="ckjr.xinge" android:scheme="launch" />
</intent-filter>