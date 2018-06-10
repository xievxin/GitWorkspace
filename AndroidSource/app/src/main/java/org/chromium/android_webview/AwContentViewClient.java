// Copyright 2013 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.android_webview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

import org.chromium.content.browser.ContentVideoView;
import org.chromium.content.browser.ContentVideoViewClient;
import org.chromium.content.browser.ContentViewClient;

/**
 * ContentViewClient implementation for WebView
 */
public class AwContentViewClient extends ContentViewClient {

    private class AwContentVideoViewClient implements ContentVideoViewClient {
        @Override
        public boolean onShowCustomView(View view) {
            final FrameLayout viewGroup = new FrameLayout(mContext);
            viewGroup.addView(view);
            viewGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewDetachedFromWindow(View v) {
                    // Intentional no-op (see onDestroyContentVideoView).
                }

                @Override
                public void onViewAttachedToWindow(View v) {
                    if (mAwContents.isFullScreen()) {
                        return;
                    }
                    View fullscreenView = mAwContents.enterFullScreen();
                    if (fullscreenView != null) {
                        fullscreenView.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                                        && event.getAction() == KeyEvent.ACTION_UP
                                        && mAwContents.isFullScreen()) {
                                    exitFullscreen();
                                    return true;
                                }
                                return false;
                            }
                        });
                        fullscreenView.setFocusable(true);
                        fullscreenView.setFocusableInTouchMode(true);
                        fullscreenView.requestFocus();
                        viewGroup.addView(fullscreenView);
                    }
                }
            });
            WebChromeClient.CustomViewCallback cb = new WebChromeClient.CustomViewCallback() {
                @Override
                public void onCustomViewHidden() {
                    exitFullscreen();
                }
            };
            mAwContentsClient.onShowCustomView(viewGroup, cb);
            return true;
        }

        private void exitFullscreen() {
            ContentVideoView contentVideoView = ContentVideoView.getContentVideoView();
            if (contentVideoView != null)
                contentVideoView.exitFullscreen(false);
        }

        @Override
        public void onDestroyContentVideoView() {
            mAwContents.exitFullScreen();
            mAwContentsClient.onHideCustomView();
        }

        @Override
        public View getVideoLoadingProgressView() {
            return mAwContentsClient.getVideoLoadingProgressView();
        }
    }

    private AwContentsClient mAwContentsClient;
    private AwSettings mAwSettings;
    private AwContents mAwContents;
    private Context mContext;

    public AwContentViewClient(
            AwContentsClient awContentsClient, AwSettings awSettings, AwContents awContents,
            Context context) {
        mAwContentsClient = awContentsClient;
        mAwSettings = awSettings;
        mAwContents = awContents;
        mContext = context;
    }

    @Override
    public void onBackgroundColorChanged(int color) {
        mAwContentsClient.onBackgroundColorChanged(color);
    }

    @Override
    public void onStartContentIntent(Context context, String contentUrl) {
        //  Callback when detecting a click on a content link.
        mAwContentsClient.shouldOverrideUrlLoading(contentUrl);
    }

    @Override
    public void onUpdateTitle(String title) {
        mAwContentsClient.onReceivedTitle(title);
    }

    @Override
    public boolean shouldOverrideKeyEvent(KeyEvent event) {
        return mAwContentsClient.shouldOverrideKeyEvent(event);
    }

    @Override
    public final ContentVideoViewClient getContentVideoViewClient() {
        return new AwContentVideoViewClient();
    }

    @Override
    public boolean shouldBlockMediaRequest(String url) {
        return mAwSettings != null ?
                mAwSettings.getBlockNetworkLoads() && URLUtil.isNetworkUrl(url) : true;
    }
}
